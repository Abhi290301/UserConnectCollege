package com.example.college.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.college.Models_Adapters.TeacherDataModel;
import com.example.college.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddTeachers extends AppCompatActivity {
    private final int REQ = 1;
    private CircleImageView addTeacherImage;
    private EditText addTeacherName, addTeacherEmail, addTeacherPost;
    private Spinner addTeacherCategory;
    private Button addTeacherButton;
    private Bitmap bitmap = null;
    private String category;
    private ProgressDialog progressBar;
    private String name,email,post,downloadUrl="";
    private StorageReference storageReference,getStorageReference;
    private DatabaseReference reference,databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teachers);
        addTeacherName =findViewById(R.id.addTeacherName);
        addTeacherImage = findViewById(R.id.addTeacherImage);
        addTeacherEmail = findViewById(R.id.addTeacherEmail);
        addTeacherPost = findViewById(R.id.addTeacherPost);
        addTeacherCategory = findViewById(R.id.addTeacherCategory);
        addTeacherButton = findViewById(R.id.addTeacherButton);
        storageReference= FirebaseStorage.getInstance().getReference();
        reference= FirebaseDatabase.getInstance().getReference().child("Teacher");
        progressBar = new ProgressDialog(this);
        addTeacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }
//Selecting image
    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage, REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (Exception e) {
                e.getStackTrace();
            }
            addTeacherImage.setImageBitmap(bitmap);
        }

//Selecting category from spinner
        String[] item = new String[]{"--Select Category--", "Computer Science", "Electronics", "Electrical"};
        addTeacherCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, item));

        addTeacherCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = addTeacherCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // Add Teacher Button
        addTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }


        });
    }
    private void checkValidation() {
        name = addTeacherName.getText().toString();
        email = addTeacherEmail.getText().toString();
        post =addTeacherPost.getText().toString();
        if(name.isEmpty()){
            addTeacherName.setError("Name required!!");
            addTeacherName.requestFocus();
        } else if (email.isEmpty()) {
            addTeacherEmail.setText("Email required!!");
            addTeacherEmail.requestFocus();
        } else if (post.isEmpty()) {
            addTeacherPost.setError("Post Empty!!");
            addTeacherPost.requestFocus();
        }else if(category.equals("--Select Category--")){
            Toast.makeText(this, "Select category!", Toast.LENGTH_SHORT).show();
        } else if (bitmap==null) {
           uploadData();
        } else {
            InsertImage();
        }
    }

    private void InsertImage() {
        progressBar.setMessage("Uploading...");
        progressBar.show();

        // Convert bitmap to byte array and write it into the output stream
        ByteArrayOutputStream outputImage = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputImage);
        byte[] image = outputImage.toByteArray();

        final StorageReference filepath = storageReference.child("Teacher").child(image + "jpg");
        final UploadTask uploadTask = filepath.putBytes(image);

        uploadTask.addOnCompleteListener(AddTeachers.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    uploadData();
                                }
                            });
                        }
                    });
                } else {
                    progressBar.dismiss();
                    Toast.makeText(AddTeachers.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void uploadData() {
        databaseReference=reference.child(category);
        final String uniqueKey = databaseReference.push().getKey();


//        Calendar calForDate = Calendar.getInstance();
//        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yy");
//        String date = currentDate.format(calForDate.getTime());
//
//        Calendar calForTime = Calendar.getInstance();
//        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss a");
//        String time =currentTime.format(calForTime.getTime());

        TeacherDataModel teacherDataModel =new TeacherDataModel(name,email,post,downloadUrl,uniqueKey);
        databaseReference.child(uniqueKey).setValue(teacherDataModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressBar.dismiss();
                Toast.makeText(AddTeachers.this, "Uplaoded", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddTeachers.this, "Something Went Wrong!!Try Again", Toast.LENGTH_SHORT).show();
            }
        });

    }
}