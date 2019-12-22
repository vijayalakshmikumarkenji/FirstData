package com.firstdata.shopping.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.firstdata.shopping.Model.Database.Product;
import com.firstdata.shopping.Presenter.ProductClickPresentImpl;
import com.firstdata.shopping.Presenter.ProductPresenterImpl;
import com.firstdata.shopping.R;
import com.firstdata.shopping.ShoppingApplication;
import com.squareup.otto.Subscribe;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements IViewInterface, IProductClickView {
    public static final int FRAGMENT_CONTAINER = R.id.fragment_container;

    private ProductPresenterImpl mProductPresenter;

    private ProductClickPresentImpl mProductClickPresenter;
    private static FrameLayout fragment_container;
    FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);
        mFragmentManager = getSupportFragmentManager();

        //    showProgress("Getting Product List");
        mProductPresenter = new ProductPresenterImpl(this);
        mProductClickPresenter = new ProductClickPresentImpl(this);
        mProductPresenter.getProductList();
    }


    @Override
    protected void onResume() {
        super.onResume();
        ShoppingApplication.bus.register(ottoBusHandler);
    }


    @Override
    protected void onPause() {
        super.onPause();
        ShoppingApplication.bus.unregister(ottoBusHandler);
    }

    public void replaceFragment(Fragment fragment, String tag, Boolean addToBackStack) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, tag);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }
        fragmentTransaction.commit();
    }


    private void launchProductListFragment(List<Product> products) {
        ProductListFragment productListFragment = ProductListFragment.newInstance(products);
        replaceFragment(productListFragment, ProductListFragment.class.getName(), false);
    }

    private void launchProductDetailFragment(Product product) {
        ProductDetailFragment productDetailFragment = ProductDetailFragment.newInstance(product);
        replaceFragment(productDetailFragment, ProductDetailFragment.class.getName(), true);

    }


    @Override
    public void showProgress(String message) {
        ProgressDialogFragment progressDialogFragment = null;

        if (mFragmentManager.findFragmentByTag("progress") == null) {
            progressDialogFragment = ProgressDialogFragment.newInstance(message);

        }

        mFragmentManager.beginTransaction()
                .add(progressDialogFragment, "progress")
                .commitAllowingStateLoss();

    }


    @Override
    public void hideProgress() {
        Log.d("viji", "hide progress ");

        ProgressDialogFragment progressDialogFragment =
                (ProgressDialogFragment) mFragmentManager
                        .findFragmentByTag("progress");

        if (progressDialogFragment != null) {
            Log.d("viji", "hide progress not null");
            mFragmentManager.beginTransaction().remove(progressDialogFragment).commitAllowingStateLoss();
        }


    }

    @Override
    public void onRetrieveProductList(List<Product> products) {
        hideProgress();
        launchProductListFragment(products);
    }

    @Override
    public void getProductDetails(Product product) {
        launchProductDetailFragment(product);
    }

    private Object ottoBusHandler = new Object() {
        @Subscribe
        public void onProductClicked(ProductListAdapter.OnProductClickedEvent event) {
            mProductClickPresenter.onProductClicked(event.getProductUid());
        }
    };
}
