package com.example.myapplicationtest;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<ExampleItem> mExampleList;
    private OnItemClickListener mListener;
    private Object ArrayList;

    public interface  OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener= listener;
    }

    //items doorgeven aan de adapter
    public ExampleAdapter(Context context, ArrayList<ExampleItem> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.example_item, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ExampleItem currentItem= mExampleList.get(position);

        String imageUrl= currentItem.getImageUrl();
        String creatorName= currentItem.getCreator();
        int likeCount= currentItem.getLikeCount();
      //  JSONObject ingredient= currentItem.getIngredients();
      //  JSONObject bereidingsUrl= currentItem.getBereiding();

        holder.mTextViewCreator.setText(creatorName);
        holder.mTextViewLikes.setText("Calorieën: " + likeCount);
      //  holder.mTextViewIngredient.setText((CharSequence) ingredient);
      //  holder.mTextViewBereiding.setText((CharSequence) bereidingsUrl);

        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextViewCreator;
        public TextView mTextViewLikes;
      //  public TextView mTextViewIngredient;
      //  public TextView mTextViewBereiding;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewCreator = itemView.findViewById(R.id.text_view_creator);
            mTextViewLikes = itemView.findViewById(R.id.text_view_likes);
        //    mTextViewIngredient= itemView.findViewById(R.id.text_view_ingredients_detail);
        //    mTextViewBereiding= itemView.findViewById(R.id.text_view_bereiding_detail);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position= getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
