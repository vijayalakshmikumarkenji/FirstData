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

package com.firstdata.shopping.Presenter;

import android.icu.util.ValueIterator;

import com.firstdata.shopping.Model.Database.Product;
import com.firstdata.shopping.Model.Database.ProductData;
import com.firstdata.shopping.ShoppingApplication;
import com.firstdata.shopping.View.IViewInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter class home activity and checkout view actions.
 * <p>
 * Created  by Vijayalakshmi K K
 */
public class ProductPresenterImpl implements IProductPresenter {

    ProductData productData;
    IViewInterface viewInterface;

    public ProductPresenterImpl(IViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    @Override
    public void getCartCountFromPresenter() {
        viewInterface.getCartItemCount(ShoppingApplication
                .getInstance().getProductDataBase().getCartSize());
    }

    @Override
    public void removeProductFromCart(Product product) {
        int isDelete = ShoppingApplication.getInstance().getProductDataBase()
                .deleteProduct(product);
        List<Product> productsList = new ArrayList<>();
        productsList = ShoppingApplication.getInstance()
                .getProductDataBase().getAllCartItems();
        if (isDelete > 0)
            viewInterface.deleteProduct(true, productsList);
        else
            viewInterface.deleteProduct(false, new ArrayList<Product>());
    }

    @Override
    public void onProductClicked(Long uid) {
        viewInterface.getProductDetails(ShoppingApplication.getInstance()
                .getProductData().getProductByUid(uid));
    }

    @Override
    public void getCartItemList() {
        List<Product> productsList = new ArrayList<>();
        productsList = ShoppingApplication.getInstance()
                .getProductDataBase().getAllCartItems();
        viewInterface.getCartItemsList(productsList);
    }

    @Override
    public void getProductList() {
        List<Product> productList = new ArrayList<>();

        productList = ShoppingApplication.getInstance().getProductData().getProductsList();

        if (productList.size() > 0) {
            viewInterface.onRetrieveProductList(productList);
        } else {
            viewInterface.onRetrieveProductList(new ArrayList<Product>());
        }
    }

 /*   @Override
    public void getProductListByCategory(String categoryName) {

        List<Product> list = new ArrayList<>();

        list = productData.getProductListByCategory(categoryName);

        if (list.size() > 0) {

            viewInterface.onRetriveProductListPerCategory(list);

        } else {
            viewInterface.onRetriveProductListPerCategory(new ArrayList<Product>());
        }

    }*/
}
