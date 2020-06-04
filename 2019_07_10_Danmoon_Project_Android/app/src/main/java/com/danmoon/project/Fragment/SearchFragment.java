package com.danmoon.project.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.danmoon.project.Adapter.SearchFragment_Adapter;
import com.danmoon.project.R;


public class SearchFragment extends Fragment {

    ViewPager searchMidViewpager;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.material_search_fragment, container, false);
        SearchFragment_Adapter materialSearchFragmentAdapter = new SearchFragment_Adapter(getChildFragmentManager());

        searchMidViewpager = (ViewPager) view.findViewById(R.id.search_mid_viewpager);
        searchMidViewpager.setAdapter(materialSearchFragmentAdapter);
        searchMidViewpager.setOffscreenPageLimit(2);
        searchMidViewpager.setCurrentItem(0);
        searchMidViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

             @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

}
