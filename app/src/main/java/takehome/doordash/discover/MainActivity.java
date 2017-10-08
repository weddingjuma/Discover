package takehome.doordash.discover;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import takehome.doordash.discover.features.restaurants.RestaurantItemViewAdapter;
import takehome.doordash.discover.features.restaurants.RestaurantViewModel;
import takehome.doordash.discover.model.restaurant.Restaurant;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final static String TAG = MainActivity.class.getSimpleName();

    private final static double DOOR_DASH_HQ_LAT = 37.422740;
    private final static double DOOR_DASH_HQ_LONG = -122.139956;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.loading_layout)
    View loadingLayout;

    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;

    RestaurantViewModel viewModel;

    private RestaurantItemViewAdapter adapter;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this, this);

        // Drawer Setup
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // Data Model
        viewModel = ViewModelProviders.of(this).get(RestaurantViewModel.class);
        Discover.injectionGraph().inject(viewModel);
        adapter = new RestaurantItemViewAdapter();

        // Recycler View setup
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        /*
         * Register favorite restaurant changes and invalidate the cached list
         * of restaurants inside ViewModel.
         */
        disposables.add(
                adapter.observeFavoriteChanged()
                        .subscribe(new Consumer<Restaurant>() {
                    @Override
                    public void accept(Restaurant restaurant) throws Exception {
                            Completable action;
                            if (restaurant.isFavorite) {
                                action = viewModel.saveFavorite(restaurant);
                            } else {
                                action = viewModel.removeFavorite(restaurant);
                            }
                            disposables.add(action.subscribe());
                        }
                })
        );
        disposables.add(loadRestaurants(false));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Load restaurant list.
        disposables.add(loadRestaurants(false));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel all pending actions.
        disposables.dispose();
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *      Click Callbacks
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                viewModel.clearRestaurantCache();
                disposables.add(loadRestaurants(true));
                break;
            case R.id.nav_gallery:
            case R.id.nav_manage:
            case R.id.nav_share:
            case R.id.nav_send:
        }
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *      Data Methods
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private Disposable loadRestaurants(final boolean scrollToTopAfterLoaded){
        return viewModel.getRestaurants(DOOR_DASH_HQ_LAT, DOOR_DASH_HQ_LONG, true)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showLoading();
                    }
                })
                .doOnEvent(new BiConsumer<List<Restaurant>, Throwable>() {
                    @Override
                    public void accept(List<Restaurant> restaurants, Throwable throwable) throws Exception {
                        hideLoading();
                    }
                })
                .subscribe(new Consumer<List<Restaurant>>() {
                    @Override
                    public void accept(List<Restaurant> restaurants) throws Exception {
                        adapter.setItems(restaurants);
                        adapter.notifyDataSetChanged();
                        if (scrollToTopAfterLoaded) {
                            recyclerView.scrollToPosition(0);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "Error fetching restaurants : " + throwable.getMessage());
                        throwable.printStackTrace();
                        Toast.makeText(MainActivity.this,
                                "Cannot fetch restaurants\nPlease refresh",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *      UI Methods
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private void showLoading(){
        loadingLayout.setVisibility(View.VISIBLE);
    }

    private void hideLoading(){
        loadingLayout.setVisibility(View.GONE);
    }
}
