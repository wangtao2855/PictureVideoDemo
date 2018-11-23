package com.example.home.picturevideodemo.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

/**
 * Glide图片加载框架的封装
 */
public class GlideAppUtils {

    /**
     * 普通请求封装(加载网路图片)
     *
     * @param context   上下文
     * @param imageUrl  图片地址
     * @param def       默认图片
     * @param imageView 控件
     */
    public static void displayImage(Context context, String imageUrl, int def, ImageView imageView) {
        if (imageView != null && !TextUtils.isEmpty(imageUrl)) {
            Glide.with(context).load(imageUrl).into(imageView);
        } else {
            imageView.setImageResource(def);
        }
    }

    /**
     * 普通请求封装(加载网路图片,设置为圆形)
     *
     * @param context   上下文
     * @param imageUrl  图片地址
     * @param def       默认图片
     * @param imageView 控件
     */
    public static void displayCircleImage(Context context, String imageUrl, int def, ImageView imageView) {
        if (imageView != null && !TextUtils.isEmpty(imageUrl)) {
            Glide.with(context).load(imageUrl).into(imageView);
        } else {
            imageView.setImageResource(def);
        }
    }

    /**
     * 普通请求封装(加载本地图片)
     *
     * @param context   上下文
     * @param def       默认图片
     * @param imageView 控件
     */
    public static void displayImage(Context context, int def, ImageView imageView) {
        Glide.with(context).load(def).into(imageView);
    }

    /**
     * 普通请求封装(加载路径图片)
     *
     * @param context   上下文
     * @param imageUrl  图片本地地址 相册所择图片啥的
     * @param def       默认图片
     * @param imageView 控件
     */
    public static void displayImage(Context context, File imageUrl, int def, ImageView imageView) {
        if (imageUrl != null && !TextUtils.isEmpty(imageUrl.toString())) {
            Glide.with(context).load(imageUrl).into(imageView);
        } else {
            imageView.setImageResource(def);
        }
    }
}
