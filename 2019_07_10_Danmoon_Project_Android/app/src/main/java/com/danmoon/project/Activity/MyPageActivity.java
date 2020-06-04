package com.danmoon.project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.danmoon.project.R;
import com.danmoon.project.Saver.LoggedInInfoChecker;
import com.kakao.auth.Session;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class MyPageActivity extends AppCompatActivity {

    TextView email_textView;
    TextView nickname_textView;
    TextView type_textView;
    TextView idx_textView;
    Button logout_button;
    Button writing_move_button;

    LoggedInInfoChecker loggedInInfoChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");
        final String nickname = intent.getStringExtra("nickname");
        final int idx = intent.getIntExtra("idx", 0 );
        final String type = intent.getStringExtra("type");

        email_textView = (TextView)findViewById(R.id.email_mypage);
        nickname_textView = (TextView)findViewById(R.id.nickname_mypage);
        type_textView = (TextView)findViewById(R.id.type_mypage);
        idx_textView = (TextView)findViewById(R.id.idx_mypage);
        logout_button = (Button)findViewById(R.id.logout_mypage);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loggedInInfoChecker = new LoggedInInfoChecker(getApplicationContext());
                loggedInInfoChecker.loggedInInfoTerminator();

                if(type.equals("kakao")) {
                    if (Session.getCurrentSession().isOpened()) {
                        requestLogout();
                    }
                } else if (type.equals(("local"))){

                    Toast.makeText(MyPageActivity.this, "로그아웃 성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class); // Intent 객체에 어디로 이동할 것인지 그 정보를 담는다
                    startActivityForResult(intent, 1000); // 두번째 매개변수 - 요청코드(아무코드나 쓰면 된다)
                }
            }
        });

        email_textView.setText(email);
        nickname_textView.setText(nickname);
        type_textView.setText(type);
        idx_textView.setText(idx + "");

        writing_move_button = (Button)findViewById(R.id.writing_move_button);
        writing_move_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MaterialActivity.class); // Intent 객체에 어디로 이동할 것인지 그 정보를 담는다
                startActivityForResult(intent, 1000); // 두번째 매개변수 - 요청코드(아무코드나 쓰면 된다)
            }
        });
    }

    private void requestLogout() {
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyPageActivity.this, "로그아웃 성공", Toast.LENGTH_SHORT).show();
                        System.out.println("세션없음");
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class); // Intent 객체에 어디로 이동할 것인지 그 정보를 담는다
                        startActivityForResult(intent, 1000); // 두번째 매개변수 - 요청코드(아무코드나 쓰면 된다)
                    }
                });
            }
        });
    }
}
