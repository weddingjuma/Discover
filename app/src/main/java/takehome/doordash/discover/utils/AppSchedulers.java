package takehome.doordash.discover.utils;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jc on 10/6/17.
 */

public interface AppSchedulers {

    Scheduler main();

    Scheduler io();

    Scheduler computation();

    Scheduler from(@NonNull Executor executor);
}
