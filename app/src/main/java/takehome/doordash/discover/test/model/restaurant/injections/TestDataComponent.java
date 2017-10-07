package takehome.doordash.discover.test.model.restaurant.injections;

import javax.inject.Singleton;

import dagger.Component;
import takehome.doordash.discover.features.restaurants.RestaurantViewModel;
import takehome.doordash.discover.injections.DataComponent;

/**
 * Created by jc on 10/6/17.
 */
@Singleton
@Component(modules = {
        TestServiceModule.class,
        TestRepoModule.class,
        TestSchedulersModule.class
})
public interface TestDataComponent extends DataComponent {

}
