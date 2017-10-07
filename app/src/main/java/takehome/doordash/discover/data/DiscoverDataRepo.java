package takehome.doordash.discover.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import takehome.doordash.discover.data.restaurant.favorites.FavoriteRestaurant;
import takehome.doordash.discover.data.restaurant.favorites.FavoriteRestaurantsDAO;

/**
 * Created by jc on 10/6/17.
 */
@Database(entities = {FavoriteRestaurant.class}, version = 1)
public abstract class DiscoverDataRepo extends RoomDatabase {

    public abstract FavoriteRestaurantsDAO favoriteRestaurantQueries();
}
