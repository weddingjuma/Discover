package takehome.doordash.discover.model.restaurant;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jc on 10/6/17.
 */

public class Restaurant {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    @NonNull
    public String name;

    @SerializedName("description")
    @NonNull
    public String description;

    @SerializedName("cover_img_url")
    @Nullable
    public String coverImageUrl;

    @SerializedName("status")
    @NonNull
    public String status;

    @SerializedName("delivery_fee")
    public int deliveryFeeCents;

    public boolean isFavorite;
}
