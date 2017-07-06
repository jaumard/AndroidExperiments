package com.jaumard.owt.ui.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.jaumard.owt.R;
import com.jaumard.owt.databinding.ActivityCatalogBinding;
import com.jaumard.owt.models.CatalogEntry;
import com.jaumard.owt.viewmodels.CatalogViewModel;

import javax.inject.Inject;

import timber.log.Timber;

public class CatalogActivity extends LoggedActivity<CatalogViewModel> {
    public static final String EVENT_CATALOG_ENTRY = "EVENT_CATALOG_ENTRY";
    @Inject
    CatalogViewModel catalogViewModel;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, CatalogActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCatalogBinding catalogBinding = DataBindingUtil.setContentView(this, R.layout.activity_catalog);
        activityComponent.inject(this);
        catalogBinding.setViewModel(getViewModel());

        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */
        catalogBinding.swipeRefresh.setOnRefreshListener(
                () -> {
                    Timber.i("onRefresh called from SwipeRefreshLayout");
                    getViewModel().search(getViewModel().getLastQuery());
                }
        );

    }

    @Override
    protected CatalogViewModel getViewModel() {
        return catalogViewModel;
    }

    @Override
    protected void onResume() {
        super.onResume();

        compositeDisposable.add(catalogViewModel.getNavigationBus()
                .doOnNext(event -> {
                    if (EVENT_CATALOG_ENTRY.equals(event.getType())) {
                        goToCatalogEntry((CatalogEntry) event.getData());
                    }
                })
                .subscribe());
    }

    private void goToCatalogEntry(CatalogEntry catalogEntry) {
        startActivity(DetailsActivity.getIntent(this, catalogEntry));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        // Associate searchable configuration with the SearchView
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        String lastQuery = getViewModel().getLastQuery();
        if (!TextUtils.isEmpty(lastQuery)) {
            MenuItemCompat.expandActionView(menuItem);
            searchView.setQuery(lastQuery, false);
        }

        searchView.setOnCloseListener(() -> {
            catalogViewModel.discovery();
            return true;
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                catalogViewModel.search(query);
                InputMethodManager imMethod = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imMethod.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText) || newText.length() >= 3) {
                    catalogViewModel.search(newText);
                    return true;
                }
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            firebaseAuth.signOut();
        } else if (item.getItemId() == R.id.history) {
            goToHistory();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToHistory() {
        startActivity(new Intent(this, HistoryActivity.class));
    }
}
