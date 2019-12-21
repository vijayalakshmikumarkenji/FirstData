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

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.CustomHolder> {

    Context mContext;
    List<Product> mProductList = new ArrayList<>();

    public ProductListAdapter(Context context, List<Product> productList) {
        this.mContext = context;
        this.mProductList = productList;
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
    public void onBindViewHolder(@NonNull CustomHolder customHolder, int i) {

        Uri uri = Uri.parse(mProductList.get(i).getImage());
        Glide.with(mContext)
                .load(uri)
                .placeholder(R.drawable.ic_launcher_foreground) //placeholder
                .error(R.drawable.ic_launcher_foreground) //error
                .into(customHolder.mProductImage);
        customHolder.mProductNameView.setText(mProductList.get(i).getName());
        customHolder.mProductPrice.setText(String.valueOf(mProductList.get(i).getPrice()));

    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    public class CustomHolder extends RecyclerView.ViewHolder {

        private ImageView mProductImage;
        private TextView mProductNameView;
        private TextView mProductPrice;

        public CustomHolder(@NonNull View itemView) {
            super(itemView);
            mProductImage = (ImageView) itemView.findViewById(R.id.imageView);
            mProductNameView = (TextView) itemView.findViewById(R.id.imageNameView);
            mProductPrice = (TextView) itemView.findViewById(R.id.imagePriceView);

        }
    }
}
