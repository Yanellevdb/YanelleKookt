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

    //niet zeker
    public void filterList(ArrayList<ExampleItem> filteredList){
        ArrayList= filteredList;
        notifyDataSetChanged();
    }

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
        String gerechtName= currentItem.getGerecht();
        int calorieCount= currentItem.getCalorieCount();

        holder.mTextViewGerecht.setText(gerechtName);
        holder.mTextViewCalorieen.setText("Calorieën: " + calorieCount);


        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextViewGerecht;
        public TextView mTextViewCalorieen;
        //  public TextView mTextViewIngredient;
        //  public TextView mTextViewBereiding;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewGerecht = itemView.findViewById(R.id.text_view_gerecht);
            mTextViewCalorieen = itemView.findViewById(R.id.text_view_calorieen);

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
