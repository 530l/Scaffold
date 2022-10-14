package com.example.gromoredemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bytedance.msdk.api.AdError;
import com.bytedance.msdk.api.TToast;
import com.bytedance.msdk.api.reward.RewardItem;
import com.bytedance.msdk.api.v2.GMAdConstant;
import com.bytedance.msdk.api.v2.GMMediationAdSdk;
import com.bytedance.msdk.api.v2.ad.reward.GMRewardedAdListener;
import com.bytedance.msdk.api.v2.ad.reward.GMRewardedAdLoadCallback;
import com.example.gromoredemo.manager.AdRewardManager;
import com.example.gromoredemo.utils.AppConst;


import java.util.Map;

/**
 * 激励广告Activity
 * 加载并展示激励视频
 */
public class AdnRewardVideoActivity extends AppCompatActivity {
    private static final String TAG = AppConst.TAG_PRE;


    private String mAdUnitId; //横屏广告位id
    private AdRewardManager mAdRewardManager; //激励视频管理类

    private boolean mLoadSuccess; //是否加载成功
    private boolean mIsLoadedAndShow;//广告加载成功并展示
    private GMRewardedAdListener mGMRewardedAdListener;
    private GMRewardedAdListener mGMRewardedPlayAgainListener;

    private int orientation = GMAdConstant.HORIZONTAL;

    @SuppressLint("CutPasteId")
    @SuppressWarnings("RedundantCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_video);
        GMMediationAdSdk.requestPermissionIfNecessary(this);
        mAdUnitId = "102155365"; //横屏广告位id
        initListener();
        initAdLoader();
        //加载激励视频
        mLoadSuccess = false;
        mIsLoadedAndShow = false;
        mAdRewardManager.loadAdWithCallback(mAdUnitId, orientation);
    }


