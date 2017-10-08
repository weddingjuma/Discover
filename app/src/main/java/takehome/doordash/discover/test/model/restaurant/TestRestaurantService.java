package takehome.doordash.discover.test.model.restaurant;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Single;
import takehome.doordash.discover.model.restaurant.Restaurant;
import takehome.doordash.discover.model.restaurant.RestaurantService;

/**
 * Created by jc on 10/6/17.
 */

public class TestRestaurantService implements RestaurantService {

    private List<Restaurant> testData;

    public TestRestaurantService(){
        Type classType = new TypeToken<List<Restaurant>>(){}.getType();
        testData = new GsonBuilder().create().fromJson(DATA, classType);
    }

    @Override
    public Single<List<Restaurant>> getRestaurants(double latitude, double longitude) {
        return Single.just(testData);
    }

    private static final String DATA = "[\n" +
            "\t{\n" +
            "\t    \"id\": 1,\n" +
            "\t    \"name\": \"A Good Morning CafÃ©\",\n" +
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
            "\t    \"cover_img_url\": \"https://cdn.doordash.com/media/restaurant/cover/La-PanotiQ.png\",\n" +
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
            "\t    \"cover_img_url\": \"https://cdn.doordash.com/media/restaurant/cover/Hobees.png\",\n" +
            "\t    \"delivery_fee\": 4\n" +
            "    },\n" +
            "    {\n" +
            "\t    \"id\": 5,\n" +
            "\t    \"name\": \"Bon me\",\n" +
            "\t    \"status_type\": \"open\",\n" +
            "\t    \"status\": \"35 mins\",\n" +
            "\t    \"description\": \"Vietnamese Sandwich\",\n" +
            "\t    \"cover_img_url\": \"https://cdn.doordash.com/media/restaurant/cover/KFC.png\",\n" +
            "\t    \"delivery_fee\": 5\n" +
            "    },\n" +
            "    {\n" +
            "\t    \"id\": 6,\n" +
            "\t    \"name\": \"LaDoise\",\n" +
            "\t    \"status_type\": \"close\",\n" +
            "\t    \"status\": \"1 hr\",\n" +
            "\t    \"description\": \"French\",\n" +
            "\t    \"cover_img_url\": \"https://cdn.doordash.com/media/restaurant/cover/LaFontaine_186_Mountain_View_CA.png\",\n" +
            "\t    \"delivery_fee\": 6\n" +
            "    },\n" +
            "    {\n" +
            "\t    \"id\": 7,\n" +
            "\t    \"name\": \"TD Steak\",\n" +
            "\t    \"status_type\": \"open\",\n" +
            "\t    \"status\": \"50 mins\",\n" +
            "\t    \"description\": \"Steakhouse\",\n" +
            "\t    \"cover_img_url\": \"https://cdn.doordash.com/media/restaurant/cover/lbsteak.png\",\n" +
            "\t    \"delivery_fee\": 7\n" +
            "    },\n" +
            "    {\n" +
            "\t    \"id\": 8,\n" +
            "\t    \"name\": \"ShojiSushi\",\n" +
            "\t    \"status_type\": \"open\",\n" +
            "\t    \"status\": \"15 mins\",\n" +
            "\t    \"description\": \"Japanese\",\n" +
            "\t    \"cover_img_url\": \"https://cdn.doordash.com/media/restaurant/cover/ShojiSushi.png\",\n" +
            "\t    \"delivery_fee\": 8\n" +
            "    },\n" +
            "    {\n" +
            "\t    \"id\": 9,\n" +
            "\t    \"name\": \"KFC\",\n" +
            "\t    \"status_type\": \"open\",\n" +
            "\t    \"status\": \"35 mins\",\n" +
            "\t    \"description\": \"Fastfood\",\n" +
            "\t    \"cover_img_url\": \"https://cdn.doordash.com/media/restaurant/cover/KFC.png\",\n" +
            "\t    \"delivery_fee\": 10\n" +
            "    }\n" +
            "]";
}
