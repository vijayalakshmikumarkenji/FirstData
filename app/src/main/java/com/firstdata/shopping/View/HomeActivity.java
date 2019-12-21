package com.firstdata.shopping.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.firstdata.shopping.Model.Database.Product;
import com.firstdata.shopping.Presenter.ProductPresenterImpl;
import com.firstdata.shopping.R;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements IViewInterface {
    public static final int FRAGMENT_CONTAINER = R.id.fragment_container;


    private static FrameLayout fragment_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);
     //   showProgress("Getting Product List");
        ProductPresenterImpl productPresenter = new ProductPresenterImpl(this);
        productPresenter.getProductList();
    }


    public void replaceFragment(Fragment fragment, String tag) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }


    private void launchProductListFragment(List<Product> products) {
        ProductListFragment productListFragment = ProductListFragment.newInstance(products);
        replaceFragment(productListFragment, ProductListFragment.class.getName());
    }
    @Override
    public void showProgress(String message) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ProgressDialogFragment progressDialogFragment = null;

        if (fragmentManager.findFragmentByTag(ProgressDialogFragment.class.getName()) == null) {
            progressDialogFragment = ProgressDialogFragment.newInstance(message);

        }

        fragmentManager.beginTransaction()
                .add(progressDialogFragment, ProgressDialogFragment.class.getName())
                .commitAllowingStateLoss();
    }


    @Override
    public void hideProgress() {
        Log.d("viji", "hide progress ");

        FragmentManager fragmentManager = getSupportFragmentManager();
        ProgressDialogFragment progressDialogFragment =
                (ProgressDialogFragment) fragmentManager
                        .findFragmentByTag(ProgressDialogFragment.class.getName());

        if (progressDialogFragment != null) {
            Log.d("viji", "hide progress not null");
            fragmentManager.beginTransaction().remove(progressDialogFragment).commitAllowingStateLoss();
        }


    }

    @Override
    public void onRetrieveProductList(List<Product> products) {
        hideProgress();
        launchProductListFragment(products);
    }
}
