package com.example.android.sqlsecondsectionapplication.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;


public class InventoryProvider extends ContentProvider {


    private static final int INVENTORY = 1;

    private static final int INVENTORY_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_PETS, INVENTORY);
        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_PETS + "/#", INVENTORY_ID);
        // TODO: Add 2 content URIs to URI matcher
    }

    private DbHelper myDbHelper;
    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = InventoryProvider.class.getSimpleName();

    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {

        myDbHelper = new DbHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase database = myDbHelper.getReadableDatabase();

        Cursor cursor;
        int match = sUriMatcher.match(uri);

        switch (match) {

            case INVENTORY:

                cursor = database.query(Contract.InventoryFirstTable.TABLE_NAME, projection, null, null, null, null, sortOrder);
                break;
            case INVENTORY_ID:

                selection = Contract.InventoryFirstTable._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(Contract.InventoryFirstTable.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

                break;
            default:


                throw new IllegalArgumentException("Cannot query unknown " + uri);


        }

        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case INVENTORY:
                return insertPet(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertPet(Uri uri, ContentValues values) {
        SQLiteDatabase database = myDbHelper.getWritableDatabase();
        long id = database.insert(Contract.InventoryFirstTable.TABLE_NAME, null, values);
        Integer quantity =values.getAsInteger(Contract.InventoryFirstTable.COLUMN_QUANTITY);
        if(quantity<0)
        {
            throw new IllegalArgumentException("quantity can not be less than zero");
        }
        String supplierName=values.getAsString(Contract.InventoryFirstTable.COLUMN_SUPPLIER_NAME);
        if (supplierName == null) {
            throw new IllegalArgumentException("supplier name  can not be null");
        }
        String supplierEmail=values.getAsString(Contract.InventoryFirstTable.COLUMN_SUPPLIER_EMAIL);
        if (supplierEmail == null) {
            throw new IllegalArgumentException("supplier email can not be null");
        }
        String supplierPhone=values.getAsString(Contract.InventoryFirstTable.COLUMN_SUPPLIER_PHONE_NUMBER);
        if (supplierPhone == null) {
            throw new IllegalArgumentException("supplier phone number can not be null");
        }
        String productName = values.getAsString(Contract.InventoryFirstTable.COLUMN_PRODUCT_NAME);
        if (productName == null) {
            throw new IllegalArgumentException("product name can not be null");
        }
        Integer price = values.getAsInteger(Contract.InventoryFirstTable.COLUMN_PRICE);
        if (price < 0) {
            throw new IllegalArgumentException("price requires valid number");
        }
        if (id == -1) {
            Log.e("Row", "row cannot be equals to -1" + uri);
            Toast.makeText(getContext(), "error row !=-1", Toast.LENGTH_SHORT).show();
            return null;
        }

        return ContentUris.withAppendedId(uri, id);
    }

    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case INVENTORY:
                return updatePet(uri, contentValues, selection, selectionArgs);
            case INVENTORY_ID:
                // For the INVENTORY_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = Contract.InventoryFirstTable._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updatePet(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updatePet(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(Contract.InventoryFirstTable.COLUMN_PRODUCT_NAME)) {
            String name = values.getAsString(Contract.InventoryFirstTable.COLUMN_PRODUCT_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Product requires a name");
            }
        }
        if (values.containsKey(Contract.InventoryFirstTable.COLUMN_PRICE)) {
            Integer price = values.getAsInteger(Contract.InventoryFirstTable.COLUMN_PRICE);
            if (price == null) {
                throw new IllegalArgumentException("Pet requires valid gender");
            }

        }
        if (values.containsKey(Contract.InventoryFirstTable.COLUMN_QUANTITY)) {
            Integer quantity = values.getAsInteger(Contract.InventoryFirstTable.COLUMN_QUANTITY);
            if (quantity != null && quantity < 0) {
                throw new IllegalArgumentException("Pet requires valid weight");
            }
        }
        //supemail
        if (values.containsKey(Contract.InventoryFirstTable.COLUMN_SUPPLIER_EMAIL)) {
            String supplierEmail = values.getAsString(Contract.InventoryFirstTable.COLUMN_SUPPLIER_EMAIL);
            if (supplierEmail == null) {
                throw new IllegalArgumentException("Product requires a name");
            }
        }
        //supnumber
        if (values.containsKey(Contract.InventoryFirstTable.COLUMN_SUPPLIER_PHONE_NUMBER)) {
            String supplierNumber = values.getAsString(Contract.InventoryFirstTable.COLUMN_SUPPLIER_PHONE_NUMBER);
            if (supplierNumber == null) {
                throw new IllegalArgumentException("Product requires a name");
            }
        }
        //suppname
        if (values.containsKey(Contract.InventoryFirstTable.COLUMN_SUPPLIER_NAME)) {
            String supplierName = values.getAsString(Contract.InventoryFirstTable.COLUMN_SUPPLIER_NAME);
            if (supplierName == null) {
                throw new IllegalArgumentException("Product requires a name");
            }
        }
        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = myDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(Contract.InventoryFirstTable.TABLE_NAME, values, selection, selectionArgs);
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }


    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = myDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case INVENTORY:
                return database.delete(Contract.InventoryFirstTable.TABLE_NAME, selection, selectionArgs);
            case INVENTORY_ID:
                selection = Contract.InventoryFirstTable._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return database.delete(Contract.InventoryFirstTable.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }
    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case INVENTORY:
                return Contract.InventoryFirstTable.CONTENT_LIST_TYPE;
            case INVENTORY_ID:
                return Contract.InventoryFirstTable.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}