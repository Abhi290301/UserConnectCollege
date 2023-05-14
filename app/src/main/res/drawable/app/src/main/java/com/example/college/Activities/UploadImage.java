package com.example.college.Activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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

public class UploadImage extends AppCompatActivity {
    private Spinner imageCategory;
    private ImageView selectImage;
    private Button uploadImage;
    private ImageView galleryImageView;
    private String category;
    private ProgressDialog pd;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    String downloadUrl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        selectImage = findViewById(R.id.addGalleryImage);
        imageCategory = findViewById(R.id.imageCategory);
        uploadImage = findViewById(R.id.uploadImageButton);
        galleryImageView = findViewById(R.id.galleryImageView);
        pd=new ProgressDialog(this);
        storageReference= FirebaseStorage.getInstance().getReference().child("gallery");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("gallery");




        //setting Spinner

        String[] item= new String[]{"--Select Category--", "Convocation","Independence Day","Cultural Events","Mid Semester Test", "End Semester Examination","End Semester Practical"};
        imageCategory.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,item));

        imageCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = imageCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(galleryImageView==null) {
                    Toast.makeText(UploadImage.this, "Please upload image", Toast.LENGTH_SHORT).show();
                }
                    if(category.equals("--Select Category--")){
                        Toast.makeText(UploadImage.this, "Select Category first.", Toast.LENGTH_SHORT).show();

                }else {
                    pd.setMessage("Uploading the content!!");
                    pd.show();
                    uploadImage();

                }
            }

            private void uploadImage() {
                pd.setMessage("Uploading...");
                pd.show();
                ByteArrayOutputStream outputImage = new ByteArrayOutputStream();
                byte[] image=outputImage.toByteArray();
                final StorageReference filepath;
                filepath = storageReference.child(image + "jpg");
                final UploadTask uploadTask = filepath.putBytes(image);
                uploadTask.addOnCompleteListener(UploadImage.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            downloadUrl=String.valueOf(uri);

                                            uploadData();
                                        }

                                    });
                                }
                            });
                        }else {
                            pd.dismiss();
                            Toast.makeText(UploadImage.this,"Something Went Wrong",Toast.LENGTH_SHORT);
                        }
                    }
                });

            }
            private void uploadData() {
                databaseReference=databaseReference.child(category);
                final String uniqueKey= databaseReference.push().getKey();
                databaseReference.child(uniqueKey).setValue(downloadUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UploadImage.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                        pd.dismiss();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadImage.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        //picking Image from gallery
        ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {

                galleryImageView.setImageURI(result);
            }
        });

        selectImage.setOnClickListener(view -> launcher.launch("image/*"));
    }
}