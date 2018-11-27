package com.example.android.inventoryapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.example.android.inventoryapp.R;
import com.example.android.inventoryapp.data.InventoryContract;
import com.example.android.inventoryapp.databinding.ItemProductListBinding;

import androidx.databinding.DataBindingUtil;


/**
 * An adapter which is responsible to inflate view for each row of product list.
 * View is provided here and data is passed by host of this adapter.
 * {@link IproductCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of product data as its data source. This adapter knows
 * how to create list items for each row of product data in the {@link Cursor}.
 */
public class IproductCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link IproductCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public IproductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_product_list, parent, false);
    }

    /**
     * This method binds the product data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current product can be set on the
     * product name TextView in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Note: 11/27/2018 by sagar  First we must ask for existing binding otherwise it will give an error
        // for view must have a tag.
        // Such error happens when we try to bind view which has already binding connected with it.
        /*https://stackoverflow.com/questions/41320627/android-databinding-view-tag-isnt-correct-on-viewnull*/
        // Note: 11/27/2018 by sagar  Data binding
        ItemProductListBinding binding = DataBindingUtil.getBinding(view);

        if (binding == null) {
            binding = ItemProductListBinding.bind(view);
        }

        // Note: 11/25/2018 by sagar  Get the column indices for values
        int columnProductName = cursor.getColumnIndex(InventoryContract.ProductEntry.COLUMN_PRODUCT_NAME);
        int columnUnitPrice = cursor.getColumnIndex(InventoryContract.ProductEntry.COLUMN_UNIT_PRICE);
        int columnQuantity = cursor.getColumnIndex(InventoryContract.ProductEntry.COLUMN_QUANTITY);
        int columnTotalPrice = cursor.getColumnIndex(InventoryContract.ProductEntry.COLUMN_TOTAL_PRICE);
        int columnSupplier = cursor.getColumnIndex(InventoryContract.ProductEntry.COLUMN_SUPPLIER_NAME);
        int columnSupplierPhone = cursor.getColumnIndex(InventoryContract.ProductEntry.COLUMN_SUPPLIER_PHONE_NUMBER);

        // Note: 11/25/2018 by sagar  Use column indices to retrieve values
        String productName = cursor.getString(columnProductName);
        float unitPrice = cursor.getFloat(columnUnitPrice);
        int quantities = cursor.getInt(columnQuantity);
        float totalPrice = cursor.getFloat(columnTotalPrice);
        String supplier = cursor.getString(columnSupplier);
        String supplierPhone = cursor.getString(columnSupplierPhone);

        if (supplierPhone.isEmpty()) {
            supplierPhone = context.getResources().getString(R.string.label_not_available);
        }

        // Note: 11/25/2018 by sagar  set values to view
        binding.tvName.setText(productName);
        binding.tvPrice.setText(String.valueOf(unitPrice));
        binding.includeLayoutQuantity.tvQuantity.setText(String.valueOf(quantities));
    }
}
