package com.abhishek.collegecu.Ui.Faculty;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhishek.collegecu.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeacherDataSetAdapter extends RecyclerView.Adapter<TeacherDataSetAdapter.TeacherView> {
    private List<TeacherDataModel> list;
    private Context context;


    public TeacherDataSetAdapter(List<TeacherDataModel> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public TeacherView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.teacher_faculty_item_layout, parent, false);
        return new TeacherView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherView holder, int position) {
        TeacherDataModel item = list.get(position);
        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.post.setText(item.getPost());
        if (item.getImage() != null && !item.getImage().isEmpty()) {
            try {
                Glide.with(context).load(item.getImage()).into(holder.image);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TeacherView extends RecyclerView.ViewHolder {
        private TextView name, email, post;

        private ImageView image;

        public TeacherView(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.teacherName);
            email = itemView.findViewById(R.id.teacherEmail);
            post = itemView.findViewById(R.id.teacherPost);
            image = itemView.findViewById(R.id.profileImage);
        }
    }
}
