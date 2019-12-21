package com.firstdata.shopping.View;

import com.firstdata.shopping.Model.Database.Product;

import java.util.List;

public interface IViewInterface {

    public void showProgress(String message);
    public void hideProgress();
    public void onRetrieveProductList(List<Product> products);

}
