package com.jaumard.owt.dagger.components;

import com.jaumard.owt.dagger.modules.ActivityModule;
import com.jaumard.owt.dagger.scopes.ActivityScope;
import com.jaumard.owt.ui.activities.BaseActivity;
import com.jaumard.owt.ui.activities.CatalogActivity;
import com.jaumard.owt.ui.activities.DetailsActivity;
import com.jaumard.owt.ui.activities.HistoryActivity;
import com.jaumard.owt.ui.activities.LoggedActivity;
import com.jaumard.owt.ui.activities.LoginActivity;
import com.jaumard.owt.viewmodels.BaseViewModel;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(LoginActivity loginActivity);

    void inject(DetailsActivity detailsActivity);

    void inject(CatalogActivity catalogActivity);

    void inject(HistoryActivity historyActivity);

    void inject(LoggedActivity<BaseViewModel> loggedActivity);

    void inject(BaseActivity loggedActivity);
}
