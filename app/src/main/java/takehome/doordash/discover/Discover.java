package takehome.doordash.discover;

import android.app.Application;

import takehome.doordash.discover.injections.DaggerProductionDataComponent;
import takehome.doordash.discover.injections.InjectionGraphs;
import takehome.doordash.discover.injections.RepoModule;

/**
 * Created by jc on 10/6/17.
 */

public class Discover extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        InjectionGraphs.initialize(DaggerProductionDataComponent
                .builder()
                .repoModule(new RepoModule(getApplicationContext()))
                .build());
    }
}
