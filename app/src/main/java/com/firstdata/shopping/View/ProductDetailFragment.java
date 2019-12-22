package com.firstdata.shopping.View;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firstdata.shopping.Model.Database.Product;
import com.firstdata.shopping.R;

public class ProductDetailFragment extends Fragment {
    private final static String PRODUCT_DATA = "product_data";
    private Product mProduct;

    ImageView mProductImageView;
    TextView mProductNameView;
    TextView mProductPriceView;
    TextView mProductDescView;
    TextView mAddToCartView;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    public static ProductDetailFragment newInstance(Product product) {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        setProductImageView(view);
        setDetailView(view);
        setAddTocartButtonView(view);
        return view;
    }


    private void setProductImageView(View view) {
        mProductImageView = (ImageView) view.findViewById(R.id.prod_image);
        Uri uri = Uri.parse(mProduct.getImage());

        Glide.with(getActivity())
                .load(uri)
                .placeholder(R.drawable.ic_launcher_foreground) //placeholder
                .error(R.drawable.ic_launcher_foreground) //error
                .into(mProductImageView);
    }

    private void setDetailView(View view) {
        mProductNameView = (TextView) view.findViewById(R.id.product_name);
        mProductPriceView = (TextView) view.findViewById(R.id.product_price);
        mProductDescView = (TextView) view.findViewById(R.id.product_description);
        mProductNameView.setText(mProduct.getName());
        mProductPriceView.setText("₹" + String.valueOf(mProduct.getPrice()));
        mProductDescView.setText(mProduct.getDescription());
    }


    private void setAddTocartButtonView(View view) {
        mAddToCartView = (TextView) view.findViewById(R.id.add_to_cart_view);
        mAddToCartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
