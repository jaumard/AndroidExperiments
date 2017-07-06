package com.jaumard.owt.viewmodels;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextUtils;

import com.jaumard.owt.R;
import com.jaumard.owt.models.CatalogEntry;
import com.jaumard.owt.network.repositories.CatalogRepository;
import com.jaumard.owt.ui.adapter.ListBindableAdapter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.jaumard.owt.ui.activities.CatalogActivity.EVENT_CATALOG_ENTRY;

public class CatalogViewModel extends BaseViewModel {
    public ObservableField<List<CatalogEntry>> catalogEntries = new ObservableField<>();
    public ObservableBoolean isRequestPending = new ObservableBoolean();
    public ObservableInt genericError = new ObservableInt();
    private final ListBindableAdapter.OnClickListener<CatalogEntry> onItemClick = item ->
            navigationBus.onNext(new NavigationEvent<>(EVENT_CATALOG_ENTRY, item));

    @Inject
    CatalogRepository catalogRepository;

    private Disposable searchDisposable;
    private String lastQuery;

    public void search(String query) {
        if (TextUtils.isEmpty(query)) {
            discovery();
        } else {
            isRequestPending.set(true);
            cancelPendingRequest();
            lastQuery = query;
            searchDisposable = catalogRepository.search(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((entries, throwable) -> {
                        isRequestPending.set(false);
                        if (throwable == null) {
                            catalogEntries.set(entries);
                        } else {
                            genericError.set(R.string.error_network);
                            Timber.e(throwable);
                        }
                    });
            compositeDisposable.add(searchDisposable);
        }
    }

    private void cancelPendingRequest() {
        if (searchDisposable != null && !searchDisposable.isDisposed()) {
            searchDisposable.dispose();
            searchDisposable = null;
        }
    }

    public void discovery() {
        lastQuery = null;
        isRequestPending.set(true);
        cancelPendingRequest();
        searchDisposable = catalogRepository.discovery()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((entries, throwable) -> {
                    isRequestPending.set(false);
                    if (throwable == null) {
                        catalogEntries.set(entries);
                    } else {
                        genericError.set(R.string.error_network);
                        Timber.e(throwable);
                    }
                });
        compositeDisposable.add(searchDisposable);
    }

    @Override
    public void bind() {
        super.bind();
        List<CatalogEntry> entries = this.catalogEntries.get();
        if (entries == null || entries.isEmpty()) {
            discovery();
        }
    }

    public ListBindableAdapter.OnClickListener<CatalogEntry> getOnItemClick() {
        return onItemClick;
    }

    @Override
    public void inject() {
        viewModelComponent.inject(this);
    }

    public String getLastQuery() {
        return lastQuery;
    }
}