    public void initListener() {
        mGMRewardedAdListener = new GMRewardedAdListener() {

            /**
             * 广告的展示回调 每个广告仅回调一次
             */
            public void onRewardedAdShow() {
                TToast.show(AdnRewardVideoActivity.this, "激励onRewardedAdShow！");
                Log.d(TAG, "onRewardedAdShow");

            }

            /**
             * show失败回调。如果show时发现无可用广告（比如广告过期或者isReady=false），会触发该回调。
             * 开发者应该结合自己的广告加载、展示流程，在该回调里进行重新加载。
             * @param adError showFail的具体原因
             */
            @Override
            public void onRewardedAdShowFail(AdError adError) {
                if (adError == null) {
                    return;
                }
                TToast.show(AdnRewardVideoActivity.this, "激励onRewardedAdShowFail！ errCode: " + adError.code + ", errMsg: " + adError.message);
                Log.d(TAG, "onRewardedAdShowFail, errCode: " + adError.code + ", errMsg: " + adError.message);
                // 开发者应该结合自己的广告加载、展示流程，在该回调里进行重新加载
            }

            /**
             * 注意Admob的激励视频不会回调该方法
             */
            @Override
            public void onRewardClick() {
                Log.d(TAG, "onRewardClick");
                TToast.show(AdnRewardVideoActivity.this, "激励onRewardClick！");

            }

            /**
             * 广告关闭的回调
             */
            public void onRewardedAdClosed() {
                Log.d(TAG, "onRewardedAdClosed");
                TToast.show(AdnRewardVideoActivity.this, "激励onRewardedAdClosed！");

            }

            /**
             * 视频播放完毕的回调 Admob广告不存在该回调
             */
            public void onVideoComplete() {
                Log.d(TAG, "onVideoComplete");
                TToast.show(AdnRewardVideoActivity.this, "激励onVideoComplete！");

            }

            /**
             * 1、视频播放失败的回调
             */
            public void onVideoError() {
                Log.d(TAG, "onVideoError");
                TToast.show(AdnRewardVideoActivity.this, "激励onVideoError！");

            }

            /**
             * 激励视频播放完毕，验证是否有效发放奖励的回调
             */
            public void onRewardVerify(RewardItem rewardItem) {
                Map<String, Object> customData = rewardItem.getCustomData();
                if (customData != null) {
                    // 首先判断是否启用了GroMore的服务端验证
                    Boolean isGroMoreServerSideVerify = (Boolean) customData.get(RewardItem.KEY_IS_GROMORE_SERVER_SIDE_VERIFY);
                    if (isGroMoreServerSideVerify != null && isGroMoreServerSideVerify) {
                        // 开启了GroMore的服务端激励验证，这里可以获取GroMore的服务端激励验证信息
                        boolean isVerify = rewardItem.rewardVerify();
                        // 如果isVerify=false，则可以根据下面的错误码来判断为什么是false，
                        //  1、如果errorCode为40001/40002/50001/50002，则是因为请求异常导致，媒体可以根据自己的判断决定是否发放奖励。
                        //  2、否则，就是媒体服务端回传的验证结果是false，此时应该不发放奖励。

                        Integer reason = (Integer) customData.get(RewardItem.KEY_REASON);
                        if (reason != null) {
                            Log.d(TAG, "rewardItem，开发者服务器回传的reason，开发者不传时为空");
                        }
                        Integer errorCode = (Integer) customData.get(RewardItem.KEY_ERROR_CODE);
                        if (errorCode != null) {
                            String errorMsg = (String) customData.get(RewardItem.KEY_ERROR_MSG);
                            Log.d(TAG, "rewardItem, gromore服务端验证异常时的错误信息，未发生异常时为0或20000：errorCode:" + errorCode + ", errMsg: " + errorMsg);
                        }
                        String gromoreExtra = (String) customData.get(RewardItem.KEY_GROMORE_EXTRA);
                        Log.d(TAG, "rewardItem, 开发者通过AdSlot传入的extra信息，会透传给媒体的服务器。开发者不传时为空，extra:" + gromoreExtra);
                        String transId = (String) customData.get(RewardItem.KEY_TRANS_ID);
                        Log.d(TAG, "rewardItem, gromore服务端验证产生的transId，一次广告播放会产生的唯一的transid: " + transId);
                    } else {
                        // 未开启GroMore的服务端激励验证，这里获取adn的激励验证信息
                        String adnName = (String) customData.get(RewardItem.KEY_ADN_NAME);
                        if (!TextUtils.isEmpty(adnName)) {
                            switch (adnName) {
                                case RewardItem.KEY_GDT:
                                    Log.d(TAG, "rewardItem gdt: " + customData.get(RewardItem.KEY_GDT_TRANS_ID));
                                    break;
                            }
                        }
                    }
                }
                Log.d(TAG, "onRewardVerify");
                TToast.show(AdnRewardVideoActivity.this, "onRewardVerify！");
            }

            /**
             * - Mintegral GDT Admob广告不存在该回调
             */
            @Override
            public void onSkippedVideo() {

            }

        };

        //穿山甲再看一次监听
        mGMRewardedPlayAgainListener = new GMRewardedAdListener() {
            /**
             * 广告的展示回调 每个广告仅回调一次
             */
            public void onRewardedAdShow() {
                TToast.show(AdnRewardVideoActivity.this, "激励onRewardedAdShow！");
                Log.d(TAG, "onRewardedAdShow---play again");

            }

            /**
             * show失败回调。如果show时发现无可用广告（比如广告过期或者isReady=false），会触发该回调。
             * 开发者应该结合自己的广告加载、展示流程，在该回调里进行重新加载。
             * @param adError showFail的具体原因
             */
            @Override
            public void onRewardedAdShowFail(AdError adError) {
                if (adError == null) {
                    return;
                }
                TToast.show(AdnRewardVideoActivity.this, "激励onRewardedAdShowFail！ errCode: " + adError.code + ", errMsg: " + adError.message);
                Log.d(TAG, "onRewardedAdShowFail---play again, errCode: " + adError.code + ", errMsg: " + adError.message);
                // 开发者应该结合自己的广告加载、展示流程，在该回调里进行重新加载
            }

            /**
             * 注意Admob的激励视频不会回调该方法
             */
            @Override
            public void onRewardClick() {
                Log.d(TAG, "onRewardClick---play again");
                TToast.show(AdnRewardVideoActivity.this, "激励onRewardClick！");

            }

            /**
             * 广告关闭的回调
             */
            public void onRewardedAdClosed() {
                Log.d(TAG, "onRewardedAdClosed---play again");
                TToast.show(AdnRewardVideoActivity.this, "激励onRewardedAdClosed！");

            }

            /**
             * 视频播放完毕的回调 Admob广告不存在该回调
             */
            public void onVideoComplete() {
                Log.d(TAG, "onVideoComplete---play again");
                TToast.show(AdnRewardVideoActivity.this, "激励onVideoComplete！");

            }

            /**
             * 1、视频播放失败的回调
             */
            public void onVideoError() {
                Log.d(TAG, "onVideoError---play again");
                TToast.show(AdnRewardVideoActivity.this, "激励onVideoError！");

            }

            /**
             * 激励视频播放完毕，验证是否有效发放奖励的回调
             */
            public void onRewardVerify(RewardItem rewardItem) {
                Map<String, Object> customData = rewardItem.getCustomData();
                if (customData != null) {
                    // 首先判断是否启用了GroMore的服务端验证
                    Boolean isGroMoreServerSideVerify = (Boolean) customData.get(RewardItem.KEY_IS_GROMORE_SERVER_SIDE_VERIFY);
                    if (isGroMoreServerSideVerify != null && isGroMoreServerSideVerify) {
                        // 开启了GroMore的服务端激励验证，这里可以获取GroMore的服务端激励验证信息
                        boolean isVerify = rewardItem.rewardVerify();
                        // 如果isVerify=false，则可以根据下面的错误码来判断为什么是false，
                        //  1、如果errorCode为40001/40002/50001/50002，则是因为请求异常导致，媒体可以根据自己的判断决定是否发放奖励。
                        //  2、否则，就是媒体服务端回传的验证结果是false，此时应该不发放奖励。

                        Integer reason = (Integer) customData.get(RewardItem.KEY_REASON);
                        if (reason != null) {
                            Log.d(TAG, "rewardItem，开发者服务器回传的reason，开发者不传时为空");
                        }
                        Integer errorCode = (Integer) customData.get(RewardItem.KEY_ERROR_CODE);
                        if (errorCode != null) {
                            String errorMsg = (String) customData.get(RewardItem.KEY_ERROR_MSG);
                            Log.d(TAG, "rewardItem, gromore服务端验证异常时的错误信息，未发生异常时为0或20000：errorCode:" + errorCode + ", errMsg: " + errorMsg);
                        }
                        String gromoreExtra = (String) customData.get(RewardItem.KEY_GROMORE_EXTRA);
                        Log.d(TAG, "rewardItem, 开发者通过AdSlot传入的extra信息，会透传给媒体的服务器。开发者不传时为空，extra:" + gromoreExtra);
                        String transId = (String) customData.get(RewardItem.KEY_TRANS_ID);
                        Log.d(TAG, "rewardItem, gromore服务端验证产生的transId，一次广告播放会产生的唯一的transid: " + transId);
                    } else {
                        // 未开启GroMore的服务端激励验证，这里获取adn的激励验证信息
                        String adnName = (String) customData.get(RewardItem.KEY_ADN_NAME);
                        if (!TextUtils.isEmpty(adnName)) {
                            switch (adnName) {
                                case RewardItem.KEY_GDT:
                                    Log.d(TAG, "rewardItem gdt: " + customData.get(RewardItem.KEY_GDT_TRANS_ID));
                                    break;
                            }
                        }
                        //穿山甲服务端验证新增的接口参数也放在customData中，可以按需获取数据，如：
                        if (customData != null) {
                            String rewardType = (String) customData.get(RewardItem.KEY_REWARD_TYPE); //获取奖励类型
                            Bundle extraInfo = (Bundle) customData.get(RewardItem.KEY_EXTRA_INFO); //获取额外参数
                            //从extraInfo中拿参数
//                            float rewardPropose = extraInfo.getFloat(TTRewardVideoAd.REWARD_EXTRA_KEY_REWARD_PROPOSE); //获取奖励百分比
                        }
                    }
                }
                Log.d(TAG, "onRewardVerify---play again");
                TToast.show(AdnRewardVideoActivity.this, "onRewardVerify！");
            }

            /**
             * - Mintegral GDT Admob广告不存在该回调
             */
            @Override
            public void onSkippedVideo() {

            }
        };
    }

