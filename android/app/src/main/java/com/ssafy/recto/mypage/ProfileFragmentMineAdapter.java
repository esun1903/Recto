package com.ssafy.recto.mypage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ssafy.recto.R;

public class ProfileFragmentMineAdapter extends RecyclerView.Adapter<ProfileFragmentMineAdapter.MyViewHolder> {

    int[] images;
    Context context;
    private static OnItemClickListener iListener = null;

    public ProfileFragmentMineAdapter(Context ct, int[] img) {
        context = ct;
        images = img;
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
        View view = inflater.inflate(R.layout.profile_fragment_mine_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
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
        }
    }
}
