package com.example.gromoredemo.immersionbar;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gromoredemo.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

public class ProgressBarActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private TextView mTextView;
    private int start = 0, maxprogress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.progress_01);
        mTextView = findViewById(R.id.tv_progress);
        maxprogress = mProgressBar.getMax();
    }

    //下载一个文件750m，大概需要6分钟。
    //那么ProgressBar的max值要设置多少
    //每秒加载多少 mProgressBar.setProgress(多少);


    @Override
    protected void onStart() {
        super.onStart();
        //启动线程加载
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        //6分钟，360.分成360
                        //把max设置成200然后你每3秒设置成setProgress1，
                        // 然后你text显示的是0.5%
                        Thread.sleep(1000);//线程休眠1s
                        start += 1;
                        if (start > maxprogress) {
                            start = maxprogress;
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

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    BigDecimal m1 = new BigDecimal(start);
                    BigDecimal m2 = new BigDecimal(maxprogress);
                    BigDecimal divide = m1.divide(m2, 4, RoundingMode.HALF_UP);
                    String B = getAccuracy(Double.parseDouble(divide.toPlainString()));
                    Log.i("BBBB", B);
                    mTextView.setText(B);//
                    mProgressBar.setProgress(start);
                    break;
            }
        }
    };

    private String getAccuracy(double accuracy) {
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);
        return nf.format(accuracy);
    }
}
