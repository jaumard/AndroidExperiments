package com.jaumard.owt.viewmodels;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.jaumard.owt.R;
import com.jaumard.owt.models.CatalogEntry;
import com.jaumard.owt.network.repositories.CatalogRepository;
import com.jaumard.owt.ui.adapter.ListBindableAdapter;

import java.util.List;

import javax.inject.Inject;

import static com.jaumard.owt.ui.activities.CatalogActivity.EVENT_CATALOG_ENTRY;

public class HistoryViewModel extends BaseViewModel {
    @Inject
    CatalogRepository catalogRepository;
    public ObservableField<List<CatalogEntry>> catalogEntries = new ObservableField<>();
    public ObservableBoolean isRequestPending = new ObservableBoolean();
    public ObservableInt genericError = new ObservableInt(0);
    private final ListBindableAdapter.OnClickListener<CatalogEntry> onItemClick = item ->
            navigationBus.onNext(new NavigationEvent<>(EVENT_CATALOG_ENTRY, item));

    @Override
    public void inject() {
        this.viewModelComponent.inject(this);
    }

    public void getHistory() {
        isRequestPending.set(true);
        compositeDisposable.add(catalogRepository.getHistory().subscribe((entries, throwable) -> {
                    isRequestPending.set(false);
                    if (throwable == null) {
                        this.catalogEntries.set(entries);
                    } else {
                        genericError.set(R.string.error_database);
                    }
                })
        );
    }

    public ListBindableAdapter.OnClickListener<CatalogEntry> getOnItemClick() {
        return onItemClick;
    }
}
