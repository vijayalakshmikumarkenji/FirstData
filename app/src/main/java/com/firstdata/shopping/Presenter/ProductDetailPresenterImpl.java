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

import com.firstdata.shopping.Model.Database.Product;
import com.firstdata.shopping.Model.Database.ProductDatabase;
import com.firstdata.shopping.ShoppingApplication;
import com.firstdata.shopping.View.IProductDetailView;


/**
 * Presenter class product detail view actions.
 * <p>
 * Created  by Vijayalakshmi K K
 */
public class ProductDetailPresenterImpl implements IProductDetailPresenter {
    IProductDetailView mIProductDetailView;
    ProductDatabase mProductDataBase;

    public ProductDetailPresenterImpl(IProductDetailView iProductDetailView) {
        this.mIProductDetailView = iProductDetailView;
        mProductDataBase = ShoppingApplication.getInstance().getProductDataBase();
    }

    @Override
    public void addProductToCart(Product product) {
        if(mProductDataBase.insertData(product) != -1){
            mIProductDetailView.isProductAdded(true);
        }else{
            mIProductDetailView.isProductAdded(false);

        }

    }

    @Override
    public void getCartSize() {
        mIProductDetailView.getCartSize(mProductDataBase.getCartSize());
    }

    @Override
    public void getPRoductAvailableinDB(Product product) {
        boolean isAvailable = mProductDataBase.isProductInDB(product);
        mIProductDetailView.isProductAvailableInDB(isAvailable);
    }

   }
