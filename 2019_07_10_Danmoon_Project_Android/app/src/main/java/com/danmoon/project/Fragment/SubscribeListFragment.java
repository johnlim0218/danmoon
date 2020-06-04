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

import com.danmoon.project.Adapter.SubscribeListFragment_Adapter;
import com.danmoon.project.DTO.SubscribeDto;
import com.danmoon.project.Activity.MaterialActivity;
import com.danmoon.project.R;
import com.danmoon.project.ServerCommunicator.ServerCommunicationForSubscribe;

import java.util.ArrayList;

public class SubscribeListFragment extends Fragment {

    static final String getSubscribeList = "/danmoon/getSubscribeList/";

    private ArrayList<SubscribeDto> arrForSubscribeList = new ArrayList<>();

    private RecyclerView subscribeListRecyclerView;
    private SubscribeListFragment_Adapter subscribeListFragmentAdapter;
    private LinearLayoutManager linearLayoutManager;

    public static SubscribeListFragment newInstance() {
        return new SubscribeListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        MaterialActivity.MODE = MaterialActivity.SUBSCRIBING_MODE;

        View view = inflater.inflate(R.layout.subscribe_list_fragment, container, false);

        try {
            ServerCommunicationForSubscribe serverCommunicationForSubscribe = new ServerCommunicationForSubscribe();
            // 글감 번호 (material_idx)를 주소에 포함시켜 서버에 보낸다.
            arrForSubscribeList = serverCommunicationForSubscribe.execute(getSubscribeList + MaterialActivity.mem_idx).get();
        } catch(Exception e){
            e.printStackTrace();
        }

        subscribeListFragmentAdapter = new SubscribeListFragment_Adapter(getContext(), arrForSubscribeList);
        subscribeListRecyclerView = (RecyclerView)view.findViewById(R.id.subscribe_list_recyclerview);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        subscribeListRecyclerView.setLayoutManager(linearLayoutManager);
        subscribeListRecyclerView.setAdapter(subscribeListFragmentAdapter);

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

}
