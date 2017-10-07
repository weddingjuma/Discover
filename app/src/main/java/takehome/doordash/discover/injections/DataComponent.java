package takehome.doordash.discover.injections;

import takehome.doordash.discover.features.restaurants.RestaurantViewModel;

/**
 * Created by jc on 10/6/17.
 */

public interface DataComponent {
    void inject(RestaurantViewModel viewModel);
}
