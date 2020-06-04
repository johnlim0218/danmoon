package com.danmoon.project.Adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.danmoon.project.Fragment.PostlistFragment;
import com.danmoon.project.Fragment.SearchFragment;

import java.util.ArrayList;
import java.util.List;

public class Material_MidLayoutFragment_Adapter extends FragmentPagerAdapter {

    private final int VIEW_PAGE_SIZE = 2;
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public Material_MidLayoutFragment_Adapter(FragmentManager fm){
        super(fm);


    }


//    public void addFragment(Fragment fragment, String title){
//
//        mFragmentList.add(fragment);
//        mFragmentTitleList.add(title);
//    }
//    public CharSequence getPageTitle(int position) {
//        return mFragmentTitleList.get(position);
//    }


    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {

        switch(position)
        {
            case 0:

                return PostlistFragment.newInstance();
            case 1:

                return SearchFragment.newInstance();
            default:
                return null;
        }




    }

    @Override
    public int getCount() {
        return VIEW_PAGE_SIZE;
    }


}

