package com.example.myapplicationtest;

//import static com.example.myapplicationtest.MainActivity.EXTRA_BEREIDING;
import static com.example.myapplicationtest.MainActivity.EXTRA_CREATOR;
import static com.example.myapplicationtest.MainActivity.EXTRA_INGREDIENTS;
import static com.example.myapplicationtest.MainActivity.EXTRA_LIKES;
import static com.example.myapplicationtest.MainActivity.EXTRA_URL;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    Button btnSpeak;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent= getIntent();
        String imageUrl= intent.getStringExtra(EXTRA_URL);
        String creatorName= intent.getStringExtra(EXTRA_CREATOR);
        int likeCount= intent.getIntExtra(EXTRA_LIKES, 0);

        String ingredients= intent.getStringExtra(EXTRA_INGREDIENTS);
        String[] splicedIngredient= ingredients.replaceAll("]", " ").replaceAll("\"", " ").replaceAll("\\[", " ").split(",");
       // String bereiding= intent.getStringExtra(EXTRA_BEREIDING);

        ImageView imageView= findViewById(R.id.image_view_detail);
        TextView textViewCreator= findViewById(R.id.text_view_creator_detail);
        TextView textViewLikes= findViewById(R.id.text_view_like_detail);
        Button share= findViewById(R.id.shareButton);
        Button btnSpeak=findViewById(R.id.speakButton);

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
                Intent shareIntent= new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, imageUrl);
                startActivity(Intent.createChooser(shareIntent, "Share link"));
            }
        });


        textToSpeech= new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i==TextToSpeech.SUCCESS){
                    int language= textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s= textViewCreator.getText().toString();
                int speech= textToSpeech.speak(s, textToSpeech.QUEUE_FLUSH, null);
            }
        });
    }
}