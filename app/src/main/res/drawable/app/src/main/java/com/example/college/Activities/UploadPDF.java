package com.example.college.Activities;
import androidx.activity.result.ActivityResultLauncher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.LinearLayout;

import com.example.college.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;

public class UploadPDF extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private String pdfName,title;
    private LinearLayout addPDF;
    private EditText pdfTitle;
    private TextView pdfNameTV;
     private  Uri pdfData;
    private Button uploadPDFBUTTON;
    private DatabaseReference dbReference;
    private StorageReference storageReference;
    String downloadUrl= "";
    private ProgressDialog progressBar;




    private ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);
        dbReference = FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();
        progressBar = new ProgressDialog(this);
        addPDF=findViewById(R.id.selectPDF);
        pdfTitle=findViewById(R.id.pdfTitle);
        uploadPDFBUTTON=findViewById(R.id.uploadPDF);
        pdfNameTV =findViewById(R.id.pdfNameTV);

        addPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        uploadPDFBUTTON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = pdfTitle.getText().toString();
                if (title.isEmpty()) {
                    pdfTitle.setError("Title Required");
                    pdfTitle.requestFocus();
                } else if (pdfData == null) {
                    Toast.makeText(UploadPDF.this, "Please select a file first", Toast.LENGTH_SHORT).show();
                }else {
                    uploadPdf();
                }
            }


        });
        }

      //Opening file manager for selecting pdf
    private void openGallery() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select pdf file"),PERMISSION_REQUEST_CODE);
    }
    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode , Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == PERMISSION_REQUEST_CODE && resultCode == RESULT_OK){
             pdfData = data.getData();

            if(pdfData.toString().startsWith("content://")){
                Cursor cursor = null;
                try {
                    cursor = UploadPDF.this.getContentResolver().query(pdfData,null,null,null);
                    if(cursor!=null && cursor.moveToFirst()){
                        pdfName=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (pdfData.toString().startsWith("file://")) {
                pdfName = new File(pdfData.toString()).getName();

            }
            pdfNameTV.setText(pdfName);
        }
    }

    //uploadPDF to Activity
    private void uploadPdf() {
        progressBar.setTitle("Please wait....");
        progressBar.setMessage("Uploading file..");
        progressBar.show();
        StorageReference reference1 = storageReference.child("pdf/"+pdfName+"-"+System.currentTimeMillis()+".pdf");
        reference1.putFile(pdfData)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri =uriTask.getResult();
                        uploadData(String.valueOf(uri));
                    }


                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.dismiss();
                        Toast.makeText(UploadPDF.this, ""+e.getStackTrace(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //Upload PDF to Database
    private void uploadData(String downloadUrl) {
        String uniquekey = dbReference.child("pdf").push().getKey();

        HashMap hashMap=new HashMap();
        hashMap.put("pdfTitle",title);
        hashMap.put("pdfUrl",downloadUrl);

        dbReference.child("pdf").child(uniquekey).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressBar.dismiss();
                Toast.makeText(UploadPDF.this, "File uploaded.", Toast.LENGTH_SHORT).show();
                pdfTitle.setText(" ");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.dismiss();
                Toast.makeText(UploadPDF.this, "Failed"+e.getStackTrace(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
