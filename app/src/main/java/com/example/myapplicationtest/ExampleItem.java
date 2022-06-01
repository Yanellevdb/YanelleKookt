package com.example.myapplicationtest;

import org.json.JSONObject;

public class ExampleItem {
    private String mImageUrl;
    private String mCreator;
    private int mLikes;
   // private JSONObject mIngredient;
  //  private JSONObject mBereidingUrl;

    public ExampleItem(String imageUrl, String creator, int likes) {
        mImageUrl = imageUrl;
        mCreator = creator;
        mLikes = likes;
        //mIngredient= ingredient;
       // mBereidingUrl= bereiding;
    }

    //hiermee kunnen we de waarde ophalen wanneer we deze methodes zouden toepassen
    public String getImageUrl() {
        return mImageUrl;
    }

    public String getCreator() {
        return mCreator;
    }

    public int getLikeCount() {
        return mLikes;
    }

   /* public JSONObject getIngredients() {
        return mIngredient;
    }*/

   /* public JSONObject getBereiding() {
        return mBereidingUrl;
    }*/
}
