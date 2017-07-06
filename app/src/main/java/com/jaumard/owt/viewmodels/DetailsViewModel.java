package com.jaumard.owt.viewmodels;

import com.jaumard.owt.models.CatalogEntry;
import com.jaumard.owt.network.repositories.CatalogRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DetailsViewModel extends BaseViewModel {
    @Inject
    CatalogRepository catalogRepository;

    public void saveEntry(CatalogEntry catalogEntry) {
        compositeDisposable.add(catalogRepository.insertOrUpdate(catalogEntry)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe());
    }

    @Override
    public void inject() {
        this.viewModelComponent.inject(this);
    }
}
