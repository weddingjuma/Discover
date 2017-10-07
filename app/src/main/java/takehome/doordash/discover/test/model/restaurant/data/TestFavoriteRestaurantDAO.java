package takehome.doordash.discover.test.model.restaurant.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import takehome.doordash.discover.data.restaurant.favorites.FavoriteRestaurant;
import takehome.doordash.discover.data.restaurant.favorites.FavoriteRestaurantsDAO;

/**
 * Created by jc on 10/7/17.
 */

public class TestFavoriteRestaurantDAO implements FavoriteRestaurantsDAO {

    private List<FavoriteRestaurant> restaurants;

    public TestFavoriteRestaurantDAO() {
        restaurants = new ArrayList<>();
        restaurants.add(new FavoriteRestaurant(3));
        restaurants.add(new FavoriteRestaurant(1));
    }

    @Override
    public List<FavoriteRestaurant> getFavoriteRestaurants() {
        return restaurants;
    }

    @Override
    public void addFavoriteRestaurant(@NonNull FavoriteRestaurant restaurant) {
        // TODO : Implement later.
    }

    @Override
    public void deleteFavoriteRestaurant(@NonNull FavoriteRestaurant restaurant) {
        // TODO : Implement later.
    }
}
