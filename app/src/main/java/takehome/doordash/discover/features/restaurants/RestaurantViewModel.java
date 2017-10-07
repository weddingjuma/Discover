package takehome.doordash.discover.features.restaurants;

import android.arch.lifecycle.ViewModel;
import android.support.v4.util.Pair;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import takehome.doordash.discover.data.restaurant.favorites.FavoriteRestaurant;
import takehome.doordash.discover.data.restaurant.favorites.FavoriteRestaurantModel;
import takehome.doordash.discover.injections.DataComponent;
import takehome.doordash.discover.injections.InjectionGraphs;
import takehome.doordash.discover.model.restaurant.Restaurant;
import takehome.doordash.discover.model.restaurant.RestaurantService;
import takehome.doordash.discover.utils.AppSchedulers;

/**
 * Created by jc on 10/6/17.
 */

public class RestaurantViewModel extends ViewModel {

    @Inject
    FavoriteRestaurantModel favoriteRestaurantModel;

    @Inject
    RestaurantService service;

    @Inject
    AppSchedulers schedulers;

    private Observable<List<Restaurant>> restaurantsObs;
    private Observable<List<FavoriteRestaurant>> favoritesObs;

    public RestaurantViewModel(){
        InjectionGraphs.data.inject(this);
    }

    public RestaurantViewModel(@android.support.annotation.NonNull DataComponent dataComponent){
        dataComponent.inject(this);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        service = null;
        clearCaches();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * *
     *
     *          Data Accessors/Modifiers
     *
     * * * * * * * * * * * * * * * * * * * * * * * */

    public Observable<List<Restaurant>> getRestaurants(double latitude,
                                                       double longitude,
                                                       final boolean sortFavorites){
        if (restaurantsObs == null) {
            restaurantsObs = service.getRestaurants(latitude, longitude)
                    .subscribeOn(schedulers.io())
                    .cache();
        }
        if (favoritesObs == null) {
            favoritesObs = favoriteRestaurantModel.getFavorites()
                    .cache();
        }
        return Observable.combineLatest(restaurantsObs, favoritesObs, new BiFunction<List<Restaurant>, List<FavoriteRestaurant>, Pair<List<Restaurant>, List<FavoriteRestaurant>>>() {
            @Override
            public Pair<List<Restaurant>, List<FavoriteRestaurant>> apply(@NonNull List<Restaurant> restaurants, @NonNull List<FavoriteRestaurant> favorites) throws Exception {
                return new Pair<>(restaurants, favorites);
            }
        })
                .map(new Function<Pair<List<Restaurant>, List<FavoriteRestaurant>>, List<Restaurant>>() {
                    @Override
                    public List<Restaurant> apply(@NonNull Pair<List<Restaurant>, List<FavoriteRestaurant>> pair) throws Exception {
                        return sortFavorites ? markFavorites(pair.first, pair.second) : pair.first;
                    }
                })
                .map(new Function<List<Restaurant>, List<Restaurant>>() {
                    @Override
                    public List<Restaurant> apply(@NonNull List<Restaurant> restaurants) throws Exception {
                        return sortFavorites ? sortFavorites(restaurants) : restaurants;
                    }
                })
                .subscribeOn(schedulers.computation())
                .observeOn(schedulers.main());
    }

    public Completable saveFavorite(Restaurant restaurant){
        return favoriteRestaurantModel
                .addFavorite(restaurant)
                .observeOn(schedulers.main());
    }

    public Completable removeFavorite(Restaurant restaurant){
        return favoriteRestaurantModel
                .removeFavorite(restaurant)
                .observeOn(schedulers.main());
    }

    public void clearRestaurantCache(){
        restaurantsObs = null;
    }

    public void clearFavoritesCache(){
        favoritesObs = null;
    }

    public void clearCaches(){
        clearFavoritesCache();
        clearRestaurantCache();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * *
     *
     *          Utility Methods
     *
     * * * * * * * * * * * * * * * * * * * * * * * */

    private static List<Restaurant> markFavorites(List<Restaurant> restaurants, List<FavoriteRestaurant> favorites){
        final Set<Integer> favoriteIds = new HashSet<>();
        Observable.fromIterable(favorites)
                .forEach(new Consumer<FavoriteRestaurant>() {
                    @Override
                    public void accept(FavoriteRestaurant restaurant) throws Exception {
                        favoriteIds.add(restaurant.id);
                    }
                });
        for(Restaurant restaurant : restaurants){
            restaurant.isFavorite = favoriteIds.contains(restaurant.id);
        }
        return restaurants;
    }

    public static List<Restaurant> sortFavorites(List<Restaurant> restaurants){
        Collections.sort(restaurants, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant r1, Restaurant r2) {
                if (r1.isFavorite == r2.isFavorite) {
                    return 0;
                } else if (r1.isFavorite) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        return restaurants;
    }
}
