package com.jaumard.owt.dagger.components;

import com.jaumard.owt.dagger.modules.ViewModelModule;
import com.jaumard.owt.viewmodels.BaseViewModel;
import com.jaumard.owt.viewmodels.CatalogViewModel;
import com.jaumard.owt.viewmodels.DetailsViewModel;
import com.jaumard.owt.viewmodels.HistoryViewModel;
import com.jaumard.owt.viewmodels.LoginViewModel;

import dagger.Subcomponent;

@Subcomponent(modules = {ViewModelModule.class})
public interface ViewModelComponent {
    void inject(CatalogViewModel catalogViewModel);

    void inject(LoginViewModel loginViewModel);

    void inject(DetailsViewModel detailsViewModel);

    void inject(HistoryViewModel detailsViewModel);

    void inject(BaseViewModel baseViewModel);
}
