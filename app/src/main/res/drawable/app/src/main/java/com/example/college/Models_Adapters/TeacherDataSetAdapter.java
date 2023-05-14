package com.example.college.Models_Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.college.Activities.UpdateTeacherInfo;
import com.example.college.R;
import com.squareup.picasso.Picasso;

import android.content.Context;

import java.util.List;

public class TeacherDataSetAdapter extends RecyclerView.Adapter<TeacherDataSetAdapter.TeacherView> {
    private List<TeacherDataModel> list;
    private Context context;
    private String category;

    public TeacherDataSetAdapter(List<TeacherDataModel> list, Context context, String category) {
        this.list = list;
        this.context = context;
        this.category=category;
    }

    @NonNull
    @Override
    public TeacherView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(context).inflate(R.layout.teacher_faculty_item_layout,parent,false);
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
                Picasso.get().load(item.getImage()).into(holder.image);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateTeacherInfo.class);
                intent.putExtra("name",item.getName());
                intent.putExtra("email",item.getEmail());
                intent.putExtra("post",item.getPost());
                intent.putExtra("image",item.getImage());
                intent.putExtra("uniqueKey",item.getUniqueKey());
                intent.putExtra("category",category);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TeacherView extends RecyclerView.ViewHolder {
        private TextView name,email,post;
        private Button update;
        private ImageView image;
        public TeacherView(@NonNull View itemView) {
            super(itemView);
            name =itemView.findViewById(R.id.teacherName);
            email=itemView.findViewById(R.id.teacherEmail);
            post=itemView.findViewById(R.id.teacherPost);
            update=itemView.findViewById(R.id.updateTeacherInfo);
            image=itemView.findViewById(R.id.profileImage);
        }
    }
}
