package takehome.doordash.discover.data.restaurant.favorites;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import takehome.doordash.discover.model.restaurant.Restaurant;
import takehome.doordash.discover.utils.AppSchedulers;

/**
 * The Model class of {@link FavoriteRestaurant}.
 *
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

    public Single<List<FavoriteRestaurant>> getFavorites(){
        return Single.fromCallable(new Callable<List<FavoriteRestaurant>>() {
            @Override
            public List<FavoriteRestaurant> call() throws Exception {
                return dao.getFavoriteRestaurants();
            }
        })
                .subscribeOn(schedulers.from(executor))
                .observeOn(schedulers.main());
    }

    public Completable addFavorite(@NonNull Restaurant restaurant){
        return addFavorite(toFavorite(restaurant));
    }

    public Completable addFavorite(@NonNull final FavoriteRestaurant restaurant){
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                dao.addFavoriteRestaurant(restaurant);
            }
        }).subscribeOn(schedulers.from(executor));
    }

    public Completable removeFavorite(@NonNull Restaurant restaurant){
        return removeFavorite(toFavorite(restaurant));
    }

    public Completable removeFavorite(@NonNull final FavoriteRestaurant restaurant){
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                dao.deleteFavoriteRestaurant(restaurant);
            }
        }).subscribeOn(schedulers.from(executor));
    }

    @NonNull
    private static FavoriteRestaurant toFavorite(@NonNull Restaurant restaurant){
        return new FavoriteRestaurant(restaurant.id);
    }
}
