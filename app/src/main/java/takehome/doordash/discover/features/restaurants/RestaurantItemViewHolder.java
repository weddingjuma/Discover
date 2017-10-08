package takehome.doordash.discover.features.restaurants;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import takehome.doordash.discover.R;
import takehome.doordash.discover.model.restaurant.Restaurant;

/**
 * Created by jc on 10/6/17.
 */

public class RestaurantItemViewHolder extends RecyclerView.ViewHolder {

    interface ClickListener {
        void onFavoriteClicked(int position);
    }

    @BindView(R.id.image_logo)
    public ImageView imageLogo;

    @BindView(R.id.text_name)
    public TextView textName;

    @BindView(R.id.text_type)
    public TextView textType;

    @BindView(R.id.image_favorite)
    public AppCompatImageButton buttonFavorite;

    @BindView(R.id.text_status)
    public TextView textStatus;

    public boolean isFavorite;

    RestaurantItemViewHolder(View itemView, @Nullable final ClickListener clickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        if (clickListener != null){
            buttonFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() > -1) {
                        clickListener.onFavoriteClicked(getAdapterPosition());
                    }
                }
            });
        }
    }

    void bind(@NonNull Restaurant data){
        isFavorite = data.isFavorite;
        textName.setText(data.name);
        textType.setText(data.description);
        buttonFavorite.setImageResource(data.isFavorite
                ? R.drawable.ic_star_24dp
                : R.drawable.ic_star_border_24dp);
        textStatus.setText(data.status);
        if (data.coverImageUrl != null) {
            Picasso.with(itemView.getContext())
                    .load(data.coverImageUrl)
                    .placeholder(android.R.color.darker_gray)
                    .error(android.R.color.darker_gray)
                    .fit()
                    .centerCrop()
                    .into(imageLogo);
        } else {
            Picasso.with(itemView.getContext())
                    .load(R.drawable.ic_menu_share)
                    .placeholder(android.R.color.darker_gray)
                    .into(imageLogo);
        }
    }
}
