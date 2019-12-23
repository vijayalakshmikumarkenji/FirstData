package com.firstdata.shopping.Presenter;

import android.icu.util.ValueIterator;

import com.firstdata.shopping.Model.Database.Product;
import com.firstdata.shopping.Model.Database.ProductData;
import com.firstdata.shopping.ShoppingApplication;
import com.firstdata.shopping.View.IViewInterface;

import java.util.ArrayList;
import java.util.List;

public class ProductPresenterImpl implements IProductPresenter {

    ProductData productData;
    IViewInterface viewInterface;

    public ProductPresenterImpl(IViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    @Override
    public void getCartCountFromPresenter() {
        Long cartItemCount = 0L;
         viewInterface.getCartItemCount(cartItemCount);

    }

    @Override
    public void getProductList() {
        List<Product> productList = new ArrayList<>();

        productList = ShoppingApplication.getProductData().getProductsList();

        if (productList.size() > 0) {
            viewInterface.onRetrieveProductList(productList);
        } else {
            viewInterface.onRetrieveProductList(new ArrayList<Product>());
        }
    }

 /*   @Override
    public void getProductListByCategory(String categoryName) {

        List<Product> list = new ArrayList<>();

        list = productData.getProductListByCategory(categoryName);

        if (list.size() > 0) {

            viewInterface.onRetriveProductListPerCategory(list);

        } else {
            viewInterface.onRetriveProductListPerCategory(new ArrayList<Product>());
        }

    }*/
}
