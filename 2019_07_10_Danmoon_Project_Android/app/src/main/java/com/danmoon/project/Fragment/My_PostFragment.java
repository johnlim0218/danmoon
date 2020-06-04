package com.danmoon.project.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.danmoon.project.DTO.PostDto;
import com.danmoon.project.Activity.MaterialActivity;
import com.danmoon.project.R;
import com.danmoon.project.ServerCommunicator.ServerCommunicationForMyPost;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.HashMap;
import java.util.Map;

public class My_PostFragment extends Fragment {

    static final String getMyPost = "/danmoon/getUserPost/";
    static final String updateMyPost = "/danmoon/updateMyPost/";
    static final String deleteMyPost = "/danmoon/deleteMyPost/";

    private EditText myPostContentText;
    private PostDto postDto = new PostDto();

    private CollapsingToolbarLayout collapsingToolbar;
    private ImageButton deletePostButton;

    private int p_idx;
    private String strP_idx;

    private FragmentManager fragmentManager;

    public static My_PostFragment newInstance() {
        return new My_PostFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        MaterialActivity.MODE = MaterialActivity.READ_MY_POST_MODE;

        View view = inflater.inflate(R.layout.my_post_fragment, container, false);

        collapsingToolbar = (CollapsingToolbarLayout) getActivity().findViewById(R.id.post_collapsingtoolbarlayout);
        deletePostButton = (ImageButton) getActivity().findViewById(R.id.delete_post_button);
        myPostContentText = view.findViewById(R.id.mypost_content_text);

        // 삭제 버튼 보이게
        deletePostButton.setVisibility(View.VISIBLE);

        Bundle bundle = getArguments();
        p_idx = bundle.getInt("p_idx");
        strP_idx = p_idx + "";

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
        myPostContentText.setText(postDto.getP_content());
        makeUnenabledContentEditText();

//        loadTemporarySave();


        deleteButtonEvent();

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


//    public void loadTemporarySave(){
//
//        TemporarySave temporarySave = new TemporarySave(this.getActivity().getApplicationContext());
////        String content = temporarySave.temporarySaveLoader();
//
//        if(!content.equals("")){
//            Toast.makeText(this.getActivity().getApplicationContext(), "임시저장된 글입니다.", Toast.LENGTH_LONG).show();
//            myPostContentText.setText(content);
//        }
//
//    }

    public void updateMyPost(){

        String updateMyPostContent = myPostContentText.getText().toString();

        Map<String, Object> postData = new HashMap<>();
        postData.put("p_idx", postDto.getP_idx_pk());
        postData.put("content", updateMyPostContent);

        String updatePublic = "n";

        if (MaterialActivity.PUBLIC == MaterialActivity.POST_PUBLIC) {
            updatePublic = "y";
        } else if(MaterialActivity.PUBLIC == MaterialActivity.POST_NOT_PUBLIC){
            updatePublic = "n";
        }
        postData.put("public", updatePublic);

        if(!updateMyPostContent.equals(postDto.getP_content()) || !updatePublic.equals(postDto.getP_public())) {
            ServerCommunicationForMyPost serverCommunicationForMyPost = new ServerCommunicationForMyPost(postData);
            serverCommunicationForMyPost.execute(updateMyPost);
        }

    }

    public void deleteButtonEvent(){
        deletePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
                alert_confirm.setMessage("글을 삭제하시겠습니까?");
                alert_confirm.setPositiveButton("네",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 'YES'

                                    ServerCommunicationForMyPost serverCommunicationForMyPost = new ServerCommunicationForMyPost();
                                    // 글 번호 (p_idx)를 주소에 포함시켜 서버에 보낸다.
                                    serverCommunicationForMyPost.execute(deleteMyPost + strP_idx);
                                    toMyPostList();

                            }
                });

                alert_confirm.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 'No'
                                return;
                            }
                        });
                AlertDialog alert = alert_confirm.create();
                alert.show();

            }
        });
    }

    public void toMyPostList(){
        deletePostButton.setVisibility(View.GONE);
        fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
        My_PostlistFragment myPostlistFragment = My_PostlistFragment.newInstance();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.post_mid_layout, myPostlistFragment, "myPostListFragment");
        fragmentTransaction.commit();
    }
}
