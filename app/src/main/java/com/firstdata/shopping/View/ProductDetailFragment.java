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

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firstdata.shopping.Model.Database.Product;
import com.firstdata.shopping.Presenter.ProductDetailPresenterImpl;
import com.firstdata.shopping.R;
import com.firstdata.shopping.ShoppingApplication;
import com.squareup.otto.Bus;


/**
 * Fragment class for product detail view and its action.
 * <p>
 * Created  by Vijayalakshmi K K
 */
public class ProductDetailFragment extends Fragment implements IProductDetailView {
    private final static String PRODUCT_DATA = "product_data";
    private Product mProduct;
    private ProductDetailPresenterImpl mProductDetailPresenter;

    ImageView mProductImageView;
    TextView mProductNameView;
    TextView mProductPriceView;
    TextView mProductDescView;
    TextView mAddToCartView;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    public static ProductDetailFragment
    newInstance(Product product) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(PRODUCT_DATA, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProduct
                    = getArguments().getParcelable(PRODUCT_DATA);
        }
        mProductDetailPresenter = new ProductDetailPresenterImpl(this);

    }

    @Override
    public void getCartSize(Long size) {
        mProductDetailPresenter.getPRoductAvailableinDB(mProduct);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        setProductImageView(view);
        setDetailView(view);
        setAddTocartButtonView(view);
        mProductDetailPresenter.getCartSize();
        return view;
    }


    /**
     * Method to set the product image using glide.
     *
     * @param view view
     */
    private void setProductImageView(View view) {
        mProductImageView = (ImageView) view.findViewById(R.id.prod_image);
        Uri uri = Uri.parse(mProduct.getImage());

        Glide.with(getActivity())
                .load(uri)
                .placeholder(R.drawable.ic_launcher_foreground) //placeholder
                .error(R.drawable.ic_launcher_foreground) //error
                .into(mProductImageView);
    }

    /**
     * Method to define other views than image and add to cart button.
     *
     * @param view view
     */
    private void setDetailView(View view) {
        mProductNameView = (TextView) view.findViewById(R.id.product_name);
        mProductPriceView = (TextView) view.findViewById(R.id.product_price);
        mProductDescView = (TextView) view.findViewById(R.id.product_description);
        mProductNameView.setText(mProduct.getName());
        mProductPriceView.setText("₹" + String.valueOf(mProduct.getPrice()));
        mProductDescView.setText(mProduct.getDescription());
    }

    /**
     * Method to define the add to cart button view and its actions.
     *
     * @param view view
     */
    private void setAddTocartButtonView(View view) {
        mAddToCartView = (TextView) view.findViewById(R.id.add_to_cart_view);
        mAddToCartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAddToCartView.getText().toString().equals(getResources().getString(R.string.addToCart)))
                    mProductDetailPresenter.addProductToCart(mProduct);
                else
                    ShoppingApplication.bus.post(new GoToCheckoutPageClickedEvent());

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        ShoppingApplication.bus.unregister(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        ShoppingApplication.bus.register(this);
    }

    @Override
    public void isProductAdded(boolean isProductAdded) {
        if (isProductAdded) {
            Toast.makeText(getActivity(), "Product added successfully", Toast.LENGTH_LONG).show();
            mAddToCartView.setText(R.string.goToCart);
            ShoppingApplication.bus.post(new GetCartCountEvent());
        } else {
            Toast.makeText(getActivity(), "Something happened bad while adding product " +
                    "to cart, Please try again", Toast.LENGTH_LONG).show();
            mAddToCartView.setText(R.string.addToCart);
        }
    }

    @Override
    public void isProductAvailableInDB(boolean isProductAvailableInDB) {
        if (isProductAvailableInDB) {
            mAddToCartView.setText(R.string.goToCart);
        } else {
            mAddToCartView.setText(R.string.addToCart);
        }
    }

    // Class for event to whenever it need to launch checkout page if go to cart button clicked.
    public static final class GoToCheckoutPageClickedEvent {
        public GoToCheckoutPageClickedEvent() {

        }
    }

    // class for Event to get the cart count.
    public static final class GetCartCountEvent {

    }
}
