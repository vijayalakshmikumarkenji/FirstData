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

package com.firstdata.shopping.View;

import com.firstdata.shopping.Model.Database.Product;

import java.util.List;


/**
 * View interface for home activity and check out view and its action.
 * <p>
 * Created  by Vijayalakshmi K K
 */
public interface IViewInterface {

    public void showProgress(String message);

    public void hideProgress();

    //View method to retrieve the product list from input json which will be called from presenter.
    public void onRetrieveProductList(List<Product> products);

    //View method to get cart item count to show in ui which will be called from presenter.
    public void getCartItemCount(Long cartItemCount);

    //View method to get product list when user click on cart menu which will be called from presenter.
    public void getCartItemsList(List<Product> productList);

    //View method to delete the product when user click on remove button which will be called from presenter.
    public void deleteProduct(boolean isdelete, List<Product> productList);

    //View method to get product detail which will be called from presenter.
    public void getProductDetails(Product product);

}
