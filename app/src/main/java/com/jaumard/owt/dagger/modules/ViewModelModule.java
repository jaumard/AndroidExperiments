package com.jaumard.owt.dagger.modules;

import com.jaumard.owt.viewmodels.BaseViewModel;

import dagger.Module;

@Module
public class ViewModelModule {
    private final BaseViewModel viewModel;

    public ViewModelModule(BaseViewModel viewModel) {
        this.viewModel = viewModel;
    }

}
