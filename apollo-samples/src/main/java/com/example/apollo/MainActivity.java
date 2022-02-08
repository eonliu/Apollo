package com.example.apollo;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.eonliu.apollo.Apollo;
import com.eonliu.apollo.environments.AppEnvironments;
import com.eonliu.apollo.environments.LibEnvironments;
import com.eonliu.apollo.environments.SearchEnvironments;
import com.example.apollo.util.Logger;

/**
 * @author Eon Liu
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logger.d(String.format("音乐地址 >>> %s", AppEnvironments.getMusicUrl()));
        Logger.d(String.format("视频地址 >>> %s", AppEnvironments.getVideoUrl()));
        Logger.d(String.format("搜索地址 >>> %s", SearchEnvironments.getSearchUrl()));
        Logger.d(String.format("Lib地址 >>> %s", LibEnvironments.getApolloUrl()));

        // 测试日志开关
        findViewById(R.id.logButton).setOnClickListener(view -> {
            if (Apollo.logEnable()) {
                Log.d(TAG, ">>> log日志已开启，可以打印调试日志信息。");
            } else {
                Log.e(TAG, ">>> log日志已关闭（默认状态为关闭），一般为Release包，此时不应该打印业务日志。");
            }
        });

        // 测试环境切换
        findViewById(R.id.envButton).setOnClickListener(view -> {
            Logger.d(String.format("音乐地址 >>> %s", AppEnvironments.getMusicUrl()));
            Logger.d(String.format("视频地址 >>> %s", AppEnvironments.getVideoUrl()));
            Logger.d(String.format("搜索地址 >>> %s", SearchEnvironments.getSearchUrl()));
            Logger.d(String.format("Lib地址 >>> %s", LibEnvironments.getApolloUrl()));
        });
    }
}