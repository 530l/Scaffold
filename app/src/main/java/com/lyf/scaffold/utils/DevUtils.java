package com.lyf.scaffold.utils;

import java.util.Locale;

public class DevUtils {

    /**
     * 设备名称
     *
     * @return 设备名称
     */
    public static String getDeviceName() {
        return android.os.Build.DEVICE;
    }

    /**
     * 设备型号
     *
     * @return 设备型号
     */
    public static String getModelName() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取厂商
     *
     * @return 厂商
     */
    public static String getBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取设备制造商
     *
     * @return 制造商
     */
    public static String getManufacturer() {
        return android.os.Build.MANUFACTURER;
    }

    /**
     * SDK 版本
     * @return
     */
    public static String getSDKVersion() {
       String SDKVersion = android.os.Build.VERSION.SDK ;
        return  SDKVersion;
    }

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }




}
