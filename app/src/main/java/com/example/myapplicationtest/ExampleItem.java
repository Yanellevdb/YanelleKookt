package com.example.myapplicationtest;

public class ExampleItem {
    private String mImageUrl;
    private String mCreator;
    private String mDishType;
    private int mLikes;

    public ExampleItem(String imageUrl, String creator, String dishType, int likes) {
        mImageUrl = imageUrl;
        mCreator = creator;
        mDishType= dishType;
        mLikes = likes;
    }

    //hiermee kunnen we de waarde ophalen wanneer we deze methodes zouden toepassen
    public String getImageUrl() {
        return mImageUrl;
    }

    public String getCreator() {
        return mCreator;
    }

    public String getDishtype() {
        return mCreator;
    }

    public int getLikeCount() {
        return mLikes;
    }
}