    public void initAdLoader() {
        mAdRewardManager = new AdRewardManager(this, new GMRewardedAdLoadCallback() {
            @Override
            public void onRewardVideoLoadFail(AdError adError) {
                mLoadSuccess = false;
                Log.e(TAG, "load RewardVideo ad error : " + adError.code + ", " + adError.message);
                mAdRewardManager.printLoadFailAdnInfo();
            }

            @Override
            public void onRewardVideoAdLoad() {
                mLoadSuccess = true;
                Log.e(TAG, "load RewardVideo ad success !");
                // 获取本次waterfall加载中，加载失败的adn错误信息。
                mAdRewardManager.printLoadAdInfo(); //打印已经加载广告的信息
                mAdRewardManager.printLoadFailAdnInfo();//打印加载失败的adn错误信息
                //todo 加载成功。播放
                showRewardAd();
            }

            @Override
            public void onRewardVideoCached() {
                mLoadSuccess = true;
                Log.d(TAG, "onRewardVideoCached....缓存成功");
                TToast.show(AdnRewardVideoActivity.this, "激励视频素材缓存成功！");
                if (mIsLoadedAndShow) { //加载并展示
                    showRewardAd();
                }
            }
        });
    }

    /**
     * 展示广告
     */
    private void showRewardAd() {
        if (mLoadSuccess && mAdRewardManager != null) {
            if (mAdRewardManager.getGMRewardAd() != null && mAdRewardManager.getGMRewardAd().isReady()) {
                //在获取到广告后展示,强烈建议在onRewardVideoCached回调后，展示广告，提升播放体验
                //该方法直接展示广告，如果展示失败了（如过期），会回调onVideoError()
                //展示广告，并传入广告展示的场景
                mAdRewardManager.getGMRewardAd().setRewardAdListener(mGMRewardedAdListener);
                mAdRewardManager.getGMRewardAd().setRewardPlayAgainListener(mGMRewardedPlayAgainListener);
                mAdRewardManager.getGMRewardAd().showRewardAd(this);
                mAdRewardManager.printSHowAdInfo();//打印已经展示的广告信息
                mLoadSuccess = false;
            } else {
                TToast.show(this, "当前广告不满足show的条件");
            }
        } else {
            TToast.show(this, "请先加载广告");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAdRewardManager != null) {
            mAdRewardManager.destroy();
        }
    }

}
