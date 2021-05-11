package com.ssafy.recto.publiccard;


import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ssafy.recto.R;
import com.ssafy.recto.api.CardData;

import java.util.ArrayList;
import java.util.List;

public class PublicFragmentMyAdapter extends RecyclerView.Adapter<PublicFragmentMyAdapter.MyViewHolder> {

    List<CardData> photoCards = new ArrayList<>();
//    int[] images;
    Context context;
    private static OnItemClickListener iListener = null;

    public PublicFragmentMyAdapter(Context ct, List<CardData> img){
        context = ct;
        photoCards = img;
//        images = img;
    }

    public interface OnItemClickListener
    {
        void onItemClick(View v, int pos);
    }

    public static void setOnItemClickListener(OnItemClickListener listener)
    {
        iListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.public_fragment_card_row, parent, false);
        return new MyViewHolder(view);
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_fragment_card_row, parent, false);
//        MyViewHolder viewHolder = new MyViewHolder(itemView);
//
//        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        CardData photoCard = photoCards.get(position);
//        holder.imageView.setImageResource(images[position]);
//        holder.imageView.setImageResource(photoCards.get(position).getPhoto_url());
        Glide.with(holder.itemView.getContext()).load(photoCards.get(position).getPhoto_url()).into(holder.imageView);
//        Log.d("success", photoCards.get(position).toString());

//        Glide.with(holder.itemView.getContext()).load("https://project-recto.s3.ap-northeast-2.amazonaws.com/samplephoto3.png").into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return photoCards.size();
//        return images.length;
    }

//    public void setItems(List<CardData> items) {
//        this.photoCards = items;
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            imageView.setClickable(true);

            imageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        iListener.onItemClick(v, pos);
                    }
                }
            });
            Log.d("제발..", photoCards.toString());
        }
    }
}