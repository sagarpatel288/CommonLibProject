package com.example.android.inventoryapp.addEditDetail.ui;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.android.inventoryapp.R;
import com.example.android.inventoryapp.base.BaseActivity;

public class AddEditDetailActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Uri contentUri;
    private static final int PRODUCT_LOADER = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_edit_detail;
    }

    @Override
    protected void onViewStubInflated(View inflatedView, Bundle savedInstancSate) {

    }

    @Override
    protected void initControllers() {
        if (getIntent() != null){
            if (getIntent().getData() != null){
                contentUri = getIntent().getData();
            }
        }
    }

    @Override
    protected void handleViews() {
        if (contentUri != null){
            setData(contentUri);
        }
    }

    private void setData(Uri contentUri) {

    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void onGetConnectionState(boolean isConnected) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
