package com.example.myapplicationtest;

import static com.example.myapplicationtest.MainActivity.EXTRA_GERECHT;
import static com.example.myapplicationtest.MainActivity.EXTRA_INGREDIENTS;
import static com.example.myapplicationtest.MainActivity.EXTRA_CALORIEEN;
import static com.example.myapplicationtest.MainActivity.EXTRA_URL;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    TextToSpeech t1;
    Button b1;
    TextView ingredientList;

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setTitle("Go back");
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ImageView imageView= findViewById(R.id.image_view_detail);
        TextView textViewGerecht= findViewById(R.id.text_view_gerecht_detail);
        TextView textViewCalorieen= findViewById(R.id.text_view_calorieen_detail);
        Button share= findViewById(R.id.shareButton);
        TextView textViewIngredient= findViewById(R.id.text_view_ingredients_detail);

        Intent intent= getIntent();
        String imageUrl= intent.getStringExtra(EXTRA_URL);
        String gerechtName= intent.getStringExtra(EXTRA_GERECHT);
        int calorieCount= intent.getIntExtra(EXTRA_CALORIEEN, 0);
        String ingredients= intent.getStringExtra(EXTRA_INGREDIENTS);

        String[] splicedIngredient = ingredients.replaceAll("\",", "\n").replaceAll("]", " ").replaceAll("\\[", "").replaceAll("\"", "\n").split(",");        // String bereiding= intent.getStringExtra(EXTRA_BEREIDING);
        for(int i=0; i < splicedIngredient.length; i++){
            textViewIngredient.append(splicedIngredient[i]);
        }

        Picasso.with(this).load(imageUrl).fit().centerInside().into(imageView);
        textViewGerecht.setText(gerechtName);
        textViewCalorieen.setText("Calorieën: " + calorieCount);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Bekijk dit recept!");
                intent.setType("text/plain");
                startActivity(intent);
            }
        });
    }
}