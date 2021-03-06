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
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import takehome.doordash.discover.data.restaurant.favorites.FavoriteRestaurant;
import takehome.doordash.discover.data.restaurant.favorites.FavoriteRestaurantModel;
import takehome.doordash.discover.model.restaurant.Restaurant;
import takehome.doordash.discover.model.restaurant.RestaurantService;
import takehome.doordash.discover.utils.AppSchedulers;

/**
 * View Model class which provides access to the list of ordered/un-ordered
 * {@link Restaurant}.
 *
 * All the reactive streams in this class will be observing on Android main
 * thread by default.
 *
 * Created by jc on 10/6/17.
 */

public class RestaurantViewModel extends ViewModel {

    @Inject
    FavoriteRestaurantModel favoriteRestaurantModel;

    @Inject
    RestaurantService service;

    @Inject
    AppSchedulers schedulers;

    private Single<List<Restaurant>> restaurantsObs;
    private Single<List<FavoriteRestaurant>> favoritesObs;

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

    /**
     * Returns a reactive stream of a query results of {@link Restaurant} near
     * the given coordinate.
     *
     * If the data aren't fetched yet, {@link RestaurantService} shall make a request to
     * DoorDash server and the result will be cached after it's fetched.
     *
     * @param latitude lat of location
     * @param longitude long of location
     * @param sortByFavorites whether to sort the favorite restaurants to the front.
     * @return a {@link Single} of list of {@link Restaurant}.
     */
    public Single<List<Restaurant>> getRestaurants(double latitude,
                                                       double longitude,
                                                       final boolean sortByFavorites){
        if (restaurantsObs == null) {
            restaurantsObs = service.getRestaurants(latitude, longitude)
                    .subscribeOn(schedulers.io())
                    .cache();
        }
        if (favoritesObs == null) {
            favoritesObs = favoriteRestaurantModel.getFavorites()
                    .cache();
        }
        return Single.zip(restaurantsObs, favoritesObs, new BiFunction<List<Restaurant>, List<FavoriteRestaurant>, Pair<List<Restaurant>, List<FavoriteRestaurant>>>() {
            @Override
            public Pair<List<Restaurant>, List<FavoriteRestaurant>> apply(@NonNull List<Restaurant> restaurants, @NonNull List<FavoriteRestaurant> favorites) throws Exception {
                return new Pair<>(restaurants, favorites);
            }
        })
                .map(new Function<Pair<List<Restaurant>, List<FavoriteRestaurant>>, List<Restaurant>>() {
                    @Override
                    public List<Restaurant> apply(@NonNull Pair<List<Restaurant>, List<FavoriteRestaurant>> pair) throws Exception {
                        return markFavorites(pair.first, pair.second);
                    }
                })
                .map(new Function<List<Restaurant>, List<Restaurant>>() {
                    @Override
                    public List<Restaurant> apply(@NonNull List<Restaurant> restaurants) throws Exception {
                        return sortByFavorites ? sortFavorites(restaurants) : restaurants;
                    }
                })
                .subscribeOn(schedulers.computation())
                .observeOn(schedulers.main());
    }

    /**
     * Returns a reactive stream of favorite saving action.
     *
     * This action happens asynchronously but will return
     * callback on the main thread.
     *
     * This action also automatically invalidate favorites cache
     * once the action is done.
     *
     * @param restaurant restaurant to be added to the favorite list.
     * @return a {@link Completable}
     */
    public Completable saveFavorite(Restaurant restaurant){
        return favoriteRestaurantModel
                .addFavorite(restaurant)
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        clearFavoritesCache();
                    }
                })
                .observeOn(schedulers.main());
    }

    /**
     * Returns a reactive stream of favorite deleting action.
     *
     * This action happens asynchronously but will return
     * callback on the main thread.
     *
     * This action also automatically invalidate favorites cache
     * once the action is done.
     *
     * @param restaurant restaurant to be removed from the favorite list.
     * @return a {@link Completable}
     */
    public Completable removeFavorite(Restaurant restaurant){
        return favoriteRestaurantModel
                .removeFavorite(restaurant)
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        clearFavoritesCache();
                    }
                })
                .observeOn(schedulers.main());
    }

    /**
     * Invalidates nearest restaurant query result.
     */
    public void clearRestaurantCache(){
        restaurantsObs = null;
    }

    /**
     * Invalidates favorites restaurant list saved in memory.
     */
    private void clearFavoritesCache(){
        favoritesObs = null;
    }

    /**
     * Invalidate all data.
     */
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
        for (FavoriteRestaurant favorite: favorites) {
            favoriteIds.add(favorite.id);
        }
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
