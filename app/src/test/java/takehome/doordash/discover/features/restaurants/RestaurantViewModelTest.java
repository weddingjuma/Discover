package takehome.doordash.discover.features.restaurants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.TestObserver;
import takehome.doordash.discover.injections.DataComponent;
import takehome.doordash.discover.model.restaurant.Restaurant;
import takehome.doordash.discover.test.model.restaurant.injections.DaggerTestDataComponent;
import takehome.doordash.discover.test.model.restaurant.injections.TestSchedulersModule;
import takehome.doordash.discover.utils.AppSchedulers;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by jc on 10/7/17.
 */
@RunWith(JUnit4.class)
public class RestaurantViewModelTest {

    private RestaurantViewModel viewModel;

    @Before
    public void setup(){
        DataComponent component = DaggerTestDataComponent.builder()
                .testSchedulersModule(new TestSchedulersModule(AppSchedulers.TEST))
                .build();
        viewModel = new RestaurantViewModel();
        component.inject(viewModel);
    }

    @Test
    public void favorite_restaurants_original_order_test(){
        TestObserver<List<Restaurant>> testObserver = viewModel.getRestaurants(0, 0, false)
                .test();

        testObserver.awaitTerminalEvent(1, TimeUnit.SECONDS);

        // 1. Assert events

        testObserver.assertSubscribed();
        testObserver.assertComplete();

        // 2. Assert restaurants are in the original order

        testObserver.assertValue(new Predicate<List<Restaurant>>() {
            @Override
            public boolean test(@NonNull List<Restaurant> restaurants) throws Exception {
                return restaurants.get(0).id == 1;
            }
        });
        testObserver.assertValue(new Predicate<List<Restaurant>>() {
            @Override
            public boolean test(@NonNull List<Restaurant> restaurants) throws Exception {
                return restaurants.get(1).id == 2;
            }
        });
        testObserver.assertValue(new Predicate<List<Restaurant>>() {
            @Override
            public boolean test(@NonNull List<Restaurant> restaurants) throws Exception {
                return restaurants.get(2).id == 3;
            }
        });
        testObserver.assertValue(new Predicate<List<Restaurant>>() {
            @Override
            public boolean test(@NonNull List<Restaurant> restaurants) throws Exception {
                return restaurants.get(3).id == 4;
            }
        });
        testObserver.assertValue(new Predicate<List<Restaurant>>() {
            @Override
            public boolean test(@NonNull List<Restaurant> restaurants) throws Exception {
                return restaurants.get(4).id == 5;
            }
        });
        testObserver.assertValue(new Predicate<List<Restaurant>>() {
            @Override
            public boolean test(@NonNull List<Restaurant> restaurants) throws Exception {
                return restaurants.get(5).id == 6;
            }
        });
        testObserver.assertValue(new Predicate<List<Restaurant>>() {
            @Override
            public boolean test(@NonNull List<Restaurant> restaurants) throws Exception {
                return restaurants.get(6).id == 7;
            }
        });
        testObserver.assertValue(new Predicate<List<Restaurant>>() {
            @Override
            public boolean test(@NonNull List<Restaurant> restaurants) throws Exception {
                return restaurants.get(7).id == 8;
            }
        });
        testObserver.assertValue(new Predicate<List<Restaurant>>() {
            @Override
            public boolean test(@NonNull List<Restaurant> restaurants) throws Exception {
                return restaurants.get(8).id == 9;
            }
        });
    }

    @Test
    public void favorite_restaurant_soring_blocking(){
        List<Restaurant> restaurants = viewModel.getRestaurants(0, 0, true)
                .blockingGet();

        // 1. Assert number of restaurants.

        assertEquals(restaurants.size(), 9);

        // 2. Assert that all favorite restaurants are sorted to the front.

        assertTrue(restaurants.get(0).isFavorite);
        assertTrue(restaurants.get(1).isFavorite);
        assertTrue(restaurants.get(2).isFavorite);

        assertFalse(restaurants.get(3).isFavorite);
        assertFalse(restaurants.get(4).isFavorite);
        assertFalse(restaurants.get(5).isFavorite);
        assertFalse(restaurants.get(6).isFavorite);
        assertFalse(restaurants.get(7).isFavorite);
        assertFalse(restaurants.get(8).isFavorite);
    }

