package com.example.myapplicationtest;

//import static com.example.myapplicationtest.MainActivity.EXTRA_BEREIDING;
import static com.example.myapplicationtest.MainActivity.EXTRA_CREATOR;
import static com.example.myapplicationtest.MainActivity.EXTRA_INGREDIENTS;
import static com.example.myapplicationtest.MainActivity.EXTRA_LIKES;
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

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private Button mBtnSpeak;
    private EditText mTextEnter;
    private TextToSpeech mTextToSpeech;

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

        Intent intent= getIntent();
        String imageUrl= intent.getStringExtra(EXTRA_URL);
        String creatorName= intent.getStringExtra(EXTRA_CREATOR);
        int likeCount= intent.getIntExtra(EXTRA_LIKES, 0);

        String ingredients= intent.getStringExtra(EXTRA_INGREDIENTS);
        String[] splicedIngredient= ingredients.replaceAll("]", " ").replaceAll("\"", " ").replaceAll("\\[", " ").replaceAll("\"", "\n").split(",");
       // String bereiding= intent.getStringExtra(EXTRA_BEREIDING);

        getSupportActionBar().setTitle("Go back");

        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ImageView imageView= findViewById(R.id.image_view_detail);
        TextView textViewCreator= findViewById(R.id.text_view_creator_detail);
        TextView textViewLikes= findViewById(R.id.text_view_like_detail);
        Button share= findViewById(R.id.shareButton);
        Button btnSpeak=findViewById(R.id.speakButton);

        mTextToSpeech= new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i == TextToSpeech.SUCCESS){
                    int result= mTextToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

         TextView textViewIngredient= findViewById(R.id.text_view_ingredients_detail);
       // TextView textViewBereidingUrl= findViewById(R.id.text_view_bereiding_detail);

        Picasso.with(this).load(imageUrl).fit().centerInside().into(imageView);
        textViewCreator.setText(creatorName);
        textViewLikes.setText("Calorieën: " + likeCount);

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


        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s= textViewIngredient.getText().toString();
                int speech= mTextToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }
}