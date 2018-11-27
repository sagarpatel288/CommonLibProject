package com.example.android.inventoryapp.catalog.ui;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.android.inventoryapp.R;
import com.example.android.inventoryapp.adapters.IproductCursorAdapter;
import com.example.android.inventoryapp.addEditDetail.ui.AddEditDetailActivity;
import com.example.android.inventoryapp.base.BaseActivity;
import com.example.android.inventoryapp.data.DbHelper;
import com.example.android.inventoryapp.data.InventoryContract.ProductEntry;
import com.example.android.inventoryapp.databinding.ActivityProductListBinding;
import com.library.android.common.appconstants.AppConstants;

import androidx.databinding.DataBindingUtil;

public class ProductListActivity extends BaseActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PRODUCT_LOADER = 1;
    private DbHelper dbHelper;
    private int dataCount = 1;
    private float unitPrice = 10.0f;
    private IproductCursorAdapter cursorAdapter;
    /**
     * Must have one unique binding. Do not make more than one instance of binding or else it can give unexpected result.
     * Such as, no click listener!
     */
    private ActivityProductListBinding binding;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_list;
    }

    @Override
    protected void onViewStubInflated(View inflatedView, Bundle savedInstancSate) {
        binding = DataBindingUtil.bind(inflatedView);
        initTextView();
        if (binding != null) {
            binding.fab.setOnClickListener(v -> insertDummyData());
        }
    }

    @Override
    protected void initControllers() {

    }

    @Override
    protected void handleViews() {

    }

    @Override
    protected void setListeners() {
        setListView();
    }

    private void setListView() {
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProductListActivity.this, AddEditDetailActivity.class);
                intent.setData(ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id));
                startActivity(intent);
            }
        });
        binding.listView.setAdapter(cursorAdapter);
        binding.listView.setEmptyView(binding.tvNoDataMsg);
        // Note: 11/25/2018 by sagar  Adapter for list view that is responsible for each row of product list item
        // Note: 11/25/2018 by sagar  Passing null because we have not initialized our cursor loader yet.
        // The adapter will have cursor once loader gets ready {@link onLoadFinished()}
        cursorAdapter = new IproductCursorAdapter(this, null);
        // Note: 11/25/2018 by sagar  Initializing the loader
        getLoaderManager().initLoader(PRODUCT_LOADER, null, this);
    }

    @Override
    protected void onGetConnectionState(boolean isConnected) {

    }

    private void initTextView() {
        binding.tvNoDataMsg.setText(getString(R.string.label_tap_floating_bottom_button_to_add_dummy_data));
    }

    /**
     * Insert dummy data on click of fab
     *
     * @since 1.0
     */
    private void insertDummyData() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductEntry.COLUMN_PRODUCT_NAME, "product: " + dataCount);
        contentValues.put(ProductEntry.COLUMN_UNIT_PRICE, unitPrice);
        contentValues.put(ProductEntry.COLUMN_QUANTITY, dataCount);
        contentValues.put(ProductEntry.COLUMN_TOTAL_PRICE, unitPrice * dataCount);
        contentValues.put(ProductEntry.COLUMN_SUPPLIER_NAME, "Sagar Patel: " + dataCount);
        contentValues.put(ProductEntry.COLUMN_SUPPLIER_PHONE_NUMBER, "+91-7405763413");

        Uri uri = getContentResolver().insert(ProductEntry.CONTENT_URI, contentValues);
        long id = ContentUris.parseId(uri);

        if (AppConstants.NULL != id) {
            Toast.makeText(this, getString(R.string.msg_data_has_been_saved), Toast.LENGTH_SHORT).show();
            dataCount++;
        } else {
            Toast.makeText(this, getString(R.string.error_msg_something_went_wrong_while_saving_data), Toast.LENGTH_SHORT).show();
        }

        readData();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the product database.
     */
    private void readData() {
        // Note: 11/24/2018 by sagar:  We create the new instance of dbHelper only if it is not available currently
        if (dbHelper == null) {
            // To access our database, we instantiate our subclass of SQLiteOpenHelper
            // and pass the context, which is the current activity.
            dbHelper = new DbHelper(this);
        }

        // Note: 11/24/2018 by sagar  Local database variable because we don't want this heavy variable to roam here and there without actual need.
        // We want this database variable only during any database operation.
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        // Note: 11/24/2018 by sagar  All values except table name are null because we don't want to add any filter and we want simply all available data
        /*try (Cursor cursor = database.query(ProductEntry.TABLE_NAME, null, null, null, null, null, null)) {
//            setData(cursor);
        }*/
        Cursor cursor = database.query(ProductEntry.TABLE_NAME, null, null, null, null, null, null);
        setList(cursor);
    }

    private void setList(Cursor cursor) {
        IproductCursorAdapter cursorAdapter = new IproductCursorAdapter(this, cursor);
        binding.listView.setAdapter(cursorAdapter);
    }

    private void setData(Cursor cursor) {
        binding.tvNoDataMsg.setText(String.format("%s: %s\n\n", getString(R.string.label_total_rows), String.valueOf(cursor.getCount())));
        // Figure out the index of each column
        try {
            int columnIdIndex = cursor.getColumnIndex(ProductEntry._ID);
            int columnProductNameIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
            int columnProductPriceIndex = cursor.getColumnIndex(ProductEntry.COLUMN_TOTAL_PRICE);
            int columnProductQuantityIndex = cursor.getColumnIndex(ProductEntry.COLUMN_QUANTITY);
            int columnProductSupplierName = cursor.getColumnIndex(ProductEntry.COLUMN_SUPPLIER_NAME);
            int columnSupplierPhone = cursor.getColumnIndex(ProductEntry.COLUMN_SUPPLIER_PHONE_NUMBER);

            // Use that index to extract the String or Int value of the word
            // at the current row the cursor is on.
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            while (cursor.moveToNext()) {
                binding.tvNoDataMsg.append(String.format("%s: %s\n%s: %s\n%s: %s\n%s: %s\n%s: %s\n%s: %s\n\n",
                        ProductEntry._ID, cursor.getInt(columnIdIndex),
                        ProductEntry.COLUMN_PRODUCT_NAME, cursor.getString(columnProductNameIndex),
                        ProductEntry.COLUMN_TOTAL_PRICE, cursor.getFloat(columnProductPriceIndex),
                        ProductEntry.COLUMN_QUANTITY, cursor.getInt(columnProductQuantityIndex),
                        ProductEntry.COLUMN_SUPPLIER_NAME, cursor.getString(columnProductSupplierName),
                        ProductEntry.COLUMN_SUPPLIER_PHONE_NUMBER, cursor.getString(columnSupplierPhone)
                ));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String projection[] = new String[]{ProductEntry._ID,
                ProductEntry.COLUMN_PRODUCT_NAME,
                ProductEntry.COLUMN_UNIT_PRICE,
                ProductEntry.COLUMN_QUANTITY,
                ProductEntry.COLUMN_TOTAL_PRICE,
                ProductEntry.COLUMN_SUPPLIER_NAME,
                ProductEntry.COLUMN_SUPPLIER_PHONE_NUMBER
        };

        return new CursorLoader(this, ProductEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {
        cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> cursor) {
        cursorAdapter.swapCursor(null);
    }
}
