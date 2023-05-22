package com.abhishek.collegecu.Ui.Notice;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abhishek.collegecu.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewAdapter> {
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 1;
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

        // Date and Time
        holder.date.setText(currentData.getDate());
        holder.time.setText(currentData.getTime());
        holder.noticeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the image in a separate window
                Glide.with(context)
                        .load(currentData.getImage())
                        .into(new CustomTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                ImageView imageView = new ImageView(context);
                                imageView.setImageDrawable(resource);

                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setView(imageView);
                                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Save the image
                                        saveImageToGallery(currentData.getImage());
                                    }

                                    private void saveImageToGallery(String image) {
                                        if(ContextCompat.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                                            saveImage();
                                        }
                                    }

                                    private void saveImage() {
                                    }
                                });
                                builder.setNegativeButton("Close", null);
                                builder.show();
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                                // Not implemented
                            }
                        });
            }
        });
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

