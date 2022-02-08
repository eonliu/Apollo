package com.example.apollo;

import android.app.Application;

import com.eonliu.apollo.Apollo;

/**
 * @author Eon Liu
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 如果是debug包则默认打印日志，否则默认不打印日志
        Apollo.setLogEnable(BuildConfig.DEBUG);
    }
}
