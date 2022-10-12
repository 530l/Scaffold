package com.lyf.scaffold.adn;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdLoadType;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.lyf.scaffold.R;
import com.lyf.scaffold.adn.utils.AdnToast;


public class RewardVideoActivity extends Activity {
    private static RewardVideoActivity _Instance;

    public static RewardVideoActivity Inst() {
        return _Instance;
    }

    private TTAdNative mTTAdNative;
    private boolean isExpress = false;
    private String TAG = "RewardVideoActivity";
    private boolean mIsLoaded = false; //视频是否加载完成
    private TTRewardVideoAd mttRewardVideoAd;

    // 是否开放进阶奖励功能
    private boolean isEnableAdvancedReward = false;

    private boolean mHasShowDownloadActive = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pewardvideo);
        _Instance = this;
        //step1:初始化sdk
        TTAdManager ttAdManager = TTAdManagerHolder.getTTAdManager();
        if (ttAdManager == null) return;
        //step2:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdManagerHolder.getTTAdManager().requestPermissionIfNecessary(this);
        //step3:创建TTAdNative对象,用于调用广告请求接口
        mTTAdNative = ttAdManager.createAdNative(getApplicationContext());
        Intent it = getIntent();
        String codeId = it.getStringExtra("codeId");
        int orientation = it.getIntExtra("orientation", 2);
        String userId = it.getStringExtra("userId");
        AddLoad(codeId, orientation, userId);
    }

    //codeId 平台创建的代码位ID 以9开头9位数字 orientation 期望视频的播放方向 1竖屏 2横屏
    public void AddLoad(final String codeId, int orientation, String userId) {
        AdSlot adSlot;
        adSlot = new AdSlot.Builder()
                .setCodeId(codeId)
                //模板广告需要设置期望个性化模板广告的大小,单位dp,激励视频场景，只要设置的值大于0即可
                //且仅是模板渲染的代码位ID使用，非模板渲染代码位切勿使用
                .setExpressViewAcceptedSize(1080, 1920)
                .setUserID(userId)//tag_id
                .setMediaExtra("media_extra") //附加参数
                .setOrientation(orientation) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                .setAdLoadType(TTAdLoadType.LOAD)//推荐使用，用于标注此次的广告请求用途为预加载（当做缓存）还是实时加载，方便后续为开发者优化相关策略
                .build();
        mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.e(TAG, "Callback --> onError: " + code + ", " + String.valueOf(message));
                goToMainActivity();
                AdnToast.show(RewardVideoActivity.this, message);
            }

            //视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
            @Override
            public void onRewardVideoCached() {
                Log.e(TAG, "Callback --> onRewardVideoCached");
                mIsLoaded = true;
                AdnToast.show(RewardVideoActivity.this, "Callback --> rewardVideoAd video cached");
            }

            @Override
            public void onRewardVideoCached(TTRewardVideoAd ad) {
                Log.e(TAG, "Callback --> onRewardVideoCached");
                mIsLoaded = true;
                AdnToast.show(RewardVideoActivity.this, "Callback --> rewardVideoAd video cached");
                ad.showRewardVideoAd(RewardVideoActivity.this, TTAdConstant.RitScenes.CUSTOMIZE_SCENES, "scenes_test");
            }

            //视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
                Log.e(TAG, "Callback --> onRewardVideoAdLoad");
                AdnToast.show(RewardVideoActivity.this, "rewardVideoAd loaded 广告类型：" + getAdType(ad.getRewardVideoAdType()));
                mIsLoaded = false;
                mttRewardVideoAd = ad;
                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {
                    @Override
                    //广告的展示回调
                    public void onAdShow() {
                        Log.d(TAG, "Callback --> rewardVideoAd show");
                        AdnToast.show(RewardVideoActivity.this, "Callback --> rewardVideoAd show");
                    }

                    @Override
                    //广告下载bar点击回调
                    public void onAdVideoBarClick() {
                        Log.d(TAG, "Callback --> rewardVideoAd bar click");
                        AdnToast.show(RewardVideoActivity.this, "Callback --> onAdVideoBarClick");
                    }

                    @Override
                    //广告关闭回调
                    public void onAdClose() {
                        Log.d(TAG, "Callback --> rewardVideoAd close");
                        if (isEnableAdvancedReward) {
                            Log.d(TAG, "本次奖励共发放：");
                        }
                        AdnToast.show(RewardVideoActivity.this, "Callback --> rewardVideoAd close");
                        goToMainActivity();
                    }

                    //视频播放完成回调
                    @Override
                    public void onVideoComplete() {
                        Log.d(TAG, "Callback --> rewardVideoAd complete");
                        AdnToast.show(RewardVideoActivity.this, "Callback --> rewardVideoAd complete");
                    }

                    @Override
                    public void onVideoError() {
                        Log.e(TAG, "Callback --> rewardVideoAd error");
                        AdnToast.show(RewardVideoActivity.this, "Callback --> rewardVideoAd error");
                        goToMainActivity();
                    }

                    //视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
                    @Override
                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName, int errorCode, String errorMsg) {
                        String logString = "verify:" + rewardVerify + " amount:" + rewardAmount +
                                " name:" + rewardName + " errorCode:" + errorCode + " errorMsg:" + errorMsg;
                        Log.e(TAG, "onRewardVerify Callback --> " + logString);
//                        goToMainActivity("");
                        AdnToast.show(RewardVideoActivity.this, "Callback --> onRewardVerify");
                    }

                    /**
                     * 激励视频播放完毕，验证是否有效发放奖励的回调 4400版本新增
                     *
                     * @param isRewardValid 奖励有效
                     * @param rewardType 奖励类型，0:基础奖励 >0:进阶奖励
                     * @param extraInfo 奖励的额外参数
                     */
                    @Override
                    public void onRewardArrived(boolean isRewardValid, int rewardType, Bundle extraInfo) {
                        Log.e(TAG, "Callback --> rewardVideoAd has onRewardArrived ");
                        AdnToast.show(RewardVideoActivity.this, "Callback --> onRewardArrived");
                    }

                    //跳过视频播放回调
                    @Override
                    public void onSkippedVideo() {
                        Log.e(TAG, "Callback --> rewardVideoAd has onSkippedVideo");
                    }
                });
                mttRewardVideoAd.setRewardPlayAgainInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {
                    @Override
                    public void onAdShow() {
                        Log.d(TAG, "Callback --> rewardPlayAgain show");
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        Log.d(TAG, "Callback --> rewardPlayAgain bar click");
                        AdnToast.show(RewardVideoActivity.this, "Callback --> rewardPlayAgain bar click");
                    }

                    @Override
                    public void onAdClose() {
                        Log.d(TAG, "Callback --> rewardPlayAgain close");
                        AdnToast.show(RewardVideoActivity.this, "Callback --> rewardPlayAgain bar click");
                    }

                    //视频播放完成回调
                    @Override
                    public void onVideoComplete() {
                        Log.d(TAG, "Callback --> rewardPlayAgain complete");
                        AdnToast.show(RewardVideoActivity.this, "Callback --> rewardPlayAgain complete");
                    }

                    @Override
                    public void onVideoError() {
                        Log.e(TAG, "Callback --> rewardPlayAgain error");
                        AdnToast.show(RewardVideoActivity.this, "Callback --> rewardPlayAgain error");
                    }

                    //视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
                    @Override
                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName, int errorCode, String errorMsg) {
                        String logString = "rewardPlayAgain verify:" + rewardVerify + " amount:" + rewardAmount +
                                " name:" + rewardName + " errorCode:" + errorCode + " errorMsg:" + errorMsg;
                        Log.e(TAG, "Callback --> " + logString);
                        AdnToast.show(RewardVideoActivity.this, "Callback --> rewardPlayAgain onRewardVerify");
                    }

                    @Override
                    public void onRewardArrived(boolean isRewardValid, int rewardType, Bundle extraInfo) {
                        Log.e(TAG, "Callback --> rewardPlayAgain has onRewardArrived ");
                        AdnToast.show(RewardVideoActivity.this, "Callback --> rewardPlayAgain onRewardArrived");
                    }

                    @Override
                    public void onSkippedVideo() {
                        Log.e(TAG, "Callback --> rewardPlayAgain has onSkippedVideo");
                        AdnToast.show(RewardVideoActivity.this, "Callback --> rewardPlayAgain onSkippedVideo");
                    }
                });
                mttRewardVideoAd.setDownloadListener(new TTAppDownloadListener() {
                    @Override
                    public void onIdle() {
                        mHasShowDownloadActive = false;
                    }

                    @Override
                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadActive==totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);
                        if (!mHasShowDownloadActive) {
                            mHasShowDownloadActive = true;
                            Log.d("DML", "onDownloadActive==totalBytes=  DownLoad .......");
                        }
                    }

                    @Override
                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadPaused===totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);
                    }

                    @Override
                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadFailed==totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);
                    }

                    @Override
                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadFinished==totalBytes=" + totalBytes + ",fileName=" + fileName + ",appName=" + appName);
                    }

                    @Override
                    public void onInstalled(String fileName, String appName) {
                        Log.d("DML", "onInstalled==" + ",fileName=" + fileName + ",appName=" + appName);
                    }
                });
            }
        });
    }

    public void SetExpress(boolean exp) {
        isExpress = exp;
    }

    private void goToMainActivity() {
//        Intent intent = new Intent(RewardVideoActivity.this, UnityPlayerActivity.class);
//        startActivity(intent);
//        this.finish();
    }

    private String getAdType(int type) {
        switch (type) {

            case TTAdConstant.AD_TYPE_COMMON_VIDEO:
                return "普通激励视频，type=" + type;

            case TTAdConstant.AD_TYPE_PLAYABLE_VIDEO:
                return "Playable激励视频，type=" + type;

            case TTAdConstant.AD_TYPE_PLAYABLE:
                return "纯Playable，type=" + type;

            case TTAdConstant.AD_TYPE_LIVE:
                return "直播流，type=" + type;
        }

        return "未知类型+type=" + type;
    }

    public void RewardVideo(Context context, String codeId, int orientation, String userId) {
        Log.d(TAG, "RewardVideo  codeId:" + codeId + "orientation" + orientation + "userId" + userId);
        Intent intent = new Intent(context, RewardVideoActivity.class);
        intent.putExtra("codeId", codeId);
        intent.putExtra("orientation", orientation);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }
}
