package com.ssafy.recto.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ssafy.recto.R;
import com.ssafy.recto.api.CardData;
import com.ssafy.recto.publiccard.PublicFragmentMyAdapter;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    List<CardData> photoCards = new ArrayList<>();
    private Context context;
    private static OnItemClickListener iListener = null;

    public MyAdapter(Context context, List<CardData> img) {
        this.context = context;
        this.photoCards = img;
    }

    public interface OnItemClickListener
    {
        void onItemClick(View v, int pos);
    }

    public static void setOnItemClickListener(MyAdapter.OnItemClickListener listener)
    {
        iListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // context 와 parent.getContext() 는 같다.
        View view = LayoutInflater.from(context)
                .inflate(R.layout.main_card_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // URL을 이미지로 변경해주는 부분
        Glide.with(this.context).load(photoCards.get(position).getPhoto_url()).into(holder.imageView);
//        holder.imageView.setOnClickListener(onClickItem);
    }

    @Override
    public int getItemCount() {
        return photoCards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textview;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.card_tv);
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
            Log.d("어댑터", photoCards.toString());
        }
    }
}
