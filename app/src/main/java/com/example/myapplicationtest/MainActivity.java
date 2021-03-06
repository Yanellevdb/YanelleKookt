package com.example.myapplicationtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

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
    public static final String EXTRA_GERECHT= "gerechtName";
    public static final String EXTRA_CALORIEEN= "calorieCount";
    public static final String EXTRA_INGREDIENTS= "ingredients";

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

        getSupportActionBar().setTitle("Yanelle kookt");

        mRecyclerView= findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mExampleList= new ArrayList<>();
        mExampleAdapter= new ExampleAdapter(MainActivity.this, mExampleList);
        mRecyclerView.setAdapter(mExampleAdapter);

        mEdit= findViewById(R.id.queryInputText);
        mEdit.setText(DEFAULT_QUERY);

        mRequestQueue= Volley.newRequestQueue(this);
        parseJSON(DEFAULT_QUERY, "");

        //zoekfunctie
        findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query= mEdit.getText().toString();
                if(query.isEmpty()) return;
                parseJSON(query, "");
            }
        });

        //location
        Button locationBtn= findViewById(R.id.locationBtn);
        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query= "e";
                String sortAll="&cuisineType=Central%20Europe";
                parseJSON(query, sortAll);
            }
        });

        //filters
        Button sortLowFat= findViewById(R.id.lowFat);
        sortLowFat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String query= "e";
                String sortAll="&diet=low-fat";
                parseJSON(query, sortAll);
            }
        });

        Button sortLowCarb= findViewById(R.id.lowCarb);
        sortLowCarb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query= "e";
                String sortAll="&diet=low-carb";
                parseJSON(query, sortAll);
            }
        });

        Button sortHighProtein= findViewById(R.id.highProtein);
        sortHighProtein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query= "e";
                String sortAll="&diet=high-protein";
                parseJSON(query, sortAll);
            }
        });

        Button sortHighFiber= findViewById(R.id.highFiber);
        sortHighFiber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query= "a";
                String sortAll="&diet=high-fiber";
                parseJSON(query, sortAll);
            }
        });

        Button sortBalanced= findViewById(R.id.balanced);
        sortBalanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query= "e";
                String sortAll="&diet=balanced";
                parseJSON(query, sortAll);
            }
        });

        Button sortAll= findViewById(R.id.sortAll);
        sortAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query= "all";
                String sortAll="";
                parseJSON(query, sortAll);
            }
        });
    }

    private void parseJSON(String query, String query2){
        String url="https://api.edamam.com/api/recipes/v2?type=public&q=" + query +"&app_id=8faf6fa5&app_key=97f92e2c74c48a27e066bcc515e951b0" + query2;

        mExampleList.clear();

        JsonObjectRequest request= new JsonObjectRequest
                (Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray jsonArray= response.getJSONArray("hits");

                                    for(int i=0; i < jsonArray.length(); i++){
                                        JSONObject recipe= jsonArray.getJSONObject(i).getJSONObject("recipe");

                                        String gerechtName= recipe.getString("label");
                                        String imageUrl= recipe.getString("image");
                                        int calorieCount= recipe.getInt("calories");
                                        String ingredients= recipe.getString("ingredientLines");
                                        String diet= recipe.getString("dietLabels");

                                        mExampleList.add(new ExampleItem(imageUrl, gerechtName, calorieCount, ingredients));
                                    }

                                    mExampleAdapter= new ExampleAdapter(MainActivity.this, mExampleList);
                                    mRecyclerView.setAdapter(mExampleAdapter);
                                    mExampleAdapter.setOnItemClickListener(MainActivity.this);

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
        detailIntent.putExtra(EXTRA_GERECHT, clickedItem.getGerecht());
        detailIntent.putExtra(EXTRA_CALORIEEN, clickedItem.getCalorieCount());
        detailIntent.putExtra(EXTRA_INGREDIENTS, clickedItem.getIngredients());

        startActivity(detailIntent);
    }
}