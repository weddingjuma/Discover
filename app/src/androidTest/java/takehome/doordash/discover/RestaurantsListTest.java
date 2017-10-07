package takehome.doordash.discover;

import android.support.annotation.IdRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import takehome.doordash.discover.features.restaurants.RestaurantItemViewHolder;
import takehome.doordash.discover.injections.InjectionGraphs;
import takehome.doordash.discover.test.model.restaurant.injections.DaggerTestDataComponent;
import takehome.doordash.discover.test.model.restaurant.injections.TestRepoModule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by jc on 10/6/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RestaurantsListTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class){
        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();
            InjectionGraphs.initialize(DaggerTestDataComponent
                    .builder()
                    .build());
        }
    };

    @Before
    public void setup(){
        InjectionGraphs.initialize(DaggerTestDataComponent
                .builder()
                .build());
    }

    @Test
    public void title_test(){
        onView(withText("Discover")).check(matches(isDisplayed()));
    }

    @Test
    public void set_one_restaurant_favorite() {

        // 1. Assert that the first restaurant is "A Good Morning Cafe";
        onView(withRecyclerView(R.id.recycler_view).atPosition(0))
                .check(matches(hasDescendant(Matchers.allOf(
                        withText("A Good Morning Cafe")
                ))));

        // TODO : Perform clicking on the favorite button.
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(0));

//        onView(withResourceName("ic_star_black_24dp")).check(matches(isFavorite()));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * *
     *
     *          Utility Methods
     *
     * * * * * * * * * * * * * * * * * * * * * * * */

    public static RecyclerViewMatcher withRecyclerView(@IdRes int resId){
        return new RecyclerViewMatcher(resId);
    }

    public static ViewAction clickFavoriteButton(){
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(R.id.image_favorite);
                v.performClick();
            }
        };
    }

    public static Matcher<String> checkIsSetFavorite(){
        return new TypeSafeMatcher<String>() {
            @Override
            protected boolean matchesSafely(String item) {
                return "ic_star_black_24dp".equals(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("item is set to favorite");
            }
        };
    }

    public static Matcher<RestaurantItemViewHolder> isFavorite(){
        return new TypeSafeMatcher<RestaurantItemViewHolder>() {
            @Override
            protected boolean matchesSafely(RestaurantItemViewHolder item) {
                return item.isFavorited;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("item is set to favorite");
            }
        };
    }
}
