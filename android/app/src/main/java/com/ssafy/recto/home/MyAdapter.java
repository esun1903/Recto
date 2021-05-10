package com.ssafy.recto.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ssafy.recto.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private int itemList[];
//    private ArrayList<String> itemList;
    private Context context;
    private View.OnClickListener onClickItem;

    public MyAdapter(Context context, int itemList[], View.OnClickListener onClickItem) {
        this.context = context;
        this.itemList = itemList;
        this.onClickItem = onClickItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // context 와 parent.getContext() 는 같다.
        View view = LayoutInflater.from(context)
                .inflate(R.layout.main_card_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        String item = itemList.get(position);

        holder.imageView.setImageResource(itemList[position]);
        holder.imageView.setOnClickListener(onClickItem);
//        holder.textview.setText(item);
//        holder.textview.setTag(item);
    }

    @Override
    public int getItemCount() {
//        return itemList.size();
        return itemList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textview;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.card_tv);
        }
    }
}
