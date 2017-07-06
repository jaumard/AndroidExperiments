package com.jaumard.owt.viewmodels.adapters;

import android.databinding.BindingAdapter;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;

import com.jaumard.owt.BR;
import com.jaumard.owt.ui.adapter.ItemBinder;
import com.jaumard.owt.ui.adapter.ListBindableAdapter;
import com.jaumard.owt.ui.adapter.MultipleTypeItemBinder;

import java.util.List;

public final class RecyclerViewBindingAdapter {
    private RecyclerViewBindingAdapter() {
    }

    @BindingAdapter({"items", "itemLayout", "onItemClick"})
    public static <T> void setRecyclerItems(RecyclerView recyclerView, List<T> items, @LayoutRes int itemLayout,
                                            ListBindableAdapter.OnClickListener<T> onItemClick) {
        setRecyclerItems(recyclerView, items, new ItemBinder(itemLayout, BR.data), onItemClick);
    }

    @SuppressWarnings("unchecked")
    public static <T> void setRecyclerItems(RecyclerView recyclerView, List<T> items, MultipleTypeItemBinder itemBinder, ListBindableAdapter.OnClickListener<T> listener) {
        ListBindableAdapter<T> adapter = (ListBindableAdapter<T>) recyclerView.getAdapter();

        if (adapter == null) {
            adapter = new ListBindableAdapter<>(items, itemBinder);
            adapter.setOnClickListener(listener);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setOnClickListener(listener);
            adapter.setItemBinder(itemBinder);
            adapter.setItems(items);
        }
    }
}
