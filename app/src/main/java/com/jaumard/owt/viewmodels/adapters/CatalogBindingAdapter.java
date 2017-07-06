package com.jaumard.owt.viewmodels.adapters;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;

public final class CatalogBindingAdapter {
    private CatalogBindingAdapter() {
    }

    @BindingAdapter("refreshing")
    public static void setRefreshing(SwipeRefreshLayout swipeRefreshLayout, boolean isRefreshing) {
        swipeRefreshLayout.setRefreshing(isRefreshing);
    }

}