    @Test
    public void favorite_restaurant_sorting_observable(){
        TestObserver<List<Restaurant>> testObserver = viewModel.getRestaurants(0, 0, true)
                .test();

        testObserver.awaitTerminalEvent(1, TimeUnit.SECONDS);

        // 1. Assert events

        testObserver.assertSubscribed();
        testObserver.assertComplete();

        // 2. Assert that all favorite restaurants are sorted to the front.

        testObserver.assertValue(new Predicate<List<Restaurant>>() {
            @Override
            public boolean test(@NonNull List<Restaurant> restaurants) throws Exception {
                return restaurants.size() == 9;
            }
        });
        testObserver.assertValue(new Predicate<List<Restaurant>>() {
            @Override
            public boolean test(@NonNull List<Restaurant> restaurants) throws Exception {
                return restaurants.get(0).isFavorite;
            }
        });
        testObserver.assertValue(new Predicate<List<Restaurant>>() {
            @Override
            public boolean test(@NonNull List<Restaurant> restaurants) throws Exception {
                return restaurants.get(1).isFavorite;
            }
        });
        testObserver.assertValue(new Predicate<List<Restaurant>>() {
            @Override
            public boolean test(@NonNull List<Restaurant> restaurants) throws Exception {
                return restaurants.get(2).isFavorite;
            }
        });
        testObserver.assertValue(new Predicate<List<Restaurant>>() {
            @Override
            public boolean test(@NonNull List<Restaurant> restaurants) throws Exception {
                return !restaurants.get(3).isFavorite;
            }
        });
        testObserver.assertValue(new Predicate<List<Restaurant>>() {
            @Override
            public boolean test(@NonNull List<Restaurant> restaurants) throws Exception {
                return !restaurants.get(4).isFavorite;
            }
        });
        testObserver.assertValue(new Predicate<List<Restaurant>>() {
            @Override
            public boolean test(@NonNull List<Restaurant> restaurants) throws Exception {
                return !restaurants.get(5).isFavorite;
            }
        });
        testObserver.assertValue(new Predicate<List<Restaurant>>() {
            @Override
            public boolean test(@NonNull List<Restaurant> restaurants) throws Exception {
                return !restaurants.get(6).isFavorite;
            }
        });
        testObserver.assertValue(new Predicate<List<Restaurant>>() {
            @Override
            public boolean test(@NonNull List<Restaurant> restaurants) throws Exception {
                return !restaurants.get(7).isFavorite;
            }
        });
        testObserver.assertValue(new Predicate<List<Restaurant>>() {
            @Override
            public boolean test(@NonNull List<Restaurant> restaurants) throws Exception {
                return !restaurants.get(8).isFavorite;
            }
        });
    }

    @Test
    public void add_favorite_restaurant(){
        List<Restaurant> restaurants = viewModel.getRestaurants(0, 0, false)
                .blockingGet();

        // 1. Set restaurant (id = 5) to favorite.

        final Restaurant restaurant5 = restaurants.get(4);
        TestObserver<Void> saveObserver = viewModel.saveFavorite(restaurant5).test();

        saveObserver.awaitTerminalEvent(1, TimeUnit.SECONDS);

        saveObserver.assertSubscribed();
        saveObserver.assertComplete();

        // 2. Verify that restaurant (id = 5) is a favorite restaurant.

        TestObserver<List<Restaurant>> testObserver = viewModel.getRestaurants(0, 0, true)
                .test();

        testObserver.awaitTerminalEvent(1, TimeUnit.SECONDS);

        testObserver.assertValue(new Predicate<List<Restaurant>>() {
            @Override
            public boolean test(@NonNull List<Restaurant> restaurants) throws Exception {
                for (Restaurant restaurant: restaurants) {
                    if (restaurant.id == restaurant5.id){
                        return restaurant.isFavorite;
                    }
                }
                return false;
            }
        });
    }

    @Test
    public void remove_favorite_restaurant(){
        List<Restaurant> restaurants = viewModel.getRestaurants(0, 0, false)
                .blockingGet();

        // 1. Set restaurant (id = 8) not be to favorite.

        final Restaurant restaurant8 = restaurants.get(7);
        TestObserver<Void> removeObserver = viewModel.removeFavorite(restaurant8).test();

        removeObserver.awaitTerminalEvent(1, TimeUnit.SECONDS);

        removeObserver.assertSubscribed();
        removeObserver.assertComplete();

        // 2. Verify that restaurant (id = 8) is NOT a favorite restaurant.

        TestObserver<List<Restaurant>> testObserver = viewModel.getRestaurants(0, 0, true)
                .test();

        testObserver.awaitTerminalEvent(1, TimeUnit.SECONDS);

        testObserver.assertValue(new Predicate<List<Restaurant>>() {
            @Override
            public boolean test(@NonNull List<Restaurant> restaurants) throws Exception {
                for (Restaurant restaurant: restaurants) {
                    if (restaurant.id == restaurant8.id){
                        return !restaurant.isFavorite;
                    }
                }
                return false;
            }
        });
    }
}