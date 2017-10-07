package takehome.doordash.discover.injections;

import javax.inject.Singleton;

import dagger.Component;
import takehome.doordash.discover.features.restaurants.RestaurantViewModel;
import takehome.doordash.discover.utils.AppSchedulers;

/**
 * Created by jc on 10/6/17.
 */
@Singleton
@Component(modules = {
        ServiceModule.class,
        RepoModule.class,
        SchedulersModule.class
})
public interface ProductionDataComponent extends DataComponent {

}