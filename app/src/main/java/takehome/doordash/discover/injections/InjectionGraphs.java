package takehome.doordash.discover.injections;

import android.support.annotation.NonNull;

/**
 * Created by jc on 10/6/17.
 */

public class InjectionGraphs {

    public static DataComponent data;

    /**
     * TODO : This is a hack to let Android Unit test to inject {@link takehome.doordash.discover.test.model.restaurant.injections.DaggerTestDataComponent}
     * TODO : and prevent it being replace by the production one.
     * TODO : Need to be removed in the future.
     */
    public static void initialize(@NonNull DataComponent component){
        if (data == null){
            data = component;
        }
    }
}
