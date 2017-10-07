package takehome.doordash.discover.features.restaurants;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import takehome.doordash.discover.R;
import takehome.doordash.discover.model.restaurant.Restaurant;

/**
 * Created by jc on 10/6/17.
 */

public class RestaurantItemViewAdapter extends RecyclerView.Adapter<RestaurantItemViewHolder> {

    private List<Restaurant> items;

    private PublishSubject<Restaurant> favoriteClickSub;

    public RestaurantItemViewAdapter(){
        items = new ArrayList<>();
        favoriteClickSub = PublishSubject.create();
    }

    public void setItems(@NonNull List<Restaurant> restaurants){
        items.clear();
        items.addAll(restaurants);
    }

    public Observable<Restaurant> observeFavoriteChanged(){
        return favoriteClickSub;
    }

    @Override
    public RestaurantItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_restaurant_item, parent, false);
        return new RestaurantItemViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(RestaurantItemViewHolder holder, int position) {
        if (position > -1 && position < items.size()) {
            Restaurant item = items.get(position);
            holder.bind(item);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private RestaurantItemViewHolder.ClickListener clickListener = new RestaurantItemViewHolder.ClickListener() {
        @Override
        public void onFavoriteClicked(int position) {
            Restaurant item = items.get(position);
            item.isFavorite = !item.isFavorite;
            notifyItemChanged(position);
            favoriteClickSub.onNext(item);
        }
    };
}
