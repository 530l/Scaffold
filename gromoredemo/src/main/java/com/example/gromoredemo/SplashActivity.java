package com.example.gromoredemo;



import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;

import com.bytedance.msdk.api.AdError;
import com.bytedance.msdk.api.TToast;
import com.bytedance.msdk.api.v2.ad.splash.GMSplashAdListener;
import com.bytedance.msdk.api.v2.ad.splash.GMSplashAdLoadCallback;
import com.example.gromoredemo.manager.AdSplashManager;
import com.example.gromoredemo.utils.AppConst;

/**
 * 开屏广告Activity示例
 */
public class SplashActivity extends BaseActivity {
    public static final String EXTRA_FORCE_LOAD_BOTTOM = "extra_force_load_bottom";

    private static final String TAG = AppConst.TAG_PRE + SplashActivity.class.getSimpleName();

    private FrameLayout mSplashContainer;
    private String mAdUnitId = "102155800";
    private AdSplashManager mAdSplashManager;
    private boolean mForceLoadBottom;
    private GMSplashAdListener mSplashAdListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSplashContainer = findViewById(R.id.splash_container);
        mForceLoadBottom = getIntent().getBooleanExtra(EXTRA_FORCE_LOAD_BOTTOM, false);
        initListener();
        initAdLoader();
        //加载开屏广告
        if (mAdSplashManager != null) {
            mAdSplashManager.loadSplashAd(mAdUnitId);
        }
    }

    @Override
    public void initListener() {
        mSplashAdListener = new GMSplashAdListener() {
            @Override
            public void onAdClicked() {
                Log.d(TAG, "onAdClicked");
            }

            @Override
            public void onAdShow() {
                Log.d(TAG, "onAdShow");
            }

            /**
             * show失败回调。如果show时发现无可用广告（比如广告过期），会触发该回调。
             * 开发者应该结合自己的广告加载、展示流程，在该回调里进行重新加载。
             * @param adError showFail的具体原因
             */
            @Override
            public void onAdShowFail(AdError adError) {
                Log.d(TAG, "onAdShowFail");
                // 开发者应该结合自己的广告加载、展示流程，在该回调里进行重新加载
                if (mAdSplashManager != null) {
                    mAdSplashManager.loadSplashAd(mAdUnitId);
                }
            }

            @Override
            public void onAdSkip() {
                Log.d(TAG, "onAdSkip");
                goToMainActivity();
            }

            @Override
            public void onAdDismiss() {
                Log.d(TAG, "onAdDismiss");
                goToMainActivity();
            }
        };
    }


    @Override
    public void initAdLoader() {
        mAdSplashManager = new AdSplashManager(this, mForceLoadBottom, new GMSplashAdLoadCallback() {
            @Override
            public void onSplashAdLoadFail(AdError adError) {
                TToast.show(SplashActivity.this, "广告加载失败");
                Log.d(TAG, adError.message);
                Log.e(TAG, "load splash ad error : " + adError.code + ", " + adError.message);
                // 获取本次waterfall加载中，加载失败的adn错误信息。
                if (mAdSplashManager.getSplashAd() != null)
                    Log.d(TAG, "ad load infos: " + mAdSplashManager.getSplashAd().getAdLoadInfoList());
                goToMainActivity();
            }

            @Override
            public void onSplashAdLoadSuccess() {
                TToast.show(SplashActivity.this, "广告加载成功");
                Log.e(TAG, "load splash ad success ");
                mAdSplashManager.printInfo();
                // 根据需要选择调用isReady()
//                if (mAdSplashManager.getSplashAd().isReady()) {
//                    mAdSplashManager.getSplashAd().showAd(mSplashContainer);
//                }
                mAdSplashManager.getSplashAd().showAd(mSplashContainer);

            }

            // 注意：***** 开屏广告加载超时回调已废弃，统一走onSplashAdLoadFail，GroMore作为聚合不存在SplashTimeout情况。*****
            @Override
            public void onAdLoadTimeout() {
            }
        }, mSplashAdListener);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAdSplashManager != null) {
            mAdSplashManager.destroy();
        }
    }

    /**
     * 跳转到主页面
     */
    private void goToMainActivity() {
        mSplashContainer.removeAllViews();
        this.finish();
    }

}
