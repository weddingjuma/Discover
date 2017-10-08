package takehome.doordash.discover.utils;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

/**
 * Created by jc on 10/6/17.
 */

public abstract class AppSchedulers {

    public abstract Scheduler main();

    public abstract Scheduler io();

    public abstract Scheduler computation();

    public abstract Scheduler from(@NonNull Executor executor);

    public static final AppSchedulers PRODUCTION = new AppSchedulers() {
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

    public static final AppSchedulers TEST = new AppSchedulers() {

        private final Scheduler testScheduler = Schedulers.trampoline();

        @Override
        public Scheduler main() {
            return testScheduler;
        }

        @Override
        public Scheduler io() {
            return testScheduler;
        }

        @Override
        public Scheduler computation() {
            return testScheduler;
        }

        @Override
        public Scheduler from(@NonNull Executor executor) {
            return testScheduler;
        }
    };
}
