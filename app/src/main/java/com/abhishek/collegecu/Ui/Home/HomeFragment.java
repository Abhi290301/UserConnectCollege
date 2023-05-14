package com.abhishek.collegecu.Ui.Home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.abhishek.collegecu.R;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import java.util.ArrayList;
public class HomeFragment extends Fragment {
    private ImageSlider imageSlider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        imageSlider = view.findViewById(R.id.image_slider);
        ImageView mapnav = view.findViewById(R.id.map);
        mapnav.setOnClickListener(v -> openMap());
        setSliderImages();
        return view;
    }
    private void setSliderImages() {
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.atal1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.atal2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.atal3, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.atal4, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.atal5, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
    }
    private void openMap() {
        Uri uri = Uri.parse("geo:0,0?q=Atal Bihari Vajpayee governement Institute of Engineering and Technology,Pragatinagar,Shimla");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }
}