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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firstdata.shopping.Model.Database.Product;
import com.firstdata.shopping.Presenter.ProductPresenterImpl;
import com.firstdata.shopping.R;
import com.firstdata.shopping.ShoppingApplication;
import com.squareup.otto.Subscribe;

import java.util.List;

/**
 * Home activity class contains the container for fragments
 * and contains the logic for bus handling.
 * <p>
 * Created  by Vijayalakshmi K K
 */
public class HomeActivity extends AppCompatActivity implements IViewInterface {
    private ProductPresenterImpl mProductPresenter;
    FragmentManager mFragmentManager;
    private ImageView mCartImageView;
    private TextView mCartCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);
        mProductPresenter = new ProductPresenterImpl(this);
        mCartCountTextView = (TextView) findViewById(R.id.cart_counter_view);
        mCartImageView = (ImageView) findViewById(R.id.view_cart_view);
        mCartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProductPresenter.getCartItemList();

            }
        });

        mFragmentManager = getSupportFragmentManager();

        //showProgress("Getting Product List");

        mProductPresenter.getProductList();
        mProductPresenter.getCartCountFromPresenter();
    }


    @Override
    public void getCartItemCount(Long cartItemCount) {
        if (cartItemCount > 0)
            mCartCountTextView.setText(String.valueOf(cartItemCount));
        else
            mCartCountTextView.setText("");

    }


    @Override
    protected void onResume() {
        super.onResume();
        ShoppingApplication.bus.register(ottoBusHandler);
    }


    @Override
    protected void onPause() {
        super.onPause();
        ShoppingApplication.bus.unregister(ottoBusHandler);
    }

    /**
     * Common method to replace the fragment.
     *
     * @param fragment       Fragment to replace
     * @param tag            tag name of the fragment to replace
     * @param addToBackStack boolean identifier to add to stack ot not
     */
    public void replaceFragment(Fragment fragment, String tag, Boolean addToBackStack) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, tag);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }

        fragmentTransaction.commit();
    }


    /**
     * Method to launch product list fragment.
     *
     * @param products Product list
     */
    private void launchProductListFragment(List<Product> products) {
        ProductListFragment productListFragment = ProductListFragment.newInstance(products);
        replaceFragment(productListFragment, Constants.PRODUCT_LIST_FRAGMENT, false);
    }

    /**
     * Method to launch the product detail fragment.
     *
     * @param product Product
     */
    private void launchProductDetailFragment(Product product) {
        ProductDetailFragment productDetailFragment = ProductDetailFragment.newInstance(product);
        replaceFragment(productDetailFragment, Constants.PRODUCT_DETAIL_FRAGMENT, true);
    }

    /**
     * Method to launch the checkout fragment.
     *
     * @param productList list of products
     */
    private void launchCheckoutPageFragment(List<Product> productList) {
        CheckoutFragment checkoutFragment = CheckoutFragment.newInstance(productList);
        replaceFragment(checkoutFragment, Constants.CHECKOUT_FRAGMENT, true);
    }


    @Override
    public void getCartItemsList(List<Product> productList) {
        if (productList.size() > 0)
            launchCheckoutPageFragment(productList);
        else
            Toast.makeText(getApplicationContext(), "No items present in cart," +
                    " Please add some", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress(String message) {
        ProgressDialogFragment progressDialogFragment = null;

        if (mFragmentManager.findFragmentByTag(Constants.PROGRESS_DIALOGUE_FRAGMENT) == null) {
            progressDialogFragment = ProgressDialogFragment.newInstance(message);

        }

        mFragmentManager.beginTransaction()
                .add(progressDialogFragment, Constants.PROGRESS_DIALOGUE_FRAGMENT)
                .addToBackStack(Constants.PROGRESS_DIALOGUE_FRAGMENT)
                .commit();

    }


    @Override
    public void hideProgress() {
        Log.d("viji", "hide progress ");

        ProgressDialogFragment progressDialogFragment =
                (ProgressDialogFragment) mFragmentManager
                        .findFragmentByTag(Constants.PROGRESS_DIALOGUE_FRAGMENT);

        if (progressDialogFragment != null) {
            mFragmentManager.beginTransaction().remove(progressDialogFragment).commitAllowingStateLoss();
        }


    }

    @Override
    public void onRetrieveProductList(List<Product> products) {
        hideProgress();
        launchProductListFragment(products);
    }

    @Override
    public void deleteProduct(boolean isDelete, List<Product> productList) {

        if (isDelete) {
            if (mFragmentManager.findFragmentByTag(Constants.CHECKOUT_FRAGMENT) != null) {
                CheckoutFragment checkoutFragment = (CheckoutFragment) mFragmentManager
                        .findFragmentByTag(Constants.CHECKOUT_FRAGMENT);
                checkoutFragment.getmCheckoutPageAdapter().setProductList(productList);
                checkoutFragment.getmCheckoutPageAdapter().notifyDataSetChanged();
                mProductPresenter.getCartCountFromPresenter();
            }
        } else {
            Toast.makeText(getApplicationContext(), "There is some issue while deleting " +
                    ", Please try again", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void getProductDetails(Product product) {
        launchProductDetailFragment(product);
    }

    //Bus handler to receive post events.
    private Object ottoBusHandler = new Object() {
        @Subscribe
        public void onProductClicked(ProductListAdapter.OnProductClickedEvent event) {
            mProductPresenter.onProductClicked(event.getProductUid());
        }

        @Subscribe
        public void onGoToCheckoutPage(ProductDetailFragment.GoToCheckoutPageClickedEvent event) {
            mProductPresenter.getCartItemList();
        }

        @Subscribe
        public void OnProductRemoveFromCart(CheckoutPageAdapter.OnProductRemoveClickEvent event) {
            mProductPresenter.removeProductFromCart(event.getProduct());
        }


        @Subscribe
        public void onGetCartCountEvent(ProductDetailFragment.GetCartCountEvent event) {
            mProductPresenter.getCartCountFromPresenter();
        }

    };


}
