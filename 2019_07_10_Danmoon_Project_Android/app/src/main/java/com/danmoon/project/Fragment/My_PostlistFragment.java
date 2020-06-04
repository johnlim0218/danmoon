package com.danmoon.project.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.danmoon.project.Adapter.My_PostlistFragment_Adapter;
import com.danmoon.project.DTO.PostDto;
import com.danmoon.project.Activity.MaterialActivity;
import com.danmoon.project.R;
import com.danmoon.project.ServerCommunicator.ServerCommunicationForPostlist;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

public class My_PostlistFragment extends Fragment {

    static final String getMyPostlistURL = "/danmoon/getMyPostList/";

    private PostDto postDto = new PostDto();

    private My_PostlistFragment_Adapter postlistAdapter;
    private RecyclerView postlistRecylerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<PostDto> arrForPostDto;

    private CollapsingToolbarLayout collapsingToolbarLayout;

    public static My_PostlistFragment newInstance() {
        return new My_PostlistFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        MaterialActivity.MODE = MaterialActivity.MY_POSTLIST_MODE;

        collapsingToolbarLayout = (CollapsingToolbarLayout)getActivity().findViewById(R.id.post_collapsingtoolbarlayout);
        collapsingToolbarLayout.setTitle("나의 공간");

        arrForPostDto = new ArrayList<>();

        try {
            String URL = getMyPostlistURL + MaterialActivity.mem_idx ;
            ServerCommunicationForPostlist serverCommunicationForPostlist = new ServerCommunicationForPostlist();
            // 회원 번호(mem_idx)를 URL에 실어 보낸다.
            arrForPostDto = serverCommunicationForPostlist.execute(URL).get();
        } catch(Exception e){
            e.printStackTrace();
        }

        View view = inflater.inflate(R.layout.my_postlist_fragment, container, false);

        postlistAdapter = new My_PostlistFragment_Adapter(getContext(), arrForPostDto);
        postlistRecylerView = (RecyclerView)view.findViewById(R.id.mypostlist_fragment_recyclerview);

        linearLayoutManager = new LinearLayoutManager(getContext());
        postlistRecylerView.setLayoutManager(linearLayoutManager);
        postlistRecylerView.setAdapter(postlistAdapter);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }


}
