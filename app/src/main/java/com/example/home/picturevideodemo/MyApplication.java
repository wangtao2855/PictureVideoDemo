package com.example.home.picturevideodemo;

import android.app.Application;

import com.example.home.picturevideodemo.view.JZExoPlayer;
import com.example.home.picturevideodemo.view.TestImageLoader;
import com.previewlibrary.ZoomMediaLoader;

import cn.jzvd.Jzvd;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //视频参数配置
        Jzvd.setMediaInterface(new JZExoPlayer());  //exo
        ZoomMediaLoader.getInstance().init(new TestImageLoader());
    }
}
