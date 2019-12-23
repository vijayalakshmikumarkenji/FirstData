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

package com.firstdata.shopping;

import android.app.Application;

import com.firstdata.shopping.Model.Database.Product;
import com.firstdata.shopping.Model.Database.ProductData;
import com.firstdata.shopping.Model.Database.ProductDatabase;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Application class.
 * Created  by Vijayalakshmi K K
 */
public class ShoppingApplication extends Application {
    public static Bus bus = new Bus(ThreadEnforcer.ANY);
    private static ProductData mProductData;
    private static ProductDatabase mProductDataBase;
    static ShoppingApplication mShoppingApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mShoppingApplication = this;
        mProductData = new ProductData();
        mProductDataBase = new ProductDatabase(getApplicationContext());
    }


    public synchronized ProductDatabase getProductDataBase() {
        return mProductDataBase;
    }


    public static ShoppingApplication getInstance() {
        return mShoppingApplication;
    }

    public synchronized ProductData getProductData() {
        return mProductData;
    }
}
