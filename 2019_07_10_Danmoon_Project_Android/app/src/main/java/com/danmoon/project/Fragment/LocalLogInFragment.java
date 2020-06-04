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

public class LocalLogInFragment extends Fragment {

    static final String loginMemURL = "/danmoon/login";

    public static LocalLogInFragment newInstance() {
        return new LocalLogInFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.local_log_in_fragment, container, false);
        final EditText emailEditText = (EditText)view.findViewById(R.id.email_login);
        final EditText pwdEditText = (EditText)view.findViewById(R.id.pwd_login);

        final String password = pwdEditText.getText().toString();

        final Button loginButton = (Button)view.findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MemberDto loginMemDto = new MemberDto();
                loginMemDto.setEmail(emailEditText.getText().toString());
                loginMemDto.setPassword(pwdEditText.getText().toString());

                Map<String, Object> postData = new HashMap<>();
                postData.put("email", loginMemDto.getEmail());
                postData.put("password", loginMemDto.getPassword());
                postData.put("type", "local");

                ServerCommunicationForMember serverCommunication = new ServerCommunicationForMember(postData);
                String response = "";
                MemberDto memberDto = new MemberDto();
                try {
                    memberDto = serverCommunication.execute(loginMemURL).get();
                    System.out.println("LoginActivity");

                } catch(Exception e){
                    e.printStackTrace();
                }

                memberDto.setPassword(password);
                LoggedInInfoChecker loggedInInfoChecker = new LoggedInInfoChecker(getActivity().getApplicationContext());
                loggedInInfoChecker.logedInInfoWriter(memberDto);

                System.out.println("LoginActivity 끝");

                // MyPageActivity로 이동
                Intent intent = new Intent(getActivity().getApplicationContext(), MyPageActivity.class); // Intent 객체에 어디로 이동할 것인지 그 정보를 담는다
                intent.putExtra("email", memberDto.getEmail());
                intent.putExtra("nickname", memberDto.getNickname());
                intent.putExtra("type", memberDto.getType());
                intent.putExtra("idx", memberDto.getIdx());
                startActivityForResult(intent, 1000); // 두번째 매개변수 - 요청코드(아무코드나 쓰면 된다)

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
