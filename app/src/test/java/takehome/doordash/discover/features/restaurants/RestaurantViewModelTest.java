package takehome.doordash.discover.features.restaurants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;
import takehome.doordash.discover.injections.DataComponent;
import takehome.doordash.discover.model.restaurant.Restaurant;
import takehome.doordash.discover.test.model.restaurant.injections.DaggerTestDataComponent;

/**
 * Created by jc on 10/7/17.
 */
@RunWith(JUnit4.class)
public class RestaurantViewModelTest {

    RestaurantViewModel viewModel;

    @Before
    public void setup(){
        DataComponent component = DaggerTestDataComponent.builder()
                .build();
        viewModel = new RestaurantViewModel(component);
    }

    @Test
    public void test_favorite_restaurant_soring(){
        viewModel.getRestaurants(0, 0, true)
                .test()
                .assertSubscribed();
        viewModel.getRestaurants(0, 0, true)
                .test()
                .assertValueAt(0, new Predicate<List<Restaurant>>() {
                    @Override
                    public boolean test(@NonNull List<Restaurant> restaurants) throws Exception {
                        return restaurants.get(0).isFavorite;
                    }
                });
    }
}