package com.danmoon.project.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.danmoon.project.Activity.MainActivity;
import com.danmoon.project.R;

public class FirstMenuFragment extends Fragment {


    public static FirstMenuFragment newInstance(){
        return new FirstMenuFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final LocalJoinInFragment localJoinInFragment = new LocalJoinInFragment();

        View view = inflater.inflate(R.layout.firstmenu_fragment, container, false);

        Button localJoinButton = (Button)view.findViewById(R.id.localjoin_button);
        Button localLoginButton = (Button)view.findViewById(R.id.locallogin_button);
        Button kakaoLoginButton = (Button)view.findViewById(R.id.kakaologin_button);

        localJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MainActivity)getActivity()).replaceFragment(LocalJoinInFragment.newInstance());
            }
        });

        localLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(LocalLogInFragment.newInstance());
            }
        });

        kakaoLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(KakaoLogInFragment.newInstance());
            }
        });

        return view;
    }

}
