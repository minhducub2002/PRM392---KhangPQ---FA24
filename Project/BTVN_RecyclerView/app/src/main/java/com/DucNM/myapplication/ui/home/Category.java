package com.DucNM.myapplication.ui.home;

public class Category {
    private int categoryResId;
    private String categoryName;

    public Category(int categoryResId, String categoryName) {
        this.categoryResId = categoryResId;
        this.categoryName = categoryName;
    }

    public int getCategoryResId() {
        return categoryResId;
    }

    public void setCategoryResId(int categoryResId) {
        this.categoryResId = categoryResId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
