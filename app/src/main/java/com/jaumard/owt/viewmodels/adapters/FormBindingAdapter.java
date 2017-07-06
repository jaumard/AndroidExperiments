package com.jaumard.owt.viewmodels.adapters;

import android.databinding.BindingAdapter;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Toast;

public final class FormBindingAdapter {
    private FormBindingAdapter() {
    }

    @BindingAdapter("error")
    public static void setError(TextInputEditText textInputEditText, int error) {
        textInputEditText.setError(null);
        if (error != 0) {
            textInputEditText.setError(textInputEditText.getContext().getString(error));
        }
    }

    @BindingAdapter("toast")
    public static void showToast(View view, int error) {
        if (error != 0) {
            Toast.makeText(view.getContext(), error, Toast.LENGTH_LONG).show();
        }
    }
}
