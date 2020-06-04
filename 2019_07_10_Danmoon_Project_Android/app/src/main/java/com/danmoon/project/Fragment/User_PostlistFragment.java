package com.danmoon.project.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.danmoon.project.Adapter.User_PostlistFragment_Adapter;
import com.danmoon.project.DTO.PostDto;
import com.danmoon.project.Activity.MaterialActivity;
import com.danmoon.project.R;
import com.danmoon.project.ServerCommunicator.ServerCommunicationForPostlist;
import com.danmoon.project.ServerCommunicator.ServerCommunicationForSubscribe;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

public class User_PostlistFragment extends Fragment {

    static final String getUserPostlistURL = "/danmoon/getUserPostlist/";
    static final String insertSubscribeURL = "/danmoon/insertSubscribe/";
    static final String delectSubscribeURL = "/danmoon/deleteSubscribe/";
    private int userIdx;
    private String strMemberNickname;

    private PostDto postDto = new PostDto();

    private User_PostlistFragment_Adapter materialSearchMemInfoFragmentAdapter;
    private RecyclerView postlistRecylerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<PostDto> arrForPostDto;

    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private CheckBox userSubscribeCheckBox;

    public static User_PostlistFragment newInstance() {
        return new User_PostlistFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        MaterialActivity.MODE = MaterialActivity.SUBSCRIBING_MODE;

        View view = inflater.inflate(R.layout.material_search_mem_info_fragment, container, false);
        Bundle bundle = getArguments();
        userIdx = bundle.getInt("mem_idx");
        strMemberNickname = bundle.getString("mem_nickname");

        // 회원상세정보 fragment의 상단툴바 초기화
        initToolbar();

        arrForPostDto = new ArrayList<>();

        try {
            String URL = getUserPostlistURL + MaterialActivity.mem_idx + "&" + userIdx;

            ServerCommunicationForPostlist serverCommunicationForPostlist = new ServerCommunicationForPostlist();

            arrForPostDto = serverCommunicationForPostlist.execute(URL).get();

        } catch(Exception e){
            e.printStackTrace();
        }

        if(arrForPostDto.size() == 0){
            System.out.println("구독자도 아니고 글도 없고");
            unsubscribe();
        } else if (arrForPostDto.size() > 0){
            if(arrForPostDto.get(0).getS_memidx_fk() == MaterialActivity.mem_idx){
                System.out.println("구독자");
                subscribe();
            } else if (arrForPostDto.get(0).getS_memidx_fk() != MaterialActivity.mem_idx){
                System.out.println("구독자 아님");
                unsubscribe();
            }
        }


        materialSearchMemInfoFragmentAdapter = new User_PostlistFragment_Adapter(getContext(), arrForPostDto);

        postlistRecylerView = (RecyclerView)view.findViewById(R.id.userpostlist_fragment_recyclerview);

        linearLayoutManager = new LinearLayoutManager(getContext());
        postlistRecylerView.setLayoutManager(linearLayoutManager);
        postlistRecylerView.setAdapter(materialSearchMemInfoFragmentAdapter);

        userSubscribeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 구독취소 -> 구독하기
                // 현재 구독 중
                if(userSubscribeCheckBox.isChecked()){
                    subscribe();

                    String subscribeUrl = insertSubscribeURL + MaterialActivity.mem_idx + '&' + userIdx;
                    ServerCommunicationForSubscribe serverCommunicationForSubscribe = new ServerCommunicationForSubscribe();
                    serverCommunicationForSubscribe.execute(subscribeUrl);

                    // 구독하기 -> 구독취소
                    // 현재 구독하고 있지 않음
                } else {
                    unsubscribe();

                    String subscribeUrl = delectSubscribeURL + MaterialActivity.mem_idx + '&' + userIdx;
                    ServerCommunicationForSubscribe serverCommunicationForSubscribe = new ServerCommunicationForSubscribe();
                    serverCommunicationForSubscribe.execute(subscribeUrl);
                }
            }
        });

        return view;
    }

    // 회원상세정보 fragment의 상단툴바 초기화
    public void initToolbar(){
        collapsingToolbar =(CollapsingToolbarLayout)getActivity().findViewById(R.id.post_collapsingtoolbarlayout);
        appBarLayout = (AppBarLayout)getActivity().findViewById(R.id.post_appbarlayout);
        userSubscribeCheckBox = (CheckBox)getActivity().findViewById(R.id.user_subscribe_checkbox);

        collapsingToolbar.setTitle(strMemberNickname + "님의 공간");
        appBarLayout.setExpanded(true);
        userSubscribeCheckBox.setVisibility(View.VISIBLE);
    }

    public void subscribe(){
        userSubscribeCheckBox.setChecked(true);
        userSubscribeCheckBox.setText("구독취소");
    }

    public void unsubscribe(){
        userSubscribeCheckBox.setChecked(false);
        userSubscribeCheckBox.setText("구독하기");

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

}
