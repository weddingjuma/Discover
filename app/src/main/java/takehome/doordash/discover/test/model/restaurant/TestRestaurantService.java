package takehome.doordash.discover.test.model.restaurant;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import takehome.doordash.discover.model.restaurant.Restaurant;
import takehome.doordash.discover.model.restaurant.RestaurantService;

/**
 * Created by jc on 10/6/17.
 */

public class TestRestaurantService implements RestaurantService {

    private List<Restaurant> testData;

    public TestRestaurantService(){
        testData = (List<Restaurant>) new GsonBuilder().create().fromJson(DATA, ArrayList.class);
    }

    @Override
    public Observable<List<Restaurant>> getRestaurants(double latitude, double longitude) {
        return Observable.just(testData);
    }

    private static final String DATA = "[\n" +
            "\t{\n" +
            "\t    \"id\": 1,\n" +
            "\t    \"name\": \"A Good Morning Cafe\",\n" +
            "\t    \"status_type\": \"open\",\n" +
            "\t    \"status\": \"48 mins\",\n" +
            "\t    \"description\": \"American, Breakfast & Brunch\",\n" +
            "\t    \"cover_img_url\": \"https://cdn.doordash.com/media/restaurant/cover/A-Good-Morning-Cafe.png\",\n" +
            "\t    \"delivery_fee\": 1\n" +
            "    },\n" +
            "    {\n" +
            "\t    \"id\": 2,\n" +
            "\t    \"name\": \"Buon Giorno\",\n" +
            "\t    \"status_type\": \"open\",\n" +
            "\t    \"status\": \"23 mins\",\n" +
            "\t    \"description\": \"Italian Cusine\",\n" +
            "\t    \"cover_img_url\": \"https://cdn.doordash.com/media/restaurant/cover/A-Good-Morning-Cafe.png\",\n" +
            "\t    \"delivery_fee\": 2\n" +
            "    },\n" +
            "    \t{\n" +
            "\t    \"id\": 3,\n" +
            "\t    \"name\": \"Pad\",\n" +
            "\t    \"status_type\": \"open\",\n" +
            "\t    \"status\": \"15 mins\",\n" +
            "\t    \"description\": \"Thai\",\n" +
            "\t    \"cover_img_url\": null,\n" +
            "\t    \"delivery_fee\": 3\n" +
            "    },\n" +
            "    {\n" +
            "\t    \"id\": 4,\n" +
            "\t    \"name\": \"Golden Dragon\",\n" +
            "\t    \"status_type\": \"open\",\n" +
            "\t    \"status\": \"5 mins\",\n" +
            "\t    \"description\": \"Chinese\",\n" +
            "\t    \"cover_img_url\": \"https://cdn.doordash.com/media/restaurant/cover/A-Good-Morning-Cafe.png\",\n" +
            "\t    \"delivery_fee\": 4\n" +
            "    },\n" +
            "    {\n" +
            "\t    \"id\": 5,\n" +
            "\t    \"name\": \"Bon me\",\n" +
            "\t    \"status_type\": \"open\",\n" +
            "\t    \"status\": \"35 mins\",\n" +
            "\t    \"description\": \"Vietnamese Sandwich\",\n" +
            "\t    \"cover_img_url\": \"https://cdn.doordash.com/media/restaurant/cover/A-Good-Morning-Cafe.png\",\n" +
            "\t    \"delivery_fee\": 5\n" +
            "    }\n" +
            "]";
}
