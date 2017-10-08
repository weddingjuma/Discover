package takehome.doordash.discover.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.ImageView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;


/**
 *
 * DrawableMatcher : perform actions on an item at a specific position in a RecyclerView
 *
 * @author https://github.com/dbottillo
 * @link : https://github.com/dbottillo/Blog/blob/espresso_match_imageview/app/src/androidTest/java/com/danielebottillo/blog/config/DrawableMatcher.java
 *
 * Created by dannyroa on 5/10/15.
 */
public class DrawableMatcher extends TypeSafeMatcher<View> {

    private final int expectedId;
    private String resourceName;
    static final int MATCH_MODE_EMPTY = -1;
    static final int MATCH_MODE_ANY = -2;

    DrawableMatcher(int expectedId) {
        super(View.class);
        this.expectedId = expectedId;
    }
    public static Matcher<View> withDrawable(final int resourceId){
        return new DrawableMatcher(resourceId);
    }

    public static Matcher<View> noDrawable(){
        return new DrawableMatcher(MATCH_MODE_EMPTY);
    }

    public static Matcher<View> anyDrawable(){
        return new DrawableMatcher(MATCH_MODE_ANY);
    }

    @Override
    protected boolean matchesSafely(View target) {
        if (!(target instanceof ImageView)) {
            return false;
        }
        ImageView imageView = (ImageView) target;
        if (expectedId == MATCH_MODE_EMPTY) {
            return imageView.getDrawable() == null;
        }
        if (expectedId == MATCH_MODE_ANY) {
            return imageView.getDrawable() != null;
        }
        Resources resources = target.getContext().getResources();
        Drawable expectedDrawable = ResourcesCompat.getDrawable(resources, expectedId, null);
        resourceName = resources.getResourceEntryName(expectedId);

        if (expectedDrawable == null) {
            return false;
        }

        Bitmap bitmap = getBitmap(imageView.getDrawable());
        Bitmap otherBitmap = getBitmap(expectedDrawable);
        return bitmap != null && bitmap.sameAs(otherBitmap);
    }

    @Nullable
    private Bitmap getBitmap(@Nullable Drawable drawable) {
        if (drawable == null ||
                drawable.getIntrinsicHeight() <= 0 ||
                drawable.getIntrinsicWidth() <= 0){
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with drawable from resource id: ");
        description.appendValue(expectedId);
        if (resourceName != null) {
            description.appendText("[");
            description.appendText(resourceName);
            description.appendText("]");
        }
    }
}