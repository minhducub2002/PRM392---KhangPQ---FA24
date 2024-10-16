package com.ducnm.btvn_sqliteopenhelper;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "product")
public class Product implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String ProductName;
    private String ProductDescription;
    private Integer ProductPrice;

    public Product() {
    }

    public Product(String productName, String productDescription, Integer productPrice) {
        ProductName = productName;
        ProductDescription = productDescription;
        ProductPrice = productPrice;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public Integer getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(Integer productPrice) {
        ProductPrice = productPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
