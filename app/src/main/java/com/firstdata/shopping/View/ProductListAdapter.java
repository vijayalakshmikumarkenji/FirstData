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

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.CustomHolder> {

    Context mContext;
    List<Product> mProductList = new ArrayList<>();

    Bus mBus;

    public ProductListAdapter(Context context, List<Product> productList) {
        this.mContext = context;
        this.mProductList = productList;
        mBus = ShoppingApplication.bus;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull CustomHolder holder) {
        super.onViewAttachedToWindow(holder);
        mBus.register(this);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mBus.unregister(this);
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
    onBindViewHolder(@NonNull CustomHolder customHolder,final int i) {

        customHolder.mProductView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBus.post(new OnProductClickedEvent(mProductList.get(i).getUid()));
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
