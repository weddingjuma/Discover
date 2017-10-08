package takehome.doordash.discover.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import takehome.doordash.discover.data.restaurant.favorites.FavoriteRestaurant;
import takehome.doordash.discover.data.restaurant.favorites.FavoriteRestaurantsDAO;

/**
 *
 * Repo class of all data saved on disk.
 * Implemented by {@link android.arch.persistence.room.Room}.
 *
 * Created by jc on 10/6/17.
 */
@Database(entities = {FavoriteRestaurant.class}, version = 1, exportSchema = false)
public abstract class DiscoverDataRepo extends RoomDatabase {

    public abstract FavoriteRestaurantsDAO favoriteRestaurantQueries();
}
