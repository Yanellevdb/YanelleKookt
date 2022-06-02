package com.example.myapplicationtest;

import static com.example.myapplicationtest.MainActivity.EXTRA_GERECHT;
import static com.example.myapplicationtest.MainActivity.EXTRA_INGREDIENTS;
import static com.example.myapplicationtest.MainActivity.EXTRA_CALORIEEN;
import static com.example.myapplicationtest.MainActivity.EXTRA_URL;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    TextToSpeech t1;
    Button b1;

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

//        ed1=(EditText)findViewById(R.id.editText);
        b1=(Button)findViewById(R.id.speechButton);

        Intent intent= getIntent();
        String imageUrl= intent.getStringExtra(EXTRA_URL);
        String gerechtName= intent.getStringExtra(EXTRA_GERECHT);
        int calorieCount= intent.getIntExtra(EXTRA_CALORIEEN, 0);

        String ingredients= intent.getStringExtra(EXTRA_INGREDIENTS);
        String[] splicedIngredient = ingredients.replaceAll("\",", "\n").replaceAll("]", " ").replaceAll("\\[", "").replaceAll("\"", "\n").split(",");        // String bereiding= intent.getStringExtra(EXTRA_BEREIDING);

        getSupportActionBar().setTitle("Go back");

        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ImageView imageView= findViewById(R.id.image_view_detail);
        TextView textViewGerecht= findViewById(R.id.text_view_gerecht_detail);
        TextView textViewCalorieen= findViewById(R.id.text_view_calorieen_detail);
        Button share= findViewById(R.id.shareButton);

        b1=findViewById(R.id.speechButton);

        t1= new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int lang= t1.setLanguage(Locale.US);

                    if (lang == TextToSpeech.LANG_MISSING_DATA || lang == TextToSpeech.LANG_NOT_SUPPORTED){
                        Toast.makeText(DetailActivity.this, "Language not supported", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(DetailActivity.this, "Language supported", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data= textViewGerecht.getText().toString();
                t1.speak(data, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        TextView textViewIngredient= findViewById(R.id.text_view_ingredients_detail);

        Picasso.with(this).load(imageUrl).fit().centerInside().into(imageView);
        textViewGerecht.setText(gerechtName);
        textViewCalorieen.setText("Calorieën: " + calorieCount);

        for(int i=0; i < splicedIngredient.length; i++){
            textViewIngredient.append(splicedIngredient[i]);
        }

        //textViewIngredient.setText(ingredients);
        //textViewBereidingUrl.setText(bereiding);

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