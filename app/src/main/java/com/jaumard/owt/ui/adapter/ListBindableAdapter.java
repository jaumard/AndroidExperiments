package com.jaumard.owt.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.List;

public class ListBindableAdapter<T> extends RecyclerView.Adapter<ListBindableAdapter.BindingViewHolder> {
    private LayoutInflater inflater;
    private ObservableList<T> items;
    private OnClickListener<T> onClickListener;
    @SuppressWarnings("unchecked")
    private WeakReferenceOnListChangedCallback callback = new WeakReferenceOnListChangedCallback(this);
    private MultipleTypeItemBinder itemBinder;

    public ListBindableAdapter(List<T> items, MultipleTypeItemBinder itemBinder) {
        this.items = new ObservableArrayList<>();
        if (items != null) {
            this.items.addAll(items);
        }
        this.itemBinder = itemBinder;
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, itemBinder.getItemLayout(viewType), parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public int getItemViewType(int position) {
        return itemBinder.getItemType(items.get(position));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(itemBinder.getVariableId(), items.get(position));
        for (Pair<Integer, Object> data : itemBinder.getAdditionalVariables()) {
            binding.setVariable(data.first, data.second);
        }
        binding.getRoot().setTag(items.get(position));
        if (onClickListener != null) {
            binding.getRoot().setOnClickListener(view -> onClickListener.onClick((T) view.getTag()));
        }
        binding.executePendingBindings();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.items.addOnListChangedCallback(callback);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.items.removeOnListChangedCallback(callback);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<T> items) {
        this.items.removeOnListChangedCallback(callback);
        this.items.clear();
        this.items.addAll(items);
        this.items.addOnListChangedCallback(callback);
        notifyDataSetChanged();
    }

    public void setItemBinder(MultipleTypeItemBinder itemBinder) {
        this.itemBinder = itemBinder;
    }

    public static class BindingViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        BindingViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }

    public void setOnClickListener(OnClickListener<T> onClickListener) {
        this.onClickListener = onClickListener;
    }

    private static class WeakReferenceOnListChangedCallback<T> extends ObservableList.OnListChangedCallback {

        private final WeakReference<ListBindableAdapter<T>> adapterReference;

        public WeakReferenceOnListChangedCallback(ListBindableAdapter<T> bindingRecyclerViewAdapter) {
            this.adapterReference = new WeakReference<>(bindingRecyclerViewAdapter);
        }

        @Override
        public void onChanged(ObservableList sender) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeChanged(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeInserted(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemMoved(fromPosition, toPosition);
            }
        }

        @Override
        public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeRemoved(positionStart, itemCount);
            }
        }
    }

    public interface OnClickListener<T> {
        void onClick(T item);
    }
}
