package com.jaumard.owt.ui.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.jaumard.owt.R;
import com.jaumard.owt.databinding.ActivityHistoryBinding;
import com.jaumard.owt.models.CatalogEntry;
import com.jaumard.owt.viewmodels.HistoryViewModel;

import javax.inject.Inject;

import static com.jaumard.owt.ui.activities.CatalogActivity.EVENT_CATALOG_ENTRY;

public class HistoryActivity extends LoggedActivity<HistoryViewModel> {
    @Inject
    HistoryViewModel historyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHistoryBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_history);

        activityComponent.inject(this);

        binding.setViewModel(getViewModel());

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void goToCatalogEntry(CatalogEntry catalogEntry) {
        startActivity(DetailsActivity.getIntent(this, catalogEntry, false));
    }

    @Override
    protected void onResume() {
        super.onResume();
        compositeDisposable.add(historyViewModel.getNavigationBus()
                .doOnNext(event -> {
                    if (EVENT_CATALOG_ENTRY.equals(event.getType())) {
                        goToCatalogEntry((CatalogEntry) event.getData());
                    }
                })
                .subscribe());
    }

    @Override
    public void onStart() {
        super.onStart();
        getViewModel().getHistory();
    }

    @Override
    protected HistoryViewModel getViewModel() {
        return historyViewModel;
    }
}
