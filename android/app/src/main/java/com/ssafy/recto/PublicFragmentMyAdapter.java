package com.ssafy.recto;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class PublicFragmentMyAdapter extends RecyclerView.Adapter<PublicFragmentMyAdapter.MyViewHolder> {

    int images[];
    Context context;

    public PublicFragmentMyAdapter(Context ct, int img[]) {
        context = ct;
        images = img;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.public_fragment_card_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);

//        // 카드 목록 클릭하면 카드 상세보기 페이지로 이동
//        holder.publicCardLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, PublicFragmentCardDetail.class);
//                // 누를 때 아이템 정보도 같이 넘겨줌
//                intent.putExtra("images", images[position]);
//                context.startActivity(intent);
//            }
//        });
    }
    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ConstraintLayout publicCardLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            publicCardLayout = itemView.findViewById(R.id.publicCardLayout);

            imageView.setClickable(true);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(v.getContext(), PublicFragmentCardDetail.class);
                        intent.putExtra("number", pos);
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }
    }
}