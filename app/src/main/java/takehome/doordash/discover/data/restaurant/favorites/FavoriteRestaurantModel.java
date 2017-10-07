package takehome.doordash.discover.data.restaurant.favorites;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import takehome.doordash.discover.data.DiscoverDataRepo;
import takehome.doordash.discover.model.restaurant.Restaurant;
import takehome.doordash.discover.utils.AppSchedulers;

/**
 * Created by jc on 10/6/17.
 */

public class FavoriteRestaurantModel {

    private FavoriteRestaurantsDAO dao;
    private Executor executor;
    private AppSchedulers schedulers;

    public FavoriteRestaurantModel(@NonNull FavoriteRestaurantsDAO dao,
                                   @NonNull Executor executor,
                                   @NonNull AppSchedulers schedulers){
        this.dao = dao;
        this.executor = executor;
        this.schedulers = schedulers;
    }

    public Observable<List<FavoriteRestaurant>> getFavorites(){
        return Observable.defer(
                new Callable<ObservableSource<? extends List<FavoriteRestaurant>>>() {
                    @Override
                    public ObservableSource<? extends List<FavoriteRestaurant>> call() throws Exception {
                        return Observable.just(dao.getFavoriteRestaurants());
                    }
                })
                .subscribeOn(schedulers.from(executor))
                .observeOn(schedulers.main());
    }

    public Completable addFavorite(@NonNull Restaurant restaurant){
        return addFavorite(toFavorite(restaurant));
    }

    public Completable addFavorite(@NonNull final FavoriteRestaurant restaurant){
        return Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                dao.addFavoriteRestaurant(restaurant);
            }
        }).subscribeOn(schedulers.from(executor));
    }

    public Completable removeFavorite(@NonNull Restaurant restaurant){
        return removeFavorite(toFavorite(restaurant));
    }

    public Completable removeFavorite(@NonNull final FavoriteRestaurant restaurant){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteFavoriteRestaurant(restaurant);
            }
        });
        return Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                dao.deleteFavoriteRestaurant(restaurant);
            }
        }).subscribeOn(schedulers.from(executor));
    }

    @NonNull
    private static FavoriteRestaurant toFavorite(@NonNull Restaurant restaurant){
        return new FavoriteRestaurant(restaurant.id);
    }
}
