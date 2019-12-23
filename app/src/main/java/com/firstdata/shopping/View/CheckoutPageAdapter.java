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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firstdata.shopping.Model.Database.Product;
import com.firstdata.shopping.R;
import com.firstdata.shopping.ShoppingApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class used for showing all the items present in the cart.
 * <p>
 * Created  by Vijayalakshmi K K
 */
public class CheckoutPageAdapter extends RecyclerView.Adapter<CheckoutPageAdapter.CartItemHolder> {

    private Context mContext;
    private List<Product> mProductList = new ArrayList<>();


    CheckoutPageAdapter(Context context, List<Product> productList) {
        this.mContext = context;
        this.mProductList = productList;

    }

    @NonNull
    @Override
    public CartItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CartItemHolder cartItemHolder;
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.checkout_row_layout, viewGroup, false);
        cartItemHolder = new CartItemHolder(view);

        return cartItemHolder;
    }

    public void setProductList(List<Product> mProductList) {
        this.mProductList = mProductList;
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemHolder cartItemHolder, final int i) {

        Uri uri = Uri.parse(mProductList.get(i).getImage());
        Glide.with(mContext)
                .load(uri)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(cartItemHolder.mProductImageview);
        cartItemHolder.mProductNameView.setText(mProductList.get(i).getName());
        cartItemHolder.mProductPriceView.setText("₹" + String.valueOf(mProductList.get(i).getPrice()));
        cartItemHolder.mProductRemoveView
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ShoppingApplication.bus.post(new OnProductRemoveClickEvent(mProductList.get(i)));
                    }
                });

    }

    @Override
    public void onViewAttachedToWindow(@NonNull CartItemHolder holder) {
        super.onViewAttachedToWindow(holder);
        ShoppingApplication.bus.register(this);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull CartItemHolder holder) {
        super.onViewDetachedFromWindow(holder);
        ShoppingApplication.bus.unregister(this);
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    /**
     * Holder class for checkout row layout views.
     */
    public class CartItemHolder extends RecyclerView.ViewHolder {
        private ImageView mProductImageview;
        private TextView mProductNameView;
        private TextView mProductPriceView;
        private ImageView mProductRemoveView;

        public CartItemHolder(@NonNull View itemView) {
            super(itemView);
            mProductImageview = (ImageView) itemView.findViewById(R.id.product_image);
            mProductNameView = (TextView) itemView.findViewById(R.id.product_title_view);
            mProductPriceView = (TextView) itemView.findViewById(R.id.product_price_view);
            mProductRemoveView = (ImageView) itemView.findViewById(R.id.remove_product);
        }
    }

    /**
     * Class for sending the event when user click to remove button of respective product.
     */
    public static final class OnProductRemoveClickEvent {

        public Product mProduct;

        public OnProductRemoveClickEvent(Product product) {
            this.mProduct = product;
        }

        public Product getProduct() {
            return mProduct;
        }
    }
}
