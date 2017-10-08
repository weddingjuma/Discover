package takehome.doordash.discover.test.model.restaurant.injections;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import takehome.doordash.discover.data.restaurant.favorites.FavoriteRestaurantModel;
import takehome.doordash.discover.data.restaurant.favorites.FavoriteRestaurantsDAO;
import takehome.doordash.discover.test.model.restaurant.data.TestFavoriteRestaurantDAO;
import takehome.doordash.discover.utils.AppSchedulers;

/**
 * Created by jc on 10/6/17.
 */
@Module
public class TestRepoModule {

    @Provides
    public FavoriteRestaurantsDAO provideFavoriteRestaurantsDAO(){
        return new TestFavoriteRestaurantDAO();
    }

    @Provides
    public Executor provideThreadExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    public FavoriteRestaurantModel provideFavoriteRestaurantModel(FavoriteRestaurantsDAO dao,
                                                                  Executor executor,
                                                                  AppSchedulers schedulers){
        return new FavoriteRestaurantModel(dao, executor, schedulers);
    }

}
