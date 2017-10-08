package takehome.doordash.discover.injections;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import takehome.doordash.discover.utils.AppSchedulers;

/**
 * Created by jc on 10/6/17.
 */
@Module
public class SchedulersModule {

    @Provides
    @Singleton
    public AppSchedulers provideAppSchedulers(){
        return AppSchedulers.PRODUCTION;
    }
}
