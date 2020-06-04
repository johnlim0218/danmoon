package com.danmoon.project.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.danmoon.project.Adapter.Material_MidLayoutFragment_Adapter;
import com.danmoon.project.Activity.MaterialActivity;
import com.danmoon.project.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class Material_MidLayoutFragment extends Fragment {

    final int POSTLIST = 0;
    final int SEARCH = 1;

    private ViewPager midLayoutViewPager;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private EditText appBarSearchWord;

    static boolean blockPageChangeListener = false;

    public static Material_MidLayoutFragment newInstance() {
        return new Material_MidLayoutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.material_mid_layout_fragment, container, false);
        Material_MidLayoutFragment_Adapter materialMidLayoutFragmentAdapter = new Material_MidLayoutFragment_Adapter(getChildFragmentManager());

        collapsingToolbarLayout = (CollapsingToolbarLayout)getActivity().findViewById(R.id.post_collapsingtoolbarlayout);
        appBarLayout = (AppBarLayout)getActivity().findViewById(R.id.post_appbarlayout);
        appBarSearchWord = (EditText)getActivity().findViewById(R.id.app_bar_search_word);

        midLayoutViewPager = (ViewPager)view.findViewById(R.id.post_mid_layout_viewpager);
        midLayoutViewPager.setAdapter(materialMidLayoutFragmentAdapter);
        midLayoutViewPager.setOffscreenPageLimit(2);
        midLayoutViewPager.setCurrentItem(0);


//        // 현재 페이지에 따른 화면 초기화
//        // 0 - 글 리스트 (POSTLIST)
//        // 1 - 검색 (SEARCH)
//        if(midLayoutViewPager.getCurrentItem() == 0){
//            MaterialActivity.MODE = MaterialActivity.POSTLIST_MODE;
//            collapsingToolbarLayout.setTitle(MaterialActivity.material_str);
//            appBarLayout.setExpanded(true);
//            appBarSearchWord.setVisibility(View.GONE);
//
//
//        } else if(midLayoutViewPager.getCurrentItem() == 1){
//            MaterialActivity.MODE = MaterialActivity.SEARCH_MODE;
//            collapsingToolbarLayout.setTitle("");
//            appBarLayout.setExpanded(false);
//            appBarSearchWord.setVisibility(View.VISIBLE);
//
//        }

        midLayoutViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                if (position == POSTLIST) {

                    MaterialActivity.MODE = MaterialActivity.POSTLIST_MODE;

                    collapsingToolbarLayout.setTitle(MaterialActivity.material_str);
                    appBarLayout.setExpanded(true);
                    appBarSearchWord.setVisibility(View.GONE);

                } else if (position == SEARCH) {

                    MaterialActivity.MODE = MaterialActivity.SEARCH_MODE;

                    collapsingToolbarLayout.setTitle("");
                    appBarLayout.setExpanded(false);
                    appBarSearchWord.setVisibility(View.VISIBLE);

                }
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

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("onResume");

        if(MaterialActivity.MODE == MaterialActivity.POSTLIST_MODE) {
            midLayoutViewPager.setCurrentItem(POSTLIST);
        } else if(MaterialActivity.MODE == MaterialActivity.SEARCH_MODE){
            midLayoutViewPager.setCurrentItem(SEARCH);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("onDestroyView");
        midLayoutViewPager.setCurrentItem(0);
        appBarSearchWord.setText("");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
        midLayoutViewPager.setCurrentItem(0);
        appBarSearchWord.setText("");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("onPause");
        midLayoutViewPager.setCurrentItem(0);
        appBarSearchWord.setText("");
    }
}
