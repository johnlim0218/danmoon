package com.danmoon.project.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.danmoon.project.DTO.MemberDto;
import com.danmoon.project.Activity.MyPageActivity;
import com.danmoon.project.R;
import com.danmoon.project.Saver.LoggedInInfoChecker;
import com.danmoon.project.ServerCommunicator.ServerCommunicationForMember;

import java.util.HashMap;
import java.util.Map;

public class LocalJoinInFragment extends Fragment {

    static final String joinMemURL = "/danmoon/join";
    private  String type = "local";

    public static LocalJoinInFragment newInstance(){
        return new LocalJoinInFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.localjoinin_fragment, container, false);

        final EditText emailEditText = (EditText)view.findViewById(R.id.email_join);
        final EditText pwdEditText = (EditText)view.findViewById(R.id.pwd_join);
        final EditText nickEditText = (EditText)view.findViewById(R.id.nickname_join);
        final TextView dupIdTextView = (TextView)view.findViewById(R.id.dupId_textview);

        final String password = pwdEditText.getText().toString();

        final Button joinButton = (Button)view.findViewById(R.id.join_button);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MemberDto joinMemDto = new MemberDto();
                joinMemDto.setEmail(emailEditText.getText().toString());
                joinMemDto.setPassword(pwdEditText.getText().toString());
                joinMemDto.setNickname(nickEditText.getText().toString());

                Map<String, Object> postData = new HashMap<>();
                postData.put("email", joinMemDto.getEmail());
                postData.put("password", joinMemDto.getPassword());
                postData.put("nickname", joinMemDto.getNickname());
                postData.put("type", type);

                System.out.println("setOnClickListener");
                MemberDto memberDto = new MemberDto();
                ServerCommunicationForMember serverCommunication = new ServerCommunicationForMember(postData);
                try {
                    memberDto = serverCommunication.execute(joinMemURL).get();

                    // 사용자가 회원가입 버튼을 누르면 서버에 접속해 이메일 중복확인 후 회원가입 절차를 완료한다.
                    // 만약 이메일이 중복 됐다면 서버에서 아무것도 들어있지 않은 HashMap을 반환한다.
                    if(memberDto.getEmail() == null){
                        System.out.println("아이디 중복됨");
                        dupIdTextView.setVisibility(View.VISIBLE);
                    } else {

                        // 회원가입이 완료되면 사용자 정보를 서버에서 가져와 logedininfo.xml에 저장한다.
                        LoggedInInfoChecker loggedInInfoChecker = new LoggedInInfoChecker(getActivity().getApplicationContext());
                        memberDto.setPassword(password);
                        loggedInInfoChecker.logedInInfoWriter(memberDto);
                        System.out.println("JoinActivity 끝");

                        // MyPageActivity로 이동
                        Intent intent = new Intent(getActivity().getApplicationContext(), MyPageActivity.class); // Intent 객체에 어디로 이동할 것인지 그 정보를 담는다
                        intent.putExtra("email", memberDto.getEmail());
                        intent.putExtra("nickname", memberDto.getNickname());
                        intent.putExtra("type", memberDto.getType());
                        intent.putExtra("idx", memberDto.getIdx());
                        startActivityForResult(intent, 1000); // 두번째 매개변수 - 요청코드(아무코드나 쓰면 된다)
                    }

                } catch(Exception e){
                    e.printStackTrace();
                }

            }
        });

        return view;
    }
}
