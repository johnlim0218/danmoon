package com.danmoon.project.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.danmoon.project.DTO.MemberDto;
import com.danmoon.project.Activity.MainActivity;
import com.danmoon.project.Activity.MyPageActivity;
import com.danmoon.project.R;
import com.danmoon.project.Saver.LoggedInInfoChecker;
import com.danmoon.project.ServerCommunicator.ServerCommunicationForMember;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import java.util.HashMap;
import java.util.Map;

public class KakaoLogInFragment extends Fragment {

    static final String joinKakaoURL = "/danmoon/kakaologin";
    private  String type = "kakao";

    private SessionCallback callback;

    public static KakaoLogInFragment newInstance() {
        return new KakaoLogInFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final Session session = Session.getCurrentSession();
        session.addCallback(new SessionCallback());

        // 카카오 로그인을 성공했을 때 사용할 레이아웃


        // 카카오 로그인 신규가입일 때 사용할 레이아웃


        if(Session.getCurrentSession().isOpened()){
            requestMe();
        }else{
            // 세션이 없을 경우, 세션을 얻어온다.
            session.open(AuthType.KAKAO_LOGIN_ALL, KakaoLogInFragment.this);
        }

        return inflater.inflate(R.layout.kakao_log_in_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }
    //인터넷 연결상태 확인
    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }

        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            //access token을 성공적으로 발급 받아 valid access token을 가지고 있는 상태. 일반적으로 로그인 후의 다음 activity로 이동한다.
            if(Session.getCurrentSession().isOpened()){ // 한 번더 세션을 체크해주었습니다.
                requestMe();
            }
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e(exception);
            }
        }
    }



    private void requestMe() {

        UserManagement.getInstance().requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e("onFailure", errorResult + "");
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("onSessionClosed",errorResult + "");
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                String password;

                Log.e("onSuccess",userProfile.toString());

                Map<String, Object> postData = new HashMap<>();
                postData.put("email", userProfile.getEmail());
                postData.put("password", userProfile.getId()+"");
                postData.put("type", type);

                password = userProfile.getId() + "";

                MemberDto memberDto = new MemberDto();
                // 서버 통신
                ServerCommunicationForMember serverCommunication = new ServerCommunicationForMember(postData);
                try {
                    memberDto = serverCommunication.execute(joinKakaoURL).get();
                } catch(Exception e){
                    e.printStackTrace();
                }

                // 기존회원일 경우
                // db에서 가져온 이메일 주소와 닉네임을 화면에 출력해준다.
                if(!(memberDto.getEmail().equals("")) && !(memberDto.getNickname().equals(""))) {

                    memberDto.setPassword(password);
                    LoggedInInfoChecker loggedInInfoChecker = new LoggedInInfoChecker(getActivity().getApplicationContext());
                    loggedInInfoChecker.logedInInfoWriter(memberDto);

                    //MyPageActivity로 이동
                    Intent intent = new Intent(getActivity().getApplicationContext(), MyPageActivity.class); // Intent 객체에 어디로 이동할 것인지 그 정보를 담는다
                    intent.putExtra("email", memberDto.getEmail());
                    intent.putExtra("nickname", memberDto.getNickname());
                    intent.putExtra("type", memberDto.getType());
                    intent.putExtra("idx", memberDto.getIdx());
                    startActivityForResult(intent, 1000); // 두번째 매개변수 - 요청코드(아무코드나 쓰면 된다)

                } else {
                    // 신규 회원일 경우
                    // 간단한 회원가입을 받는다.
                    // KakaoJoinFragment로 이동

//                    Intent intent = new Intent(getApplicationContext(), KakaoJoinActivity.class); // Intent 객체에 어디로 이동할 것인지 그 정보를 담는다
//                    intent.putExtra("email", postData.get("email"));
//                    intent.putExtra("password", postData.get("password"));
//                    startActivityForResult(intent, 1000); // 두번째 매개변수 - 요청코드(아무코드나 쓰면 된다)
                    KakaoJoinInFragment kakaoJoinInFragment = KakaoJoinInFragment.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putString("email", (String)postData.get("email"));
                    bundle.putString("password", (String)postData.get("password"));
                    kakaoJoinInFragment.setArguments(bundle);
                    ((MainActivity)getActivity()).replaceFragment(kakaoJoinInFragment);

                }

            }

            @Override
            public void onNotSignedUp() {
                Log.e("onNotSignedUp","onNotSignedUp");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }


}