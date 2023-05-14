package com.example.college.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.college.Models_Adapters.TeacherDataModel;
import com.example.college.Models_Adapters.TeacherDataSetAdapter;
import com.example.college.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateFaculty extends AppCompatActivity {
   private RecyclerView csDepartment,eceDepartment,eeDepartment;
   private LinearLayout csNoData,eceNoData,eeNoData;
    private List<TeacherDataModel> list1,list2,list3;
    private DatabaseReference reference,dbRef;
    FloatingActionButton fab;
    private TeacherDataSetAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);

        fab = findViewById(R.id.fab);
        csDepartment = findViewById(R.id.computerScienceDepartment);
        eceDepartment = findViewById(R.id.electronicsDepartment);
        eeDepartment = findViewById(R.id.electricalDepartment);
        reference= FirebaseDatabase.getInstance().getReference().child("Teacher");
        csNoData = findViewById(R.id.csNoData);
        eceNoData = findViewById(R.id.eceNoData);
        eeNoData = findViewById(R.id.eeNoData);


        csDepartment();
        eceDepartment();
        eeDepartment();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddTeachers.class));
            }
        });
    }

    private void csDepartment() {
        dbRef=reference.child("Computer Science");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<>();
                if(!snapshot.exists()){
                    csNoData.setVisibility(View.VISIBLE);
                    csDepartment.setVisibility(View.GONE);
                }else {
                    csNoData.setVisibility(View.GONE);
                    csDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                        TeacherDataModel dataModel=dataSnapshot.getValue(TeacherDataModel.class);
                        list1.add(dataModel);
                    }
                    csDepartment.setHasFixedSize(true);
                    csDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter  = new TeacherDataSetAdapter(list1 , UpdateFaculty.this,"Computer Science");
                    csDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void eceDepartment() {
        dbRef=reference.child("Electronics");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2 = new ArrayList<>();
                if(!snapshot.exists()){
                    eceNoData.setVisibility(View.VISIBLE);
                    eceDepartment.setVisibility(View.GONE);
                }else {
                    eceNoData.setVisibility(View.GONE);
                    eceDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                        TeacherDataModel dataModel=dataSnapshot.getValue(TeacherDataModel.class);
                        list2.add(dataModel);
                    }
                    eceDepartment.setHasFixedSize(true);
                    eceDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter  = new TeacherDataSetAdapter(list2 , UpdateFaculty.this, "Electronics");
                    eceDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void eeDepartment() {
        dbRef=reference.child("Electrical");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3 = new ArrayList<>();
                if(!snapshot.exists()){
                    eeNoData.setVisibility(View.VISIBLE);
                    eeDepartment.setVisibility(View.GONE);
                }else {
                    eeNoData.setVisibility(View.GONE);
                    eeDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                        TeacherDataModel dataModel=dataSnapshot.getValue(TeacherDataModel.class);
                        list3.add(dataModel);
                    }
                    eeDepartment.setHasFixedSize(true);
                    eeDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter  = new TeacherDataSetAdapter(list3 , UpdateFaculty.this,"Electrical");
                    eeDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}