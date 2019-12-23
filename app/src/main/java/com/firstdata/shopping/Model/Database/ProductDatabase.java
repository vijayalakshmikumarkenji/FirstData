/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.firstdata.shopping.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Database class for different database actions like insert, create, remove etc.
 * <p>
 * Created  by Vijayalakshmi K K
 */
public class ProductDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ShoppingAppDB";
    private static final String TABLE_NAME = "Products";
    private static final String KEY_UID = "uid";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_PRICE = "price";
    private static final String KEY_CATEGORY = "category";

    private static final String[] COLUMNS = {KEY_UID, KEY_NAME, KEY_IMAGE,
            KEY_DESCRIPTION, KEY_PRICE, KEY_CATEGORY};


    public ProductDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATION_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
                + "uid LONG PRIMARY KEY , " + "name TEXT, "
                + "image TEXT, " + "description TEXT, " + "price LONG, " + "category TEXT )";

        sqLiteDatabase.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(sqLiteDatabase);
    }

    /**
     * Method to insert the product into databse.
     *
     * @param product product
     * @return long returns the status of insertion non zero if it is success else -1.
     */
    public long insertData(Product product) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_UID, product.getUid());
        contentValues.put(KEY_NAME, product.getName());
        contentValues.put(KEY_IMAGE, product.getImage());
        contentValues.put(KEY_DESCRIPTION, product.getDescription());
        contentValues.put(KEY_PRICE, product.getPrice());
        contentValues.put(KEY_CATEGORY, product.getCategory());
        long dataInserted = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
        return dataInserted;
    }


    /**
     * Method to return the size of cart count.
     *
     * @return long  returns the cart count
     */
    public Long getCartSize() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        return count;
    }


    /**
     * Method to check whether the requested product is in DB or not.
     *
     * @param product product to find
     * @return boolean returns true if product found else false
     */
    public boolean isProductInDB(Product product) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " uid = ?", // c. selections
                new String[]{String.valueOf(product.getUid())}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }


    /**
     * Method to delete the product selected.
     *
     * @param product product to delete
     * @return boolean returns no of rows which is deleted else 0
     */
    public int deleteProduct(Product product) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, "uid = ?", new String[]{String.valueOf(product.getUid())});
        db.close();
        return result;
    }

    /**
     * Method to return all the cart items.
     *
     * @return List<Product> returns the list of product
     */
    public List<Product> getAllCartItems() {

        List<Product> productList = new ArrayList<>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Product product = null;

        if (cursor.moveToFirst()) {
            do {
                product = new Product(cursor.getLong(cursor.getColumnIndex(KEY_UID)),
                        cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                        cursor.getString(cursor.getColumnIndex(KEY_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
                        cursor.getLong(cursor.getColumnIndex(KEY_PRICE)),
                        cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)));
                productList.add(product);
            } while (cursor.moveToNext());
        }

        return productList;
    }
}
