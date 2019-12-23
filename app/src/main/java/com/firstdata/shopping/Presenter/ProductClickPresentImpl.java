package com.firstdata.shopping.Presenter;

import com.firstdata.shopping.ShoppingApplication;
import com.firstdata.shopping.View.IProductClickView;

public class ProductClickPresentImpl implements IProductClickPresenter {

    IProductClickView mIProductClickView;

    public ProductClickPresentImpl(IProductClickView productClickView) {
        this.mIProductClickView = productClickView;
    }

    @Override
    public void onProductClicked(Long uid) {
        mIProductClickView.getProductDetails(ShoppingApplication.getProductData().getProductByUid(uid));
    }

}
