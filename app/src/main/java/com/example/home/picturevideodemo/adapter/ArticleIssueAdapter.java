package com.example.home.picturevideodemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.home.picturevideodemo.R;
import com.example.home.picturevideodemo.activity.PictureVideoActivity;
import com.example.home.picturevideodemo.bean.UserViewInfo;
import com.example.home.picturevideodemo.utils.GlideAppUtils;
import com.example.home.picturevideodemo.view.SystemUtils;
import com.luck.picture.lib.entity.LocalMedia;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleIssueAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<LocalMedia> localMediaList;
    private List<UserViewInfo> mUrls = new ArrayList<>();

    public ArticleIssueAdapter(Context mContext, List<LocalMedia> localMediaList) {
        this.mContext = mContext;
        this.localMediaList = localMediaList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = new ArticleIssueViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_at_issue_imageview, null));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ArticleIssueViewHolder) holder).setData(position);
    }

    @Override
    public int getItemCount() {
        if (localMediaList.size() < 9) {
            return localMediaList.size() + 1;
        } else {
            return localMediaList.size();
        }
    }

    class ArticleIssueViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_imageView)
        RoundedImageView ivImageView;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        @BindView(R.id.iv_gif)
        ImageView ivGif;

        private int mPosition;

        public ArticleIssueViewHolder(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
        }

        public void setData(int position) {
            this.mPosition = position;
            if (localMediaList.size() < 9 && position == localMediaList.size()) {
                ivDelete.setVisibility(View.GONE);
                ivImageView.setImageResource(R.mipmap.tianjia);
            } else {
                LocalMedia localMedia = localMediaList.get(position);
                ivDelete.setVisibility(View.VISIBLE);
                String imgUrl;
                if (localMedia.isCompressed() && !localMedia.getCompressPath().contains(".gif")) {
                    imgUrl = localMedia.getCompressPath();
                } else {
                    imgUrl = localMedia.getPath();
                }
                //是否显示gif标示
                if (localMedia.isCompressed() && localMedia.getCompressPath() != null && localMedia.getCompressPath().contains(".gif")) {
                    ivGif.setVisibility(View.VISIBLE);
                } else {
                    ivGif.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(imgUrl)) {
                    GlideAppUtils.displayImage(mContext, new File(imgUrl), R.mipmap.default_imgicon, ivImageView);
                }
            }
        }

        @OnClick({R.id.iv_imageView, R.id.iv_delete})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_imageView:
                    if (localMediaList.size() < 9 && mPosition == localMediaList.size()) {
                        ((PictureVideoActivity) mContext).openPhoto();
                    } else {
                        mUrls.clear();
                        for (int i = 0; i < localMediaList.size(); i++) {
                            String imgUrl;
                            if (localMediaList.get(i).isCompressed() && !localMediaList.get(mPosition).getCompressPath().contains(".gif")) {
                                imgUrl = localMediaList.get(i).getCompressPath();
                            } else {
                                imgUrl = localMediaList.get(i).getPath();
                            }
                            mUrls.add(new UserViewInfo(imgUrl));
                        }
                        SystemUtils.GPreviewBuilder(mContext, mPosition, mUrls, ivImageView);
                    }
                    break;
                case R.id.iv_delete:
                    localMediaList.remove(mPosition);
                    notifyDataSetChanged();
                    break;
            }
        }
    }
}
