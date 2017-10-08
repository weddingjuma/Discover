package takehome.doordash.discover;

import android.app.Application;

import takehome.doordash.discover.injections.DaggerProductionDataComponent;
import takehome.doordash.discover.injections.DataComponent;
import takehome.doordash.discover.injections.ProductionDataComponent;
import takehome.doordash.discover.injections.RepoModule;
import takehome.doordash.discover.test.model.restaurant.injections.DaggerTestDataComponent;
import takehome.doordash.discover.test.model.restaurant.injections.TestDataComponent;
import takehome.doordash.discover.test.model.restaurant.injections.TestSchedulersModule;
import takehome.doordash.discover.utils.AppSchedulers;

/**
 * Created by jc on 10/6/17.
 */

public class Discover extends Application {

    public static ProductionDataComponent productionDataGraph;
    public static TestDataComponent demoDataGraph;

    private static boolean isDemoMode;

    @Override
    public void onCreate() {
        super.onCreate();
        productionDataGraph = DaggerProductionDataComponent
                .builder()
                .repoModule(new RepoModule(getApplicationContext()))
                .build();
        demoDataGraph = DaggerTestDataComponent
                .builder()
                .testSchedulersModule(new TestSchedulersModule(AppSchedulers.PRODUCTION))
                .build();
    }

    public static DataComponent injectionGraph(){
        return !isDemoMode ? productionDataGraph : demoDataGraph;
    }

    public static void setDemoMode(boolean demo){
        isDemoMode = demo;
    }

    public static boolean isDemoMode(){
        return isDemoMode;
    }
}
