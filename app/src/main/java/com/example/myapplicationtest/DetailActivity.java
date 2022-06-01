package com.example.myapplicationtest;

import static com.example.myapplicationtest.MainActivity.EXTRA_CREATOR;
import static com.example.myapplicationtest.MainActivity.EXTRA_LIKES;
import static com.example.myapplicationtest.MainActivity.EXTRA_URL;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent= getIntent();
        String imageUrl= intent.getStringExtra(EXTRA_URL);
        String creatorName= intent.getStringExtra(EXTRA_CREATOR);
        int likeCount= intent.getIntExtra(EXTRA_LIKES, 0);

        ImageView imageView= findViewById(R.id.image_view_detail);
        TextView textViewCreator= findViewById(R.id.text_view_creator_detail);
        TextView textViewLikes= findViewById(R.id.text_view_like_detail);
        Button share= findViewById(R.id.shareButton);

        Picasso.with(this).load(imageUrl).fit().centerInside().into(imageView);
        textViewCreator.setText(creatorName);
        textViewLikes.setText("Calorieën: " + likeCount);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent= new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, imageUrl);
                startActivity(Intent.createChooser(shareIntent, "Share link"));
            }
        });
    }
}