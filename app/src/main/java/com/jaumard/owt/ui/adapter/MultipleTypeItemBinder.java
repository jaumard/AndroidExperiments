package com.jaumard.owt.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class MultipleTypeItemBinder {
    protected static final int TYPE_NONE = 0;
    @LayoutRes
    protected final int layoutId;
    protected final int variableId;
    protected final List<Pair<Integer, Object>> additionalVariables;

    public MultipleTypeItemBinder(int variableId) {
        this.layoutId = 0;
        this.variableId = variableId;
        this.additionalVariables = new ArrayList<>();
    }

    public MultipleTypeItemBinder(@LayoutRes int layoutId, int variableId) {
        this.layoutId = layoutId;
        this.variableId = variableId;
        this.additionalVariables = new ArrayList<>();
    }

    public MultipleTypeItemBinder(int variableId, List<Pair<Integer, Object>> additionalVariables) {
        this.layoutId = 0;
        this.variableId = variableId;
        this.additionalVariables = additionalVariables;
    }

    public MultipleTypeItemBinder(@LayoutRes int layoutId, int variableId, List<Pair<Integer, Object>> additionalVariables) {
        this.layoutId = layoutId;
        this.variableId = variableId;
        this.additionalVariables = additionalVariables;
    }

    @LayoutRes
    public int getLayoutId() {
        return layoutId;
    }

    public int getVariableId() {
        return variableId;
    }

    public List<Pair<Integer, Object>> getAdditionalVariables() {
        return additionalVariables;
    }

    public abstract int getItemType(Object item);

    @LayoutRes
    public abstract int getItemLayout(int itemType);
}
