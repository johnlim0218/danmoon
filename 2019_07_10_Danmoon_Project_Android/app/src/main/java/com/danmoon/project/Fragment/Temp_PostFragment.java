package com.danmoon.project.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.danmoon.project.DTO.MemberDto;
import com.danmoon.project.DTO.PostDto;
import com.danmoon.project.Activity.MaterialActivity;
import com.danmoon.project.R;
import com.danmoon.project.Saver.TemporarySave;
import com.danmoon.project.ServerCommunicator.ServerCommunicationForMyPost;
import com.danmoon.project.ServerCommunicator.ServerCommunicationForPost;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.HashMap;
import java.util.Map;

public class Temp_PostFragment extends Fragment {

    static final String insertPostURL = "/danmoon/insertPost";
    static final String updateMyPost = "/danmoon/updateMyPost";

    private TemporarySave temporarySave;

    private int p_idx;

    private EditText myPostContentText;
    private PostDto postDto = new PostDto();

    private CollapsingToolbarLayout collapsingToolbar;

    public static Temp_PostFragment newInstance() {
        return new Temp_PostFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        MaterialActivity.MODE = MaterialActivity.TEMP_READ_MODE;

        View view = inflater.inflate(R.layout.temp_post_fragment, container, false);

        temporarySave = new TemporarySave(getContext());

        Bundle bundle = getArguments();
        p_idx = bundle.getInt("tempsave_idx");
        String strP_idx = p_idx + "";

        collapsingToolbar = (CollapsingToolbarLayout) getActivity().findViewById(R.id.post_collapsingtoolbarlayout);
        myPostContentText = view.findViewById(R.id.temppost_content_text);

        postDto = temporarySave.selectATempSaveData(p_idx);

        MaterialActivity.post_idx = postDto.getP_idx_pk();
        MaterialActivity.material_idx = postDto.getP_material_idx_fk();

        collapsingToolbar.setTitle(postDto.getP_material());
        myPostContentText.setText(postDto.getP_content());

        makeUnenabledContentEditText();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    public void makeEnabledContentEditText(){
        myPostContentText.setEnabled(true);
    }

    public void makeUnenabledContentEditText(){
        myPostContentText.setEnabled(false);
    }

    public void updateTempPost(MemberDto memberDto){

        String tempPostContent = myPostContentText.getText().toString();
        Map<String, Object> postData = new HashMap<>();
        postData.put("content", tempPostContent);

        String updatePublic = "n";
        if (MaterialActivity.PUBLIC == MaterialActivity.POST_PUBLIC) {
            updatePublic = "y";
        } else if(MaterialActivity.PUBLIC == MaterialActivity.POST_NOT_PUBLIC){
            updatePublic = "n";
        }
        postData.put("public", updatePublic);

        // 새글 (insert)
        // 새글인 경우 DB에서 p_idx_pk 를 받아오지 못했기 때문에 임의값(0)을 줬다.
        if(postDto.getOnDB().equals("n")){
            postData.put("mem_idx", memberDto.getIdx());
            postData.put("material_idx", postDto.getP_material_idx_fk());
            postData.put("material", postDto.getP_material());

            if (!tempPostContent.equals("")) {
                ServerCommunicationForPost serverCommunicationForPost = new ServerCommunicationForPost(postData);
                serverCommunicationForPost.execute(insertPostURL);
                temporarySave.updateTempSaveDataAfterInsertDB(p_idx);
            }

            // 기존 글 (update)
        } else if (postDto.getOnDB().equals("y")){
            postData.put("p_idx", postDto.getP_idx_pk());
            postData.put("content", tempPostContent);

            if(!tempPostContent.equals(postDto.getP_content()) || !updatePublic.equals(postDto.getP_public())) {
                ServerCommunicationForMyPost serverCommunicationForMyPost = new ServerCommunicationForMyPost(postData);
                serverCommunicationForMyPost.execute(updateMyPost);
            }
        }




    }
}
