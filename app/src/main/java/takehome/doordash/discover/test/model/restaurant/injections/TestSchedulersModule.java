package takehome.doordash.discover.test.model.restaurant.injections;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import takehome.doordash.discover.utils.AppSchedulers;

/**
 * Created by jc on 10/6/17.
 */
@Module
public class TestSchedulersModule {

    private final AppSchedulers schedulers;

    public TestSchedulersModule(){
        this.schedulers = AppSchedulers.TEST;
    }

    public TestSchedulersModule(@NonNull AppSchedulers schedulers){
        this.schedulers = schedulers;
    }

    @Provides
    @Singleton
    public AppSchedulers provideAppSchedulers() {
        return schedulers;
    }
}
