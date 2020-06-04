package com.danmoon.project.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.danmoon.project.Adapter.PostlistFragment_Adapter;
import com.danmoon.project.DTO.PostDto;
import com.danmoon.project.Activity.MaterialActivity;
import com.danmoon.project.R;
import com.danmoon.project.ServerCommunicator.ServerCommunicationForPostlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// 현재 글감에 대해 사람들이 쓴 글 리스트
public class PostlistFragment extends Fragment {

    static final String getPostlistURL = "/danmoon/getPostlist/";

    PostDto postDto = new PostDto();

    PostlistFragment_Adapter postlistAdapter;
    RecyclerView postlistRecylerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<PostDto> arrForPostDto;

    public static PostlistFragment newInstance() {
        return new PostlistFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


//        MaterialActivity.MODE = MaterialActivity.POSTLIST_MODE;
        // 현재 글감의 Idx

        int materialIdx = MaterialActivity.material_idx;

        Map<String, Object> postData = new HashMap<>();
        postData.put("material_idx", materialIdx);
        String strMaterialIdx = materialIdx + "";

        arrForPostDto = new ArrayList<>();

        try {
            ServerCommunicationForPostlist serverCommunicationForPostlist = new ServerCommunicationForPostlist();
            // 글감 번호 (material_idx)를 주소에 포함시켜 서버에 보낸다.
            arrForPostDto = serverCommunicationForPostlist.execute(getPostlistURL + strMaterialIdx).get();
        } catch(Exception e){
            e.printStackTrace();
        }

        View view = inflater.inflate(R.layout.material_postlist_fragment, container, false);

        postlistAdapter = new PostlistFragment_Adapter(arrForPostDto);
        postlistRecylerView = (RecyclerView)view.findViewById(R.id.postlist_fragment_recyclerview);
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
