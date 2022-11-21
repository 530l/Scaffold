package com.example.gromoredemo.immersionbar;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gromoredemo.R;

import java.util.Random;

public class ProgressBarActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private TextView mTextView;
    private int start = 0, maxprogress;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mTextView.setText(start + " %");//更新进度
                    mProgressBar.setProgress(start);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.progress_01);
        mTextView = findViewById(R.id.tv_progress);
        maxprogress = mProgressBar.getMax();
    }

    //750m  需要5分钟完成。 那么
    @Override
    protected void onStart() {
        super.onStart();
        //启动线程加载
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);//线程休眠1s
                        int a = new Random().nextInt(10);//产生一个10以内的随机数
                        start += a;
                        if (start > maxprogress) {
                            start = 100;
                            //如果进程超过最大值
                            break;
                        }

                        mHandler.sendEmptyMessage(0);//在安卓里。我们不能直接在线程中更新UI，这里用Hander消息处理
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
