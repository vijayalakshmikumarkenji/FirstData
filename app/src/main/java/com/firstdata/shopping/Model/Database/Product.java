package com.firstdata.shopping.Model.Database;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    private Long uid;
    private String name;
    private String image;
    private String description;
    private Long price;
    private String category;
    public final static Parcelable.Creator<Product> CREATOR = new Creator<Product>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return (new Product[size]);
        }

    };

    public Product(Long uid, String name, String image, String description, long price, String category){
        this.uid = uid;
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.category = category;

    }

    protected Product(Parcel in) {
        this.uid = ((Long) in.readValue((Long.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((Long) in.readValue((Long.class.getClassLoader())));
        this.category = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Product() {
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(uid);
        dest.writeValue(name);
        dest.writeValue(image);
        dest.writeValue(description);
        dest.writeValue(price);
        dest.writeValue(category);
    }

    public int describeContents() {
        return 0;
    }

}
