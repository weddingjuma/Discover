package takehome.doordash.discover.model.restaurant;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jc on 10/6/17.
 */

public interface RestaurantService {

    @GET("v2/restaurant")
    Single<List<Restaurant>> getRestaurants(
            @Query("lat") double latitude,
            @Query("lng") double longitude
    );

}
