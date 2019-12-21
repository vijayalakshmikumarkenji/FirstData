package com.firstdata.shopping;

import android.app.Application;

import com.firstdata.shopping.Model.Database.Product;
import com.firstdata.shopping.Model.Database.ProductData;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class ShoppingApplication extends Application {
    public static Bus bus = new Bus(ThreadEnforcer.MAIN);
    static ProductData productData;

    @Override
    public void onCreate() {
        super.onCreate();
        productData = new ProductData();
    }


    public static ProductData getProductData() {
        return productData;
    }
}
