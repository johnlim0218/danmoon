package com.danmoon.project.KakaoService;

import android.app.Application;

import com.kakao.auth.KakaoSDK;

public class GlobalApplication extends Application {

    private static GlobalApplication instance;

    public static GlobalApplication getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new GlobalApplication();
            return instance;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        KakaoSDK.init(new KakaoSDKAdapter());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }
}
