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


/**
 * Interface class Product detail actions.
 * <p>
 * Created  by Vijayalakshmi K K
 */
public interface IProductDetailPresenter {

    //To check whether the requested product is available in DB or not.
    public void getPRoductAvailableinDB(Product product);

    // To add the product to cart via product detail add to cart click action.
    public void addProductToCart(Product product);

    // To get the cart size
    public void getCartSize();


}
