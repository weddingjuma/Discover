package takehome.doordash.discover.test.model.restaurant.injections;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;
import takehome.doordash.discover.utils.AppSchedulers;

/**
 * Created by jc on 10/6/17.
 */
@Module
public class TestSchedulersModule {

    @Provides
    @Singleton
    public AppSchedulers provideAppSchedulers() {
        return new AppSchedulers() {
            @Override
            public Scheduler main() {
                return test();
            }

            @Override
            public Scheduler io() {
                return test();
            }

            @Override
            public Scheduler computation() {
                return test();
            }

            @Override
            public Scheduler from(@NonNull Executor executor) {
                return test();
            }

            private Scheduler test(){
                return new TestScheduler();
            }
        };
    }
}
