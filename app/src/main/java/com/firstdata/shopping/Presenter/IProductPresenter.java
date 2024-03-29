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

import java.util.List;

/**
 * Interface class home activity and checkout view actions.
 * <p>
 * Created  by Vijayalakshmi K K
 */
public interface IProductPresenter {

    // To get the list of products from input json.
    public void getProductList();

    // To identify tge click action of any product from product list via view action.
    public void onProductClicked(Long productUid);

    // To get the cart count to show in menu from view actions
    public void getCartCountFromPresenter();

    // To get the list of products when user click on cart menu.
    public void getCartItemList();

    // To remove the product from DB via view actions from checkout.
    public void removeProductFromCart(Product product);


}
