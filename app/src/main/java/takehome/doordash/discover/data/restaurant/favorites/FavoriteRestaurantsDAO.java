package takehome.doordash.discover.data.restaurant.favorites;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by jc on 10/6/17.
 */
@Dao
public interface FavoriteRestaurantsDAO {

    @Query("SELECT * FROM FavoriteRestaurants")
    List<FavoriteRestaurant> getFavoriteRestaurants();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFavoriteRestaurant(@NonNull FavoriteRestaurant restaurant);

    @Delete
    void deleteFavoriteRestaurant(@NonNull FavoriteRestaurant restaurant);
}
