package com.abhishek.collegecu.Ui.Notice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.abhishek.collegecu.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NoticeFragment extends Fragment {
    private RecyclerView noticeRecycler;
    private ProgressBar progressBar;
    private ArrayList<NoticeData> list;
    private NoticeAdapter adapter;

    private DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notice, container, false);
        noticeRecycler = view.findViewById(R.id.deleteNoticeRecycler);
        progressBar =view.findViewById(R.id.progressBar);
        reference = FirebaseDatabase.getInstance().getReference().child("Notice");

        noticeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        noticeRecycler.setHasFixedSize(true);
        getNotice();

        return view;
    }

    private void getNotice() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    NoticeData data = snapshot1.getValue(NoticeData.class);
                    list.add(data);
                }
                adapter = new NoticeAdapter(getContext(),list);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                noticeRecycler.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
