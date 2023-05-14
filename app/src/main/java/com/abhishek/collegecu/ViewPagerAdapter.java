package com.abhishek.collegecu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.abhishek.collegecu.Ui.About.AboutFragment;
import com.abhishek.collegecu.Ui.Faculty.FacultyFragment;
import com.abhishek.collegecu.Ui.Gallery.GalleryFragment;
import com.abhishek.collegecu.Ui.Home.HomeFragment;
import com.abhishek.collegecu.Ui.Notice.NoticeFragment;


public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new HomeFragment();
            case 1: return new NoticeFragment();
            case 2: return new FacultyFragment();
            case 3: return new GalleryFragment();
            case 4: return new AboutFragment();
            default: return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
