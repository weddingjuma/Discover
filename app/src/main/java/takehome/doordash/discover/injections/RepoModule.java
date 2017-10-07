package takehome.doordash.discover.injections;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import takehome.doordash.discover.data.DiscoverDataRepo;
import takehome.doordash.discover.data.restaurant.favorites.FavoriteRestaurantModel;
import takehome.doordash.discover.data.restaurant.favorites.FavoriteRestaurantsDAO;
import takehome.doordash.discover.utils.AppSchedulers;

/**
 * Created by jc on 10/6/17.
 */

@Module
public class RepoModule {

    private final Context context;

    public RepoModule(@NonNull Context context){
        this.context = context;
    }

    @Provides
    @Singleton
    public DiscoverDataRepo provideDiscoverDataRepo(){
        return Room.databaseBuilder(
                context,
                DiscoverDataRepo.class,
                "discover_app.sqlite")
                .build();
    }

    @Provides
    @Singleton
    public FavoriteRestaurantsDAO provideFavoriteRestaurantDAO(DiscoverDataRepo repo){
        return repo.favoriteRestaurantQueries();
    }

    @Provides
    @Singleton
    public Executor provideThreadExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    public FavoriteRestaurantModel provideFavoriteRestaurantModel(FavoriteRestaurantsDAO dao,
                                                                  Executor executor,
                                                                  AppSchedulers schedulers){
        return new FavoriteRestaurantModel(dao, executor, schedulers);
    }
}
