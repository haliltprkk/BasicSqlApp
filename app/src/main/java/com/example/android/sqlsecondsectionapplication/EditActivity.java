package com.example.android.sqlsecondsectionapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.sqlsecondsectionapplication.Data.Contract;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit");
        int getPosition = getIntent().getIntExtra("position", 0);
        String[] projection = {Contract.InventoryFirstTable.ID, Contract.InventoryFirstTable.COLUMN_QUANTITY, Contract.InventoryFirstTable.COLUMN_PRODUCT_NAME, Contract.InventoryFirstTable.COLUMN_PRICE,
                Contract.InventoryFirstTable.COLUMN_SUPPLIER_NAME, Contract.InventoryFirstTable.COLUMN_SUPPLIER_EMAIL, Contract.InventoryFirstTable.COLUMN_SUPPLIER_PHONE_NUMBER};
        Cursor cursor = getContentResolver().query(Contract.InventoryFirstTable.CONTENT_URI, projection, null, null, null);
        cursor.moveToPosition(getPosition);
        final EditText editTextPrice = findViewById(R.id.price_edit_text);
        final EditText editTextProduct = findViewById(R.id.product_name_edit_text);
        final EditText editTextQuantity = findViewById(R.id.quantity__edit_text);
        final EditText editTextSupplierName = findViewById(R.id.supplier_name_edit_text);
        final EditText editTextSupplierEmail = findViewById(R.id.supplier_email_edit_text);
        final EditText editTextSupplierPhone = findViewById(R.id.supplier_phone_number_edit_text);

        int nameColumnIndex = cursor.getColumnIndex(Contract.InventoryFirstTable.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(Contract.InventoryFirstTable.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(Contract.InventoryFirstTable.COLUMN_QUANTITY);
        int supplierNameColumnIndex = cursor.getColumnIndex(Contract.InventoryFirstTable.COLUMN_SUPPLIER_NAME);
        int supplierEmailColumnIndex = cursor.getColumnIndex(Contract.InventoryFirstTable.COLUMN_SUPPLIER_EMAIL);
        int supplierPhoneColumnIndex = cursor.getColumnIndex(Contract.InventoryFirstTable.COLUMN_SUPPLIER_PHONE_NUMBER);
        String supplierName = cursor.getString(supplierNameColumnIndex);
        final String supplierEmail = cursor.getString(supplierEmailColumnIndex);
        final String supplierPhone = cursor.getString(supplierPhoneColumnIndex);
        final String name = cursor.getString(nameColumnIndex);
        Integer price = cursor.getInt(priceColumnIndex);
        final Integer[] quantity = {cursor.getInt(quantityColumnIndex)};
        editTextPrice.setText(price + "");
        editTextProduct.setText(name);
        editTextQuantity.setText(quantity[0] + "");
        editTextSupplierName.setText(supplierName);
        editTextSupplierEmail.setText(supplierEmail);
        editTextSupplierPhone.setText(supplierPhone);

        Button increaseEditButton = findViewById(R.id.inrease_edit_button);
        Button decreaseEditButton = findViewById(R.id.decrease_edit_button);
        Button deleteEditButton = findViewById(R.id.delete_edit_button);
        Button callEditButton = findViewById(R.id.call_edit_button);
        Button saveEditButton = findViewById(R.id.save_edit_button);

        saveEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextQuantity.getText().toString().isEmpty() || editTextPrice.getText().toString().isEmpty() || editTextProduct.getText().toString().isEmpty() ||
                        editTextSupplierName.getText().toString().isEmpty() ||
                        editTextSupplierPhone.getText().toString().isEmpty()) {
                    Toast.makeText(EditActivity.this, "You need to fill everywhere.", Toast.LENGTH_SHORT).show();
                } else if (isValidEmail(editTextSupplierEmail.getText().toString().trim()) == false) {
                    Toast.makeText(EditActivity.this, "You need to right your mail address correctly.", Toast.LENGTH_SHORT).show();

                } else {
                    ContentValues values = new ContentValues();
                    values.put(Contract.InventoryFirstTable.COLUMN_QUANTITY, editTextQuantity.getText().toString());
                    values.put(Contract.InventoryFirstTable.COLUMN_PRICE, editTextPrice.getText().toString());
                    values.put(Contract.InventoryFirstTable.COLUMN_PRODUCT_NAME, editTextProduct.getText().toString());
                    values.put(Contract.InventoryFirstTable.COLUMN_SUPPLIER_NAME, editTextSupplierName.getText().toString());
                    values.put(Contract.InventoryFirstTable.COLUMN_SUPPLIER_EMAIL, editTextSupplierEmail.getText().toString());
                    values.put(Contract.InventoryFirstTable.COLUMN_SUPPLIER_PHONE_NUMBER, editTextSupplierPhone.getText().toString());
                    getContentResolver().update(Contract.InventoryFirstTable.CONTENT_URI, values, Contract.InventoryFirstTable.COLUMN_PRODUCT_NAME + "=?", new String[]{name});
                    finish();

                }

            }
        });

        deleteEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditActivity.this);
                builder1.setMessage("Are you sure this record will be deleted.");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MainActivity.getInstance().delete(name);
                                finish();
                                dialog.cancel();
                            }
                        });
                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder1.create();
                alert.show();
            }
        });
        increaseEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity[0] += 1;
                editTextQuantity.setText(quantity[0] + "");

            }
        });
        decreaseEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity[0] > 0) {
                    quantity[0] -= 1;
                    editTextQuantity.setText(quantity[0] + "");
                }
            }
        });

        callEditButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                if (supplierEmail.isEmpty() == false) {

                    String uri = "tel:" + supplierPhone.trim();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                }

            }
        });


    }

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
