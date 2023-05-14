package com.abhishek.collegecu.Ui.Notice;

import android.annotation.SuppressLint;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import android.widget.ImageView;
import android.widget.TextView;


import com.abhishek.collegecu.R;
import com.bumptech.glide.Glide;




import java.util.ArrayList;


public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewAdapter> {
    private Context context;
    private ArrayList<NoticeData> list;

    public NoticeAdapter(Context context, ArrayList<NoticeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newsfeed_item_layout, parent, false);
        return new NoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdapter holder, @SuppressLint("RecyclerView") int position) {

        NoticeData currentData = list.get(position);
        holder.noticeTitle.setText(currentData.getTitle());
        Log.d("NoticeAdapter", "Error loading image: ");

        Glide.with(context)
                .load(currentData.getImage())
                .placeholder(R.drawable.logo)
                .into(holder.noticeImage);

        //Date and Time
        holder.date.setText(currentData.getDate());
        holder.time.setText(currentData.getTime());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeViewAdapter extends RecyclerView.ViewHolder {

        private TextView noticeTitle, date, time;
        private ImageView noticeImage;

        public NoticeViewAdapter(@NonNull View itemView) {
            super(itemView);

            noticeTitle = itemView.findViewById(R.id.noticeTitle);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            noticeImage = itemView.findViewById(R.id.noticeImage);
        }
    }
}
