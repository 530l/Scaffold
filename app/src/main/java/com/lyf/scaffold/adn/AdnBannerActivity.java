package com.lyf.scaffold.adn;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.lyf.scaffold.R;
import com.lyf.scaffold.adn.utils.AdnBanner;
import com.lyf.scaffold.adn.utils.AdnInsertScreen;

public class AdnBannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adnbanner);
        //获取到声明中的权限，提高广告变现效率。记得禁止插屏和Banner广告的权限申请否则会重复。
        if (TTAdManagerHolder.getTTAdManager() == null) return;
        TTAdManagerHolder.getTTAdManager().requestPermissionIfNecessary(getApplicationContext());
        //新插屏广告                              插屏广告代码位id
        AdnInsertScreen.Inst().Init(this, "949987171", TTAdConstant.HORIZONTAL);
        //banner广告   banner广告代码位id
        FrameLayout mExpressBannerContainer = findViewById(R.id.banner_container);
        AdnBanner.Inst().Init(this, mExpressBannerContainer, "949987194");


    }
}
