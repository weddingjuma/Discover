package takehome.doordash.discover;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import takehome.doordash.discover.utils.RecyclerViewMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static takehome.doordash.discover.utils.DrawableMatcher.withDrawable;

/**
 * Created by jc on 10/6/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoRestaurantsListTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(
            MainActivity.class,
            true,
            false);

    @Before
    public void setup(){
        /*
         * Switch to TestRepoModule before MainActivity is launched
         * and before RestaurantViewModel is injected.
         */
        Discover.setDemoMode(true);
    }

    @After
    public void cleanUp(){
        Discover.resetTest();
    }

    private void launchMainActivity(){
        Context context = InstrumentationRegistry.getTargetContext();
        Intent intent = new Intent(context, MainActivity.class);
        activityTestRule.launchActivity(intent);
    }

    @Test
    public void title_test(){
        launchMainActivity();

        onView(withText("Discover")).check(matches(isDisplayed()));
    }

    @Test
    public void restaurant_name_display(){
        launchMainActivity();

        onView(withText("A Good Morning Cafe")).check(matches(isDisplayed()));
        onView(withText("ShojiSushi")).check(matches(isDisplayed()));
        onView(withText("Pad")).check(matches(isDisplayed()));
        onView(withText("Buon Giorno")).check(matches(isDisplayed()));
        onView(withText("Golden Dragon")).check(matches(isDisplayed()));
        onView(withText("Bon me")).check(matches(isDisplayed()));
    }

    @Test
    public void favorite_restaurant_sorted_order(){
        launchMainActivity();

        onView(withRecyclerView(R.id.recycler_view).atPosition(0))
                .check(matches(
                        allOf(
                                hasDescendant(withText("A Good Morning Cafe")),
                                hasDescendant(withText("American, Breakfast & Brunch")),
                                hasDescendant(withText("48 mins")),
                                hasDescendant(withDrawable(R.drawable.ic_star_24dp))
                        )
                ));
        onView(withRecyclerView(R.id.recycler_view).atPosition(1))
                .check(matches(
                        allOf(
                                hasDescendant(withText("Pad")),
                                hasDescendant(withText("Thai")),
                                hasDescendant(withText("15 mins")),
                                hasDescendant(withDrawable(R.drawable.ic_star_24dp))
                        )
                ));
        onView(withRecyclerView(R.id.recycler_view).atPosition(2))
                .check(matches(
                        allOf(
                                hasDescendant(withText("ShojiSushi")),
                                hasDescendant(withText("Japanese")),
                                hasDescendant(withText("15 mins")),
                                hasDescendant(withDrawable(R.drawable.ic_star_24dp))
                        )
                ));
        onView(withRecyclerView(R.id.recycler_view).atPosition(3))
                .check(matches(
                        allOf(
                                hasDescendant(withText("Buon Giorno")),
                                hasDescendant(withText("Italian Cuisine")),
                                hasDescendant(withText("23 mins")),
                                hasDescendant(withDrawable(R.drawable.ic_star_border_24dp))
                        )
                ));
        onView(withRecyclerView(R.id.recycler_view).atPosition(4))
                .check(matches(
                        allOf(
                                hasDescendant(withText("Golden Dragon")),
                                hasDescendant(withText("Chinese")),
                                hasDescendant(withText("5 mins")),
                                hasDescendant(withDrawable(R.drawable.ic_star_border_24dp))
                        )
                ));

        onView(withRecyclerView(R.id.recycler_view).atPosition(5))
                .check(matches(
                        allOf(
                                hasDescendant(withText("Bon me")),
                                hasDescendant(withText("Vietnamese Sandwich")),
                                hasDescendant(withText("35 mins")),
                                hasDescendant(withDrawable(R.drawable.ic_star_border_24dp))
                        )
                ));
    }

    @Test
    public void add_favorite_restaurant() {
        launchMainActivity();

        onView(allOf(
                childWithId(
                        withRecyclerView(R.id.recycler_view).atPosition(4),
                        R.id.image_favorite
                ),
                isDisplayed()
        )).perform(click());

        onView(withRecyclerView(R.id.recycler_view).atPosition(4))
                .check(matches(
                        allOf(
                                hasDescendant(withText("Golden Dragon")),
                                hasDescendant(withText("Chinese")),
                                hasDescendant(withText("5 mins")),
                                hasDescendant(withDrawable(R.drawable.ic_star_24dp))
                        )
                ));
    }

    @Test
    public void remove_favorite_restaurant() {
        launchMainActivity();

        onView(allOf(
                childWithId(
                        withRecyclerView(R.id.recycler_view).atPosition(0),
                        R.id.image_favorite
                ),
                isDisplayed()
        )).perform(click());

        onView(withRecyclerView(R.id.recycler_view).atPosition(0))
                .check(matches(
                        allOf(
                                hasDescendant(withText("A Good Morning Cafe")),
                                hasDescendant(withText("American, Breakfast & Brunch")),
                                hasDescendant(withText("48 mins")),
                                hasDescendant(withDrawable(R.drawable.ic_star_border_24dp))
                        )
                ));
    }

    /* * * * * * * * * * * * * * * * * * * * * * * *
     *
     *          Utility Methods
     *
     * * * * * * * * * * * * * * * * * * * * * * * */

    public static RecyclerViewMatcher withRecyclerView(@IdRes int resId){
        return new RecyclerViewMatcher(resId);
    }

    private static Matcher<View> childWithId(
            final Matcher<View> parentMatcher, @IdRes final int resId) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child with id " + resId + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).findViewById(resId));
            }
        };
    }
}
