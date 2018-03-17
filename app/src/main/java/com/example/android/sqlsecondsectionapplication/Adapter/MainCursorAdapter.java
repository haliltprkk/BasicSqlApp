package com.example.android.sqlsecondsectionapplication.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.sqlsecondsectionapplication.Data.Contract;
import com.example.android.sqlsecondsectionapplication.R;


public class MainCursorAdapter extends CursorAdapter {

    private Context ctx;

    public MainCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
        this.ctx = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item_main, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView textViewPrice = (TextView) view.findViewById(R.id.price_text_view);
        TextView textViewProductName = (TextView) view.findViewById(R.id.product_name_text_view);
        final TextView textViewQuantity = (TextView) view.findViewById(R.id.quantity_text_view);
        int nameColumnIndex = cursor.getColumnIndex(Contract.InventoryFirstTable.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(Contract.InventoryFirstTable.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(Contract.InventoryFirstTable.COLUMN_QUANTITY);
        final String name = cursor.getString(nameColumnIndex);
        Integer price = cursor.getInt(priceColumnIndex);
        final Integer[] quantity = {cursor.getInt(quantityColumnIndex)};
        textViewPrice.setText(price + " $");
        textViewProductName.setText(name);
        textViewQuantity.setText(quantity[0] + "");

        Button reduceButton = (Button) view.findViewById(R.id.reduce_button);

        reduceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity[0] > 0) {
                    quantity[0] -= 1;
                    ContentValues datas = new ContentValues();
                    datas.put(Contract.InventoryFirstTable.COLUMN_QUANTITY, quantity[0]);
                    ctx.getContentResolver().update(Contract.InventoryFirstTable.CONTENT_URI, datas, Contract.InventoryFirstTable.COLUMN_PRODUCT_NAME + "=?", new String[]{name});
                    textViewQuantity.setText(quantity[0] + "");

                }

            }
        });


    }
}
