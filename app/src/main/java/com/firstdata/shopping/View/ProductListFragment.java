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

    private void setElectronicsRecyclerView(View view) {
        mElectronicsRecyclerView = (RecyclerView) view.findViewById(R.id.electronicsRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mElectronicsRecyclerView.setLayoutManager(linearLayoutManager);
        ProductListAdapter mElectronicsListAdapter = new ProductListAdapter(getActivity(), getProductListByCategory(Constants.ELECTRONICS));
        mElectronicsRecyclerView.setAdapter(mElectronicsListAdapter);
    }

    private List<Product> getProductListByCategory(String categoryName) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < mProductList.size(); i++) {
            if (mProductList.get(i).getCategory().equals(categoryName)) {
                products.add(mProductList.get(i));
            }
        }
        return products;
    }


    private void setFurnitureRecyclerView(View view) {
        mFurnitureRecyclerView = (RecyclerView) view.findViewById(R.id.furnitureRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager
                (getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mFurnitureRecyclerView.setLayoutManager(linearLayoutManager);
        ProductListAdapter mFurnitureListAdapter = new ProductListAdapter
                (getActivity(), getProductListByCategory(Constants.FURNITURE));
        mFurnitureRecyclerView.setAdapter(mFurnitureListAdapter);

    }

    /*// TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String uid) {
        if (mProductClickedListener != null) {
            mProductClickedListener.onProductClicked(uid);
        }
    }
*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    /*    if (context instanceof OnProductClickedListener) {
            mProductClickedListener = (OnProductClickedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnProducClickListener");
        }*/
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface OnProductClickedListener {
        // TODO: Update argument type and name
        void onProductClicked(String uid);
    }*/
}
