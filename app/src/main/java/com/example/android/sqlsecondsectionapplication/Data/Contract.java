package com.example.android.sqlsecondsectionapplication.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by SenHalit on 11.01.2018.
 */
public class Contract {
    public static final String CONTENT_AUTHORITY = "com.example.android.sqlsecondsectionapplication";
    public static final Uri BASE_CONTENT_URI = Uri.parse( "content://" + CONTENT_AUTHORITY );
    public static final String PATH_PETS = "inventory";

    public static class InventoryFirstTable implements BaseColumns
    {
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;


        public static final Uri CONTENT_URI = Uri.withAppendedPath( BASE_CONTENT_URI, PATH_PETS );
        public static final String ID = BaseColumns._ID;
        public static final String TABLE_NAME = "inventory";
        public static final String COLUMN_PRODUCT_NAME = "productName";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_SUPPLIER_NAME = "supplierName";
        public static final String COLUMN_SUPPLIER_EMAIL = "supplierEmail";
        public static final String COLUMN_SUPPLIER_PHONE_NUMBER = "supplierPhoneNumber";
        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PRODUCT_NAME + " TEXT , "
                + COLUMN_PRICE + " INTEGER NOT NULL DEFAULT 0, "
                + COLUMN_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                + COLUMN_SUPPLIER_NAME + " TEXT , "
                + COLUMN_SUPPLIER_EMAIL + " TEXT , "
                + COLUMN_SUPPLIER_PHONE_NUMBER + " TEXT );";
    }
}
