package com.example.college.Notice;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Upload_Notice extends AppCompatActivity {
    private CardView addImage;
    public ImageView previewImage;


    private EditText noteTitle;
    private Button uploadNoticeBUTTON;
    private DatabaseReference reference, dbRef;
    private StorageReference storageReference;
    String downloadUrl = "";
    private ProgressDialog progressBar;
    private final int REQ = 1;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notice);
        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        progressBar = new ProgressDialog(this);

        addImage = findViewById(R.id.addImage);
        previewImage = findViewById(R.id.noticeImageView);
        noteTitle = findViewById(R.id.noticeTitle);
        uploadNoticeBUTTON = findViewById(R.id.uploadNotice);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }


        });

        uploadNoticeBUTTON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noteTitle.getText().toString().isEmpty()) {
                    noteTitle.setError("Title Required");
                    noteTitle.requestFocus();
                } else if (bitmap == null) {
                    uploadData();

                } else {
                    uploadImage();
                }
            }


            private void uploadImage() {
                progressBar.setMessage("Uploading...");
                progressBar.show();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
                byte[] image = byteArrayOutputStream.toByteArray();
                final StorageReference filepath;
                filepath = storageReference.child("Notice").child("notice_image.jpg"); // Use a static file name
                final UploadTask uploadTask = filepath.putBytes(image);
                uploadTask.addOnCompleteListener(Upload_Notice.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                            Toast.makeText(Upload_Notice.this, "Something went wrong during upload", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Error uploading image", task.getException());
                        }
                    }
                });
            }


            //To Store Data in Firebase
            private void uploadData() {
                dbRef = reference.child("Notice");
                final String uniqueKey = dbRef.push().getKey();
                String title = noteTitle.getText().toString();

                Calendar calForDate = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yy");
                String date = currentDate.format(calForDate.getTime());

                Calendar calForTime = Calendar.getInstance();
                SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss a");
                String time = currentTime.format(calForTime.getTime());

                NoticeData noticeData = new NoticeData(title, downloadUrl, date, time, uniqueKey);
                dbRef.child(uniqueKey).setValue(noticeData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressBar.dismiss();
                        Toast.makeText(Upload_Notice.this, "Uplaoded", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Upload_Notice.this, "Something Went Wrong!!Try Again", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }
    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,REQ);

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ && resultCode==RESULT_OK){
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            previewImage.setImageBitmap(bitmap);
        }
    }
}
