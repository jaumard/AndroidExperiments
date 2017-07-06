package com.jaumard.owt.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.jaumard.owt.CatalogApplication;
import com.jaumard.owt.dagger.components.ViewModelComponent;
import com.jaumard.owt.dagger.modules.ViewModelModule;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

public abstract class BaseViewModel extends ViewModel {
    protected PublishSubject<NavigationEvent> navigationBus = PublishSubject.create();
    protected CompositeDisposable compositeDisposable;
    protected ViewModelComponent viewModelComponent;

    public BaseViewModel() {
        viewModelComponent = CatalogApplication.getAppComponent().plusViewModelComponent(getViewModelModule());
        viewModelComponent.inject(this);
    }

    public abstract void inject();

    @NonNull
    private ViewModelModule getViewModelModule() {
        return new ViewModelModule(this);
    }

    @CallSuper
    public void bind() {
        compositeDisposable = new CompositeDisposable();
        inject();
    }

    @CallSuper
    public void unbind() {
        compositeDisposable.dispose();
    }

    public static class NavigationEvent<T> {
        private final String type;
        private final T data;

        public NavigationEvent(String type) {
            this.type = type;
            this.data = null;
        }

        public NavigationEvent(String type, T data) {
            this.data = data;
            this.type = type;
        }

        public T getData() {
            return data;
        }

        public String getType() {
            return type;
        }
    }

    public PublishSubject<NavigationEvent> getNavigationBus() {
        return navigationBus;
    }
}
