package com.danmoon.project.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.danmoon.project.DTO.MemberDto;
import com.danmoon.project.Activity.MyPageActivity;
import com.danmoon.project.R;
import com.danmoon.project.Saver.LoggedInInfoChecker;
import com.danmoon.project.ServerCommunicator.ServerCommunicationForMember;

import java.util.HashMap;
import java.util.Map;

public class KakaoJoinInFragment extends Fragment {

    static final String joinMemURL = "/danmoon/join";

    public static KakaoJoinInFragment newInstance() {
        return new KakaoJoinInFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        System.out.println("KakaoJoinActivity 시작");
        Bundle bundle = getArguments();

        final String email = bundle.getString("email"); // getString(String key)
        final String password = bundle.getString("password"); // getString(String key, String defaultValue)
        final String type = "kakao";

        View view = inflater.inflate(R.layout.kakao_join_in_fragment, container, false);

        final EditText kakao_nickname_join = (EditText)view.findViewById(R.id.kakao_nickname_join);

        final Button kakao_join_button = (Button)view.findViewById(R.id.kakao_join_button);

        kakao_join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MemberDto kakaoJoinMemDto = new MemberDto();
                kakaoJoinMemDto.setEmail(email);
                kakaoJoinMemDto.setNickname(kakao_nickname_join.getText().toString());
                kakaoJoinMemDto.setPassword(password);
                kakaoJoinMemDto.setType(type);

                Map<String, Object> postData = new HashMap<>();
                postData.put("email", kakaoJoinMemDto.getEmail());
                postData.put("password", password);
                postData.put("nickname", kakaoJoinMemDto.getNickname());
                postData.put("type", kakaoJoinMemDto.getType());


                MemberDto memberDto = new MemberDto();
                ServerCommunicationForMember serverCommunication = new ServerCommunicationForMember(postData);
                try {

                    memberDto = serverCommunication.execute(joinMemURL).get();
                    memberDto.setPassword(password);
                    LoggedInInfoChecker loggedInInfoChecker = new LoggedInInfoChecker(getActivity().getApplicationContext());
                    loggedInInfoChecker.logedInInfoWriter(memberDto);
                    System.out.println("KakaoJoinActivity 끝");

                    // MyPageActivity로 이동
                    Intent intent = new Intent(getActivity().getApplicationContext(), MyPageActivity.class); // Intent 객체에 어디로 이동할 것인지 그 정보를 담는다
                    intent.putExtra("email", memberDto.getEmail());
                    intent.putExtra("nickname", memberDto.getNickname());
                    intent.putExtra("type", memberDto.getType());
                    intent.putExtra("idx", memberDto.getIdx());
                    startActivityForResult(intent, 1000); // 두번째 매개변수 - 요청코드(아무코드나 쓰면 된다)

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        // TODO: Use the ViewModel
    }

}
