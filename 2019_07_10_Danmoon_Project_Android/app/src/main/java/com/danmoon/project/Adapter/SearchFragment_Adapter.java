package com.danmoon.project.Adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.danmoon.project.Fragment.Search_MemFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment_Adapter extends FragmentPagerAdapter {

    private final int VIEW_PAGE_SIZE = 1;
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public SearchFragment_Adapter(FragmentManager fm){
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


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {

        switch(position)
        {

            case 0:

                return Search_MemFragment.newInstance();

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return VIEW_PAGE_SIZE;
    }


}

