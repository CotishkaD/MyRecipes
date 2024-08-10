package com.grandmaskitchen.myrecipes.models;

public class CategoriesModel {
    private int categoryName;
    private int categoryImage;
    private String categoryNameInBD;

    public CategoriesModel(int categoryName, int categoryImage, String categoryNameInBD) {
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.categoryNameInBD = categoryNameInBD;
    }

    public int getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(int categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryNameInBD() {
        return categoryNameInBD;
    }

    public void setCategoryNameInBD(String categoryNameInBD) {
        this.categoryNameInBD = categoryNameInBD;
    }
}
