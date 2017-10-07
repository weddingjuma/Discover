package takehome.doordash.discover.model;

import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import takehome.doordash.discover.model.restaurant.RestaurantService;

/**
 * Created by jc on 10/6/17.
 */

public class DoorDashApi {

    private static final String DOOR_DASH_HOST_URL = "https://api.doordash.com/";

    private static RestaurantService restaurantService;

    public static RestaurantService getRestaurantService(){
        if (restaurantService == null) {
            restaurantService = createRestaurantService();
        }
        return restaurantService;
    }

    private static OkHttpClient createHttpClient(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    private static Retrofit createRetrofit(@NonNull String hostUrl){
        return new Retrofit.Builder()
                .client(createHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(hostUrl)
                .build();
    }

    private static RestaurantService createRestaurantService(){
        return createRetrofit(DOOR_DASH_HOST_URL).create(RestaurantService.class);
    }
}
