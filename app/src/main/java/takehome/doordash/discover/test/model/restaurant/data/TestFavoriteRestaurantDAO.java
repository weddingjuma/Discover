package takehome.doordash.discover.test.model.restaurant.data;

import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import takehome.doordash.discover.data.restaurant.favorites.FavoriteRestaurant;
import takehome.doordash.discover.data.restaurant.favorites.FavoriteRestaurantsDAO;

/**
 * Created by jc on 10/7/17.
 */

public class TestFavoriteRestaurantDAO implements FavoriteRestaurantsDAO {

    private Set<Integer> restaurantIds;

    public TestFavoriteRestaurantDAO() {
        restaurantIds = new HashSet<>();
        restaurantIds.add(3);
        restaurantIds.add(8);
        restaurantIds.add(1);
    }

    @Override
    public List<FavoriteRestaurant> getFavoriteRestaurants() {
        return Observable.fromIterable(restaurantIds).map(new Function<Integer, FavoriteRestaurant>() {
            @Override
            public FavoriteRestaurant apply(@io.reactivex.annotations.NonNull Integer id) throws Exception {
                return new FavoriteRestaurant(id);
            }
        })
                .toList()
                .blockingGet();
    }

    @Override
    public void addFavoriteRestaurant(@NonNull FavoriteRestaurant restaurant) {
        restaurantIds.add(restaurant.id);
    }

    @Override
    public void deleteFavoriteRestaurant(@NonNull FavoriteRestaurant restaurant) {
        restaurantIds.remove(restaurant.id);
    }
}
