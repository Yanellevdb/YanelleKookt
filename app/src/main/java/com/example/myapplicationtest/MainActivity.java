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
   // public static final String EXTRA_BEREIDING= "bereidingsUrl";

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
        mExampleList.clear();

        mRequestQueue= Volley.newRequestQueue(this);
        parseJSON(DEFAULT_QUERY, "");

        getSupportActionBar().setTitle("Yanelle kookt");

        //filters
        Button sortLowFat= findViewById(R.id.lowFat);
        sortLowFat.setOnClickListener(new View.OnClickListener() {
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


        findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query= mEdit.getText().toString();
                if(query.isEmpty()) return;
                parseJSON(query, "");
            }
        });


    }

    private void parseJSON(String query, String query2){
        String url="https://api.edamam.com/api/recipes/v2?type=public&q=" + query +"&app_id=8faf6fa5&app_key=97f92e2c74c48a27e066bcc515e951b0" + query2;

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

                        String gerechtName= recipe.getString("label");
                        String imageUrl= recipe.getString("image");
                        int calorieCount= recipe.getInt("calories");
                        String ingredients= recipe.getString("ingredientLines");
                        String diet= recipe.getString("dietLabels");
                        //JSONObject bereidingUrl= recipe.getJSONObject("totalNutrients");

                        mExampleList.add(new ExampleItem(imageUrl, gerechtName, calorieCount, ingredients)); //hier voegen we het toe aan de lijst
                    }

                    mExampleAdapter= new ExampleAdapter(MainActivity.this, mExampleList); //we geven het door aan de adapter
                    mRecyclerView.setAdapter(mExampleAdapter); //we koppelen de adapter aan de recycleview
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

    //hier gaan we een onclick uitvoeren, die gelinkt wordt met die van de adapter
    //ze geven eigenlijk de 'positie' door en dit zegt eigenlijk op welk item er werd geklikt
    //via detailIntent gaan we de info doorspelen naar de detailpagina
    @Override
    public void onItemClick(int position) {
        Intent detailIntent= new Intent(this, DetailActivity.class);
        ExampleItem clickedItem= mExampleList.get(position);

        detailIntent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_GERECHT, clickedItem.getGerecht());
        detailIntent.putExtra(EXTRA_CALORIEEN, clickedItem.getCalorieCount());
        detailIntent.putExtra(EXTRA_INGREDIENTS, clickedItem.getIngredients());
       // detailIntent.putExtra(EXTRA_BEREIDING, (Parcelable) clickedItem.getBereiding());

        startActivity(detailIntent);
    }
}