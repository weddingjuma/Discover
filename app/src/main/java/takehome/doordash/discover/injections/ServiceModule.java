package takehome.doordash.discover.injections;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import takehome.doordash.discover.model.DoorDashApi;
import takehome.doordash.discover.model.restaurant.RestaurantService;

/**
 * Created by jc on 10/6/17.
 */
@Module
public class ServiceModule {

    @Provides
    @Singleton
    public RestaurantService provideRestaurantService(){
        return DoorDashApi.getRestaurantService();
    }
}
