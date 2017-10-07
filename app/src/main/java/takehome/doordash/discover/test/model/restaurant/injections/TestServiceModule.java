package takehome.doordash.discover.test.model.restaurant.injections;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import takehome.doordash.discover.model.restaurant.RestaurantService;
import takehome.doordash.discover.test.model.restaurant.TestRestaurantService;

/**
 * Created by jc on 10/6/17.
 */
@Module
public class TestServiceModule {

    @Provides
    @Singleton
    public RestaurantService provideRestaurantService(){
        return new TestRestaurantService();
    }
}
