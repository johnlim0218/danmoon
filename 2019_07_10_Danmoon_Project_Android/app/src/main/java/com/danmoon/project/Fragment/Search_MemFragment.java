package com.danmoon.project.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.danmoon.project.Adapter.Search_MemFragment_Adapter;
import com.danmoon.project.DTO.MemberDto;
import com.danmoon.project.R;
import com.danmoon.project.ServerCommunicator.ServerCommunicationForSearchMem;

import java.util.ArrayList;

public class Search_MemFragment extends Fragment {

    static final String getSearchMemURL = "/danmoon/getSearchResult/mem/";

    private EditText appBarSearchWord;
    private Search_MemFragment_Adapter materialSearchMemFragmentAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView searchMemRecyclerView;

    private ArrayList<MemberDto> arrForSearchMem;

    public static Search_MemFragment newInstance() {
        return new Search_MemFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

//        MaterialActivity.MODE = MaterialActivity.SEARCH_MODE;

        View view = inflater.inflate(R.layout.material_search_mem_fragment, container, false);

        searchMemRecyclerView = (RecyclerView) view.findViewById(R.id.search_mem_fragment_recyclerview);

        arrForSearchMem = new ArrayList<>();
        appBarSearchWord = (EditText)getActivity().findViewById(R.id.app_bar_search_word);
        appBarSearchWord.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //Enter키눌렀을떄 처리
                    System.out.println(getSearchMemURL + appBarSearchWord.getText().toString());

                    try {
                        ServerCommunicationForSearchMem serverCommunicationForSearchMem = new ServerCommunicationForSearchMem();
                        // 검색어를 주소에 포함시켜 서버에 보낸다.
                        arrForSearchMem = serverCommunicationForSearchMem.execute(getSearchMemURL + appBarSearchWord.getText().toString()).get();
                    } catch(Exception e){
                        e.printStackTrace();
                    }

                    materialSearchMemFragmentAdapter = new Search_MemFragment_Adapter(getContext(), arrForSearchMem);

                    linearLayoutManager = new LinearLayoutManager(getActivity());

                    searchMemRecyclerView.setLayoutManager(linearLayoutManager);
                    searchMemRecyclerView.setAdapter(materialSearchMemFragmentAdapter);

                    return true;
                }
                return false;
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
