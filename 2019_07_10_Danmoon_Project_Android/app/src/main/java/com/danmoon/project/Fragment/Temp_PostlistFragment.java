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

import com.danmoon.project.Adapter.Temp_PostlistFragment_Adapter;
import com.danmoon.project.DTO.PostDto;
import com.danmoon.project.Activity.MaterialActivity;
import com.danmoon.project.R;
import com.danmoon.project.Saver.TemporarySave;

import java.util.ArrayList;

public class Temp_PostlistFragment extends Fragment {
    PostDto postDto = new PostDto();

    Temp_PostlistFragment_Adapter postlistAdapter;
    RecyclerView postlistRecylerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<PostDto> arrForPostDto;

    TemporarySave temporarySave;

    public static Temp_PostlistFragment newInstance() {
        return new Temp_PostlistFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        MaterialActivity.MODE = MaterialActivity.TEMP_POSTLIST_MODE;

        temporarySave = new TemporarySave(getContext());


//        Bundle bundle = getArguments();

//        Map<String, Object> postData = new HashMap<>();
//        postData.put("material_idx", materialIdx);
//        String strMemberIdx = bundle.getString("mem_idx");

        arrForPostDto = new ArrayList<>();

        arrForPostDto = temporarySave.selectTempSaveData();

        View view = inflater.inflate(R.layout.temp_postlist_fragment, container, false);

        postlistAdapter = new Temp_PostlistFragment_Adapter(getContext(), arrForPostDto);
        postlistRecylerView = (RecyclerView)view.findViewById(R.id.temppostlist_fragment_recyclerview);
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
