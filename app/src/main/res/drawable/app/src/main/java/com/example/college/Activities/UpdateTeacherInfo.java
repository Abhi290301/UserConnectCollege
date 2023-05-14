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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.college.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class UpdateTeacherInfo extends AppCompatActivity {
    private StorageReference storageReference;
    private DatabaseReference reference;
    String downloadUrl;
    private final int REQ = 1;
    private Bitmap bitmap = null;
private ImageView updateTeacherImage;
private Button updateInfoButton,deleteInfoButton;
private String uniqueKey,category;
private FloatingActionButton updateImgbtn;
    private EditText updateTeacherName , updateTeacherPost , updateTeacherEmail;
private String name;
    private String email;
    private String post;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_teacher_info);
        updateTeacherImage = findViewById(R.id.updateTeacherImage);
         updateImgbtn = findViewById(R.id.updateImgbtn);
        updateInfoButton = findViewById(R.id.updateInfoButton);
        deleteInfoButton = findViewById(R.id.deleteInfoButton);
        updateTeacherName = findViewById(R.id.updateTeacherName);
        updateTeacherPost = findViewById(R.id.updateTeacherPost);
        updateTeacherEmail = findViewById(R.id.updateTeacherEmail);
        storageReference= FirebaseStorage.getInstance().getReference();
        reference= FirebaseDatabase.getInstance().getReference().child("Teacher");
        uniqueKey = getIntent().getStringExtra("uniqueKey");
        category = getIntent().getStringExtra("category");
        pd = new ProgressDialog(this);

        //getting data from the adapter
        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("email");
        post=getIntent().getStringExtra("post");
        String image = getIntent().getStringExtra("image");
        deleteInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                pd.setTitle("Deleting..");
                deleteData();
            }


        });

        //Setting data which get from Adapter
        if (image != null && !image.isEmpty()) {
            Picasso.get().load(image).placeholder(R.drawable.man).into(updateTeacherImage);
        } else {
            Picasso.get().load(R.drawable.man).into(updateTeacherImage);
        }
        updateTeacherName.setText(name);
        updateTeacherPost.setText(post);
        updateTeacherEmail.setText(email);

        updateImgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }


        });
        updateInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }

        });

    }


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
            updateTeacherImage.setImageBitmap(bitmap);
        }

    }

    //Validating User filling all the required fields or not
    private void checkValidation() {
        name = updateTeacherName.getText().toString();
        post = updateTeacherPost.getText().toString();
        email = updateTeacherEmail.getText().toString();
        if (name.isEmpty()){
            updateTeacherName.setError("Empty");
            updateTeacherName.requestFocus();
        }else if(post.isEmpty()){
            updateTeacherPost.setError("Empty");
            updateTeacherPost.requestFocus();
        } else if (email.isEmpty()) {
            updateTeacherEmail.setError("Empty");
            updateTeacherEmail.requestFocus();
        }else if (bitmap==null){
            Toast.makeText(this, "Select Image", Toast.LENGTH_SHORT).show();
            updateData("");

        }else {
            uploadImage();
        }


    }

    private void uploadImage() {


        // Convert bitmap to byte array and write it into the output stream
        ByteArrayOutputStream outputImage = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputImage);
        byte[] image = outputImage.toByteArray();

        final String fileName = System.currentTimeMillis() + ".jpg";
        final StorageReference filepath = storageReference.child("Teacher").child(fileName);

        final UploadTask uploadTask = filepath.putBytes(image);

        uploadTask.addOnCompleteListener(UpdateTeacherInfo.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    updateData(downloadUrl);
                                }
                            });
                        }
                    });
                } else {

                    Toast.makeText(UpdateTeacherInfo.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateData(String s) {
        HashMap hashMap = new HashMap();

        hashMap.put("name",name);
        hashMap.put("email",email);
        hashMap.put("post",post);
        hashMap.put("image",s);



        reference.child(category).child(uniqueKey).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(UpdateTeacherInfo.this, "Data Updated Successfully.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), UpdateFaculty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateTeacherInfo.this, "Error Occur"+e.getStackTrace(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteData() {
        reference.child(category).child(uniqueKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pd.dismiss();
                Toast.makeText(UpdateTeacherInfo.this, "Data Deleted Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateTeacherInfo.this, UpdateFaculty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UpdateTeacherInfo.this, "Failed to delete data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}