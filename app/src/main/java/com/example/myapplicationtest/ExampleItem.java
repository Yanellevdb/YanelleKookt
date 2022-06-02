package com.example.myapplicationtest;

import org.json.JSONObject;

public class ExampleItem {
    private String mImageUrl;
    private String mGerecht;
    private int mCalorieen;
    private String mIngredients;
    // private JSONObject mIngredient;
    //  private JSONObject mBereidingUrl;

    public ExampleItem(String imageUrl, String gerecht, int calorieen, String ingredients) {
        mImageUrl = imageUrl;
        mGerecht = gerecht;
        mCalorieen = calorieen;
        mIngredients= ingredients;
        // mBereidingUrl= bereiding;
    }

    //hiermee kunnen we de waarde ophalen wanneer we deze methodes zouden toepassen
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

   /* public JSONObject getBereiding() {
        return mBereidingUrl;
    }*/
}