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
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firstdata.shopping.Model.Database.Product;
import com.firstdata.shopping.R;
import com.firstdata.shopping.ShoppingApplication;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;


/**
 * Adapter class for showing the product detail and its action.
 * <p>
 * Created  by Vijayalakshmi K K
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.CustomHolder> {

    Context mContext;
    List<Product> mProductList = new ArrayList<>();

    public ProductListAdapter(Context context, List<Product> productList) {
        this.mContext = context;
        this.mProductList = productList;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull CustomHolder holder) {
        super.onViewAttachedToWindow(holder);
        ShoppingApplication.bus.register(this);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        ShoppingApplication.bus.unregister(this);
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        CustomHolder customHolder;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_row_layout, viewGroup, false);
        customHolder = new CustomHolder(view);
        return customHolder;
    }

    @Override
    public void
    onBindViewHolder(@NonNull CustomHolder customHolder, final int i) {

        customHolder.mProductView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingApplication.bus.post(new OnProductClickedEvent(mProductList.get(i).getUid()));
            }
        });

        Uri uri = Uri.parse(mProductList.get(i).getImage());
        Glide.with(mContext)
                .load(uri)
                .placeholder(R.drawable.ic_launcher_foreground) //placeholder
                .error(R.drawable.ic_launcher_foreground) //error
                .into(customHolder.mProductImage);
        customHolder.mProductNameView.setText(mProductList.get(i).getName());
        customHolder.mProductPrice.setText("₹" + String.valueOf(mProductList.get(i).getPrice()));

    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    // Holder class for product list rows views.
    public class CustomHolder extends RecyclerView.ViewHolder {

        private ImageView mProductImage;
        private TextView mProductNameView;
        private TextView mProductPrice;
        private LinearLayout mProductView;

        public CustomHolder(@NonNull View itemView) {
            super(itemView);
            mProductView = (LinearLayout) itemView.findViewById(R.id.product_view);
            mProductImage = (ImageView) itemView.findViewById(R.id.imageView);
            mProductNameView = (TextView) itemView.findViewById(R.id.imageNameView);
            mProductPrice = (TextView) itemView.findViewById(R.id.imagePriceView);

        }
    }

    // Class for the Event to notify product click event from list.
    public static final class OnProductClickedEvent {
        public Long productUid;

        public OnProductClickedEvent(Long uid) {
            this.productUid = uid;

        }

        public Long getProductUid() {
            return productUid;
        }
    }
}
