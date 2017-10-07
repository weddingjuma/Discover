package takehome.doordash.discover.injections;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import takehome.doordash.discover.utils.AppSchedulers;

/**
 * Created by jc on 10/6/17.
 */
@Module
public class SchedulersModule {

    @Provides
    @Singleton
    public AppSchedulers provideAppSchedulers(){
        return new AppSchedulers() {
            @Override
            public Scheduler main() {
                return AndroidSchedulers.mainThread();
            }

            @Override
            public Scheduler io() {
                return Schedulers.io();
            }

            @Override
            public Scheduler computation() {
                return Schedulers.computation();
            }

            @Override
            public Scheduler from(@NonNull Executor executor) {
                return Schedulers.from(executor);
            }
        };
    }
}
