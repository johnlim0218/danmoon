package com.danmoon.project.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.danmoon.project.DTO.PostDto;
import com.danmoon.project.Activity.MaterialActivity;
import com.danmoon.project.R;
import com.danmoon.project.ServerCommunicator.ServerCommunicationForMyPost;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class User_PostFragment extends Fragment {

    static final String getMyPost = "/danmoon/getUserPost/";

    private EditText userPostContentText;
    private PostDto postDto = new PostDto();

    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;

    public static User_PostFragment newInstance() {
        return new User_PostFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        MaterialActivity.MODE = MaterialActivity.SEARCH_MODE;

        View view = inflater.inflate(R.layout.material_search_mem_post_fragment, container, false);

        collapsingToolbar = (CollapsingToolbarLayout) getActivity().findViewById(R.id.post_collapsingtoolbarlayout);
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.post_appbarlayout);
        userPostContentText = view.findViewById(R.id.userpost_content_text);

        appBarLayout.setExpanded(false);


        Bundle bundle = getArguments();
        int p_idx = bundle.getInt("p_idx");
        String strP_idx = p_idx + "";

        try {
            ServerCommunicationForMyPost serverCommunicationForMyPost = new ServerCommunicationForMyPost();
            // 글감 번호 (material_idx)를 주소에 포함시켜 서버에 보낸다.
            postDto = serverCommunicationForMyPost.execute(getMyPost + strP_idx).get();
        } catch(Exception e){
            e.printStackTrace();
        }

        if (postDto.getP_public().equals("y")) {
            MaterialActivity.PUBLIC = MaterialActivity.POST_PUBLIC;

        } else if(postDto.getP_public().equals("n")){
            MaterialActivity.PUBLIC = MaterialActivity.POST_NOT_PUBLIC;
        }

//        MaterialActivity.fileName = "material_" + postDto.getP_material_idx_fk();
        MaterialActivity.post_idx = postDto.getP_idx_pk();
        MaterialActivity.material_idx = postDto.getP_material_idx_fk();

        collapsingToolbar.setTitle(postDto.getP_material());
        userPostContentText.setText(postDto.getP_content());
        makeUnenabledContentEditText();

//        loadTemporarySave();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }
    public void makeUnenabledContentEditText(){
        userPostContentText.setEnabled(false);
    }
}
