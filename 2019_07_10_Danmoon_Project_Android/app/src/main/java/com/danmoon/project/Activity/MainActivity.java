package com.danmoon.project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.danmoon.project.DTO.MemberDto;
import com.danmoon.project.Fragment.FirstMenuFragment;
import com.danmoon.project.R;
import com.danmoon.project.Saver.LoggedInInfoChecker;
import com.danmoon.project.ServerCommunicator.ServerCommunicationForMember;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    static public String serverUrl = "http://192.168.11.25:9090";

    Button veryFirstButton;
    FrameLayout fragmentContainer;

    static final String loginMemURL = "/danmoon/login";

    MemberDto memberDto;
    String nullValue = "정보없음";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoggedInInfoChecker loggedInInfoChecker = new LoggedInInfoChecker(this.getApplicationContext());
        memberDto = loggedInInfoChecker.loggedInInfoReader();

        System.out.println("email :: " + memberDto.getEmail());
        System.out.println("password :: " + memberDto.getPassword());
        System.out.println("nickname :: " + memberDto.getNickname());
        System.out.println("type :: " + memberDto.getType());
        System.out.println("idx :: " + memberDto.getIdx());


        // 자동 로그인
        if(!(memberDto.getEmail().equals(nullValue)) && !(memberDto.getPassword().equals(nullValue))){

            System.out.println("자동로그인 시작");

            Map<String, Object> postData = new HashMap<>();
            postData.put("email", memberDto.getEmail());
            postData.put("password", memberDto.getPassword());
            postData.put("type", memberDto.getType());

            ServerCommunicationForMember serverCommunication = new ServerCommunicationForMember(postData);
                String response = "";
                MemberDto memberDto = new MemberDto();
                try {
                memberDto = serverCommunication.execute(loginMemURL).get();

            } catch(Exception e){
                e.printStackTrace();
            }

            // MyPageActivity로 이동
            Intent intent = new Intent(getApplicationContext(), MyPageActivity.class); // Intent 객체에 어디로 이동할 것인지 그 정보를 담는다
            intent.putExtra("email", memberDto.getEmail());
            intent.putExtra("nickname", memberDto.getNickname());
            intent.putExtra("type", memberDto.getType());
            intent.putExtra("idx", memberDto.getIdx());
            startActivityForResult(intent, 1000); // 두번째 매개변수 - 요청코드(아무코드나 쓰면 된다)

            System.out.println("자동로그인 끝");
        }

        veryFirstButton = (Button)findViewById(R.id.VeryFirst_Button);
//        fragmentContainer = (FrameLayout)findViewById(R.id.frame_container);

        final FirstMenuFragment firstMenuFragment = new FirstMenuFragment();

        veryFirstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                veryFirstButton.setVisibility(View.GONE);
//                fragmentContainer.setVisibility(View.VISIBLE);
                replaceFragment(firstMenuFragment.newInstance());

            }
        });
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTrasaction = fragmentManager.beginTransaction();
        fragmentTrasaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        fragmentTrasaction.addToBackStack(null);
        fragmentTrasaction.replace(R.id.frame_container, fragment);
        fragmentTrasaction.commit();

    }
}
