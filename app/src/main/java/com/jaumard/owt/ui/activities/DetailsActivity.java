package com.jaumard.owt.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.jaumard.owt.R;
import com.jaumard.owt.databinding.ActivityDetailsBinding;
import com.jaumard.owt.models.CatalogEntry;
import com.jaumard.owt.viewmodels.DetailsViewModel;

import javax.inject.Inject;

public class DetailsActivity extends LoggedActivity<DetailsViewModel> {
    public static final String KEY_ENTRY = "entry";
    public static final String KEY_NEED_SAVING = "saving";

    @Inject
    DetailsViewModel detailsViewModel;

    public static Intent getIntent(Context context, CatalogEntry catalogEntry, boolean needToBeSaved) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(KEY_ENTRY, catalogEntry);
        intent.putExtra(KEY_NEED_SAVING, needToBeSaved);
        return intent;
    }

    public static Intent getIntent(Context context, CatalogEntry catalogEntry) {
        return getIntent(context, catalogEntry, true);
    }

    private CatalogEntry entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        entry = getIntent().getParcelableExtra(KEY_ENTRY);
        binding.setEntry(entry);
        setTitle(entry.getTitle());

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activityComponent.inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getIntent().getBooleanExtra(KEY_NEED_SAVING, true)) {
            getViewModel().saveEntry(entry);
        }
    }

    @Override
    protected DetailsViewModel getViewModel() {
        return detailsViewModel;
    }
}
