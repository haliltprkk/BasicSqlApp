package com.example.android.sqlsecondsectionapplication;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.sqlsecondsectionapplication.Data.Contract;

public class AddActivity extends AppCompatActivity {
    EditText editTextProductName;
    EditText editTextPrice;
    EditText editTextQuantity;
    EditText editTextSupplierName;
    EditText editTextSupplierEmail;
    EditText editTextSupplierPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);
        editTextProductName = (EditText) findViewById(R.id.add_product_name_edit_text);
        editTextPrice = (EditText) findViewById(R.id.add_price_edit_text);
        editTextQuantity = (EditText) findViewById(R.id.add_quantity_edit_text);
        editTextSupplierName = (EditText) findViewById(R.id.add_supplier_name_edit_text);
        editTextSupplierEmail = (EditText) findViewById(R.id.add_supplier_email_edit_text);
        editTextSupplierPhone = (EditText) findViewById(R.id.add_supplier_phone_number_edit_text);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_inventory, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_icon_item) {
            insertData();

        }
        return true;

    }

    private void insertData() {
        if (editTextPrice.getText().toString().trim().isEmpty() || editTextProductName.getText().toString().trim().isEmpty() || editTextQuantity.getText().toString().trim().isEmpty() || editTextSupplierName.getText().toString().trim().isEmpty() || editTextSupplierPhone.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "All information is needed.", Toast.LENGTH_SHORT).show();

        } else if (isValidEmail(editTextSupplierEmail.getText().toString().trim()) == false) {
            Toast.makeText(this, "You need to right your mail address correctly.", Toast.LENGTH_SHORT).show();

        } else {

            ContentValues datas = new ContentValues();
            datas.put(Contract.InventoryFirstTable.COLUMN_PRICE, Integer.parseInt(editTextPrice.getText().toString()));
            datas.put(Contract.InventoryFirstTable.COLUMN_PRODUCT_NAME, editTextProductName.getText().toString());
            datas.put(Contract.InventoryFirstTable.COLUMN_QUANTITY, Integer.parseInt(editTextQuantity.getText().toString()));
            datas.put(Contract.InventoryFirstTable.COLUMN_SUPPLIER_NAME, editTextSupplierName.getText().toString());
            datas.put(Contract.InventoryFirstTable.COLUMN_SUPPLIER_EMAIL, editTextSupplierEmail.getText().toString());
            datas.put(Contract.InventoryFirstTable.COLUMN_SUPPLIER_PHONE_NUMBER, editTextSupplierPhone.getText().toString());
            getContentResolver().insert(Contract.InventoryFirstTable.CONTENT_URI, datas);
            finish();
        }

    }

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
