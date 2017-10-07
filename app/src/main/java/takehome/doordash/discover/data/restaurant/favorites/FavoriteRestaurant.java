package takehome.doordash.discover.data.restaurant.favorites;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by jc on 10/6/17.
 */

@Entity(tableName = "FavoriteRestaurants")
public class FavoriteRestaurant {

    @PrimaryKey
    public int id;

    public FavoriteRestaurant(int id){
        this.id = id;
    }
}
