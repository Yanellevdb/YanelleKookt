package com.example.myapplicationtest;

public class ExampleItem {
    private String mImageUrl;
    private String mGerecht;
    private int mCalorieen;
    private String mIngredients;

    public ExampleItem(String imageUrl, String gerecht, int calorieen, String ingredients) {
        mImageUrl = imageUrl;
        mGerecht = gerecht;
        mCalorieen = calorieen;
        mIngredients= ingredients;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getGerecht() {
        return mGerecht;
    }

    public int getCalorieCount() {
        return mCalorieen;
    }

    public String getIngredients() {
        return mIngredients;
    }

}