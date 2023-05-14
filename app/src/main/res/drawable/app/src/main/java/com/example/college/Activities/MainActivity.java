package com.example.college.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.Color;
import android.widget.ImageView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.college.Notice.Delete_Notice;
import com.example.college.Notice.Upload_Notice;
import com.example.college.R;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
   CardView uploadNotice , uploadImage,addEBook,updateFaculty,delete;

  private ImageSlider imageSlider;
   private ArrayList<SlideModel> slideModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        // Get the ImageView from the custom title bar layout and set the image


            // Inflate the custom title bar layout
            View customTitleBar = getLayoutInflater().inflate(R.layout.titlebar_image, null);

            // Set the custom view for the ActionBar
            ActionBar actionBar1 = getSupportActionBar();
            actionBar1.setCustomView(customTitleBar);
            actionBar1.setDisplayShowCustomEnabled(true);

            // Get the ImageView from the custom title bar layout and set the image
            ImageView titleImage = customTitleBar.findViewById(R.id.titleImage);
            titleImage.setImageResource(R.drawable.logo);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#8692f7"));
        actionBar.setBackgroundDrawable(colorDrawable);


        imageSlider=findViewById(R.id.image_slider);
        slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.atal1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.atal2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.atal3, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.atal4, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.atal5, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels,ScaleTypes.FIT);



        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        uploadNotice = findViewById(R.id.addNotice);
        uploadNotice.setOnClickListener(this);


        uploadImage = findViewById(R.id.addGalleryImage1);
        uploadImage.setOnClickListener(this);

        addEBook=findViewById(R.id.addEBook);
        addEBook.setOnClickListener(this);

        updateFaculty = findViewById(R.id.faculty);
        updateFaculty.setOnClickListener(this);

        delete = findViewById(R.id.delete);
        delete.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case  R.id.addNotice:
                uploadNotice.setCardBackgroundColor(Color.YELLOW);
                 intent =new Intent(getApplicationContext(), Upload_Notice.class);
                startActivity(intent);
                uploadNotice.setCardBackgroundColor(Color.WHITE);
                break;
            case  R.id.addGalleryImage1:
                 intent =new Intent(getApplicationContext(), UploadImage.class);
                startActivity(intent);
                break;
            case R.id.addEBook:
                intent=new Intent(getApplicationContext(), UploadPDF.class);
                startActivity(intent);
                break;
            case R.id.faculty:
                intent=new Intent(getApplicationContext(), UpdateFaculty.class);
                startActivity(intent);
                break;

            case R.id.delete:
                intent=new Intent(getApplicationContext(), Delete_Notice.class);
                startActivity(intent);
                break;
        }
    }
}