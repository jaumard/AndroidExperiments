package com.jaumard.owt.ui.adapter;

import android.support.annotation.IntegerRes;
import android.support.annotation.LayoutRes;
import android.support.v4.util.Pair;

import java.util.List;

public class ItemBinder extends MultipleTypeItemBinder {

    public ItemBinder(int variableId) {
        super(variableId);
    }

    public ItemBinder(@LayoutRes int layoutId, int variableId) {
        super(layoutId, variableId);
    }

    public ItemBinder(@IntegerRes int variableId, List<Pair<Integer, Object>> additionalVariables) {
        super(variableId, additionalVariables);
    }

    public ItemBinder(@LayoutRes int layoutId, @IntegerRes int variableId, List<Pair<Integer, Object>> additionalVariables) {
        super(layoutId, variableId, additionalVariables);
    }

    @LayoutRes
    @Override
    public int getLayoutId() {
        return layoutId;
    }

    @Override
    public int getVariableId() {
        return variableId;
    }

    @Override
    public List<Pair<Integer, Object>> getAdditionalVariables() {
        return additionalVariables;
    }

    @Override
    public int getItemType(Object item) {
        return TYPE_NONE;
    }

    @Override
    @LayoutRes
    public int getItemLayout(int itemType) {
        return layoutId;
    }
}
