package com.example.home.picturevideodemo.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.example.home.picturevideodemo.R;
import com.example.home.picturevideodemo.bean.UserViewInfo;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.previewlibrary.GPreviewBuilder;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

import static com.bumptech.glide.load.resource.bitmap.VideoDecoder.FRAME_OPTION;

/**
 * 乱七八糟的封装到这个工具类里面
 */
public class SystemUtils {

    /**
     * 查找信息 为了让图片从中间弹出来
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     */
    public static void computeBoundsBackward(NineGridImageView nglImages, List<UserViewInfo> list) {
        for (int i = 0; i < nglImages.getChildCount(); i++) {
            View itemView = nglImages.getChildAt(i);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView thumbView = (ImageView) itemView;
                thumbView.getGlobalVisibleRect(bounds);
            }
            list.get(i).setBounds(bounds);
            list.get(i).setUrl(list.get(i).getUrl());
        }
    }

    /**
     * NineGridImageView图片控件不支持一直图片的时候按照横竖图缩放,所以这边如果是一张图片就直接用ImageView
     * 查找信息 为了让图片从中间弹出来,
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     */
    public static void computeBoundsBackward(ImageView nglImages, List<UserViewInfo> list) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Rect bounds = new Rect();
                if (nglImages != null) {
                    nglImages.getGlobalVisibleRect(bounds);
                }
                list.get(i).setBounds(bounds);
                list.get(i).setUrl(list.get(i).getUrl());
            }
        }
    }


    /**
     * 图片放大器工具类(一张图片的时候，并且图片是ImageView)
     */
    public static void GPreviewBuilder(Context context, int index, String imgUrl, ImageView imageView) {
        List<UserViewInfo> mUrls = new ArrayList<>();
        mUrls.add(new UserViewInfo(imgUrl));
        //从图片的位置弹出来
        computeBoundsBackward(imageView, mUrls);
        //图片放大
        GPreviewBuilder.from((Activity) context)
                .setCurrentIndex(index)
                .setData(mUrls)
                .setType(GPreviewBuilder.IndicatorType.Dot)
                .start();//启动
    }

    /**
     * 图片放大器工具类(一张图片的时候，并且图片是ImageView)
     */
    public static void GPreviewBuilder(Context context, int index, List<UserViewInfo> mUrls, NineGridImageView imageView) {
        //从图片的位置弹出来
        computeBoundsBackward(imageView, mUrls);
        //图片放大
        GPreviewBuilder.from((Activity) context)
                .setCurrentIndex(index)
                .setData(mUrls)
                .setType(GPreviewBuilder.IndicatorType.Dot)
                .start();//启动
    }

    /**
     * 图片放大器工具类(一张图片的时候，并且图片是ImageView)
     */
    public static void GPreviewBuilder(Context context, int index, List<UserViewInfo> mUrls, ImageView imageView) {
        //从图片的位置弹出来
        computeBoundsBackward(imageView, mUrls);
        //图片放大
        GPreviewBuilder.from((Activity) context)
                .setCurrentIndex(index)
                .setData(mUrls)
                .setType(GPreviewBuilder.IndicatorType.Dot)
                .start();//启动
    }


    /**
     * textView的drawable转换
     */
    public static void textViewSetDrawable(Context context, TextView textView, int img) {
        Drawable drawable = context.getResources().getDrawable(img);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 自动播放的逻辑
     */
    public static void autoPlayVideo(RecyclerView recyclerView, int visibleCount) {
        Jzvd.SAVE_PROGRESS = false; //不支持视频缓存
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        for (int i = 0; i < visibleCount; i++) {
            if (layoutManager != null && layoutManager.getChildAt(i) != null && layoutManager.getChildAt(i).findViewById(R.id.videoplayer) != null) {
                JzvdStd jzvdStd = layoutManager.getChildAt(i).findViewById(R.id.videoplayer);
                Rect rect = new Rect();
                jzvdStd.getLocalVisibleRect(rect);
                int videoHeight = jzvdStd.getHeight();
                if (rect.top == 0 && rect.bottom == videoHeight) {
                    if (jzvdStd.currentState == JzvdStd.CURRENT_STATE_NORMAL || jzvdStd.currentState == JzvdStd.CURRENT_STATE_ERROR) {
                        jzvdStd.startButton.performClick();
                    }
                    return;
                }

            }
        }
        Jzvd.releaseAllVideos();
    }

    /**
     * context 上下文
     * uri 视频地址
     * imageView 设置image
     * frameTimeMicros 获取某一时间帧
     */
    public static void loadVideoScreenshot(final Context context, String uri, ImageView imageView, long frameTimeMicros) {
        RequestOptions requestOptions = RequestOptions.frameOf(frameTimeMicros);
        requestOptions.set(FRAME_OPTION, MediaMetadataRetriever.OPTION_CLOSEST);
        requestOptions.transform(new BitmapTransformation() {
            @Override
            protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
                return toTransform;
            }

            @Override
            public void updateDiskCacheKey(MessageDigest messageDigest) {
                try {
                    messageDigest.update((context.getPackageName() + "RotateTransform").getBytes("utf-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Glide.with(context).load(uri).apply(requestOptions).into(imageView);
    }
}
