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

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.firstdata.shopping.Model.Database.Product;
import com.firstdata.shopping.R;
import com.firstdata.shopping.ShoppingApplication;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;


/**
 * Product list fragment class to show the list of products from json and its action.
 * <p>
 * Created  by Vijayalakshmi K K
 */
public class ProductListFragment extends Fragment {
    private static final String PRODUCT_LIST_DATA = "productList";

    private List<Product> mProductList = new ArrayList<>();

    private RecyclerView mFurnitureRecyclerView;
    private RecyclerView mElectronicsRecyclerView;

    //  private OnProductClickedListener mProductClickedListener;

    public ProductListFragment() {
        // Required empty public constructor
    }

    public static ProductListFragment newInstance(List<Product> productList) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(PRODUCT_LIST_DATA, (ArrayList<? extends Parcelable>) productList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProductList
                    = getArguments().getParcelableArrayList(PRODUCT_LIST_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        setFurnitureRecyclerView(view);
        setElectronicsRecyclerView(view);
        return view;
    }

    /**
     * Method to initialize the recycler view for electronics category.
     *
     * @param view view
     */
    private void setElectronicsRecyclerView(View view) {
        mElectronicsRecyclerView = (RecyclerView) view.findViewById(R.id.electronicsRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mElectronicsRecyclerView.setLayoutManager(linearLayoutManager);
        ProductListAdapter mElectronicsListAdapter = new ProductListAdapter(getActivity(), getProductListByCategory(Constants.ELECTRONICS));
        mElectronicsRecyclerView.setAdapter(mElectronicsListAdapter);
    }

    /**
     * Method to get the list of products by category..
     *
     * @param categoryName category name
     */
    private List<Product> getProductListByCategory(String categoryName) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < mProductList.size(); i++) {
            if (mProductList.get(i).getCategory().equals(categoryName)) {
                products.add(mProductList.get(i));
            }
        }
        return products;
    }


    /**
     * Method to initialize the recycler view for furniture category.
     *
     * @param view view
     */
    private void setFurnitureRecyclerView(View view) {
        mFurnitureRecyclerView = (RecyclerView) view.findViewById(R.id.furnitureRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager
                (getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mFurnitureRecyclerView.setLayoutManager(linearLayoutManager);
        ProductListAdapter mFurnitureListAdapter = new ProductListAdapter
                (getActivity(), getProductListByCategory(Constants.FURNITURE));
        mFurnitureRecyclerView.setAdapter(mFurnitureListAdapter);

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

    @Override
    public void onDetach() {
        super.onDetach();
        //     mProductClickedListener = null;
    }

}
