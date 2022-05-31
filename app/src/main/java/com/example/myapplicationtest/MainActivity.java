package com.example.myapplicationtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExampleAdapter.OnItemClickListener {
    public static final String EXTRA_URL= "imageUrl";
    public static final String EXTRA_CREATOR= "creatorName";
    public static final String EXTRA_DISHTYPE="dishType";
    public static final String EXTRA_LIKES= "likeCount";

    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;

    private EditText mEdit;
    private static String DEFAULT_QUERY= "chicken";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEdit= findViewById(R.id.queryInputText);
        mEdit.setText(DEFAULT_QUERY);

        mRecyclerView= findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mExampleList= new ArrayList<>();
        mExampleAdapter= new ExampleAdapter(MainActivity.this, mExampleList);
        mRecyclerView.setAdapter(mExampleAdapter);

        mRequestQueue= Volley.newRequestQueue(this);
        parseJSON(DEFAULT_QUERY);

        findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query= mEdit.getText().toString();
                if(query.isEmpty()) return;
                parseJSON(query);
            }
        });
    }

    private void parseJSON(String query){
        String url="https://api.edamam.com/api/recipes/v2?type=public&q=" + query +"&app_id=8faf6fa5&app_key=97f92e2c74c48a27e066bcc515e951b0";

        mExampleList.clear();

        //Krijg je object terug (tussen curly braces) of array?
        JsonObjectRequest request= new JsonObjectRequest
                (Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray= response.getJSONArray("hits");

                    for(int i=0; i < jsonArray.length(); i++){
                        JSONObject recipe= jsonArray.getJSONObject(i).getJSONObject("recipe");

                        String creatorName= recipe.getString("label");
                        String imageUrl= recipe.getString("image");
                        String dishType= recipe.getString("dishType");
                        int likeCount= recipe.getInt("calories");

                        mExampleList.add(new ExampleItem(imageUrl, creatorName, dishType, likeCount)); //hier voegen we het toe aan de lijst
                    }

                   // mExampleAdapter= new ExampleAdapter(MainActivity.this, mExampleList); //we geven het door aan de adapter
                  //  mRecyclerView.setAdapter(mExampleAdapter); //we koppelen de adapter aan de recycleview
                  //  mExampleAdapter.setOnItemClickListener(MainActivity.this);

                    mExampleAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent= new Intent(this, DetailActivity.class);
        ExampleItem clickedItem= mExampleList.get(position);

        detailIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_CREATOR, clickedItem.getCreator());
        detailIntent.putExtra(EXTRA_LIKES, clickedItem.getLikeCount());

        startActivity(detailIntent);
    }
}