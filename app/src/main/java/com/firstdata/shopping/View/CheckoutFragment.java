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
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firstdata.shopping.Model.Database.Product;
import com.firstdata.shopping.R;
import com.firstdata.shopping.ShoppingApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment class which contains view for cart items added in cart.
 * <p>
 * Created  by Vijayalakshmi K K
 */
public class CheckoutFragment extends Fragment {

    private RecyclerView mCheckoutRecyclerView;
    private static final String PRODUCTS = "products_list";
    private List<Product> mProductList = new ArrayList<>();
    private CheckoutPageAdapter mCheckoutPageAdapter;

    public CheckoutFragment() {
    }

    public static CheckoutFragment newInstance(List<Product> productList) {
        CheckoutFragment checkoutFragment = new CheckoutFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(PRODUCTS, (ArrayList<? extends Parcelable>) productList);
        checkoutFragment.setArguments(args);
        return checkoutFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProductList
                    = getArguments().getParcelableArrayList(PRODUCTS);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        ShoppingApplication.bus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        ShoppingApplication.bus.unregister(this);
    }

    public CheckoutPageAdapter getmCheckoutPageAdapter() {
        return mCheckoutPageAdapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.checkout_fragment_layout, container, false);
        mCheckoutRecyclerView = (RecyclerView) view.findViewById(R.id.checkout_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager
                (getActivity(), LinearLayoutManager.VERTICAL, false);
        mCheckoutRecyclerView.setLayoutManager(linearLayoutManager);
        mCheckoutPageAdapter = new CheckoutPageAdapter(getActivity(), mProductList);
        mCheckoutRecyclerView.setAdapter(mCheckoutPageAdapter);
        return view;
    }
}
