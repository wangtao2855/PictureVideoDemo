package com.example.home.picturevideodemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.home.picturevideodemo.R;
import com.example.home.picturevideodemo.adapter.ArticleIssueAdapter;
import com.example.home.picturevideodemo.utils.EmojiKeyboardUtils;
import com.example.home.picturevideodemo.utils.SimpleCommonUtils;
import com.example.home.picturevideodemo.view.JZExoPlayer;
import com.example.home.picturevideodemo.view.MyEmoticonsIndicatorView;
import com.example.home.picturevideodemo.view.SystemUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import sj.keyboard.adpater.PageSetAdapter;
import sj.keyboard.data.PageSetEntity;
import sj.keyboard.interfaces.EmoticonClickListener;
import sj.keyboard.widget.EmoticonsEditText;
import sj.keyboard.widget.EmoticonsFuncView;
import sj.keyboard.widget.EmoticonsToolBarView;

public class PictureVideoActivity extends AppCompatActivity implements EmoticonsFuncView.OnEmoticonsPageViewListener, EmoticonsToolBarView.OnToolBarItemClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_titleBar_send)
    TextView tvRigth;

    @BindView(R.id.issue_editText)
    EmoticonsEditText editText;
    @BindView(R.id.rv_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.videoplayer)
    JzvdStd jzvdStd;
    @BindView(R.id.nes_scrollView1)
    NestedScrollView nestedScrollView;
    @BindView(R.id.issue_photo)
    ImageView issuePhoto;
    @BindView(R.id.il_emoji)
    RelativeLayout ilEmoji;
    @BindView(R.id.view_epv)
    EmoticonsFuncView mEmoticonsFuncView;
    @BindView(R.id.view_eiv)
    MyEmoticonsIndicatorView mEmoticonsIndicatorView;
    @BindView(R.id.view_etv)
    EmoticonsToolBarView mEmoticonsToolBarView;
    @BindView(R.id.issue_emoji)
    ImageView issueEmoji;
    @BindView(R.id.ll_keyboard)
    LinearLayout llKeyboard;

    private Context mContext;
    private ArticleIssueAdapter articleIssueAdapter;
    private List<LocalMedia> localMediaList = new ArrayList<>();
    private String type = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_video);
        this.mContext = this;
        ButterKnife.bind(this);
        tvTitle.setText("图片视频选择");
        tvRigth.setVisibility(View.VISIBLE );
        initView();
    }

    private void initView() {
        //判断是从故事界面进来的还是组的详情页面进来的
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 30, false));
        recyclerView.setLayoutManager(gridLayoutManager);
        articleIssueAdapter = new ArticleIssueAdapter(mContext, localMediaList);
        recyclerView.setAdapter(articleIssueAdapter);
        //表情
        SimpleCommonUtils.initEmoticonsEditText(editText);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        mEmoticonsFuncView.setOnIndicatorListener(this);
        mEmoticonsToolBarView.setOnToolBarItemClickListener(this);

        EmoticonClickListener emoticonClickListener = SimpleCommonUtils.getCommonEmoticonClickListener(editText);
        PageSetAdapter pageSetAdapter = new PageSetAdapter();
        SimpleCommonUtils.addEmojiPageSetEntity(pageSetAdapter, this, emoticonClickListener);
        setAdapter(pageSetAdapter);

        EmojiKeyboardUtils emojiKeyboardUtils = new EmojiKeyboardUtils(this, editText, ilEmoji, issueEmoji, nestedScrollView);
        emojiKeyboardUtils.setEmoticonPanelVisibilityChangeListener(new EmojiKeyboardUtils.OnEmojiPanelVisibilityChangeListener() {
            @Override
            public void onShowEmojiPanel() {
            }

            @Override
            public void onHideEmojiPanel() {
            }
        });
    }

    public void setAdapter(PageSetAdapter pageSetAdapter) {
        if (pageSetAdapter != null) {
            ArrayList<PageSetEntity> pageSetEntities = pageSetAdapter.getPageSetEntityList();
            if (pageSetEntities != null) {
                for (PageSetEntity pageSetEntity : pageSetEntities) {
                    mEmoticonsToolBarView.addToolItemView(pageSetEntity);
                }
            }
        }
        mEmoticonsFuncView.setAdapter(pageSetAdapter);
    }

    public void openPhoto() {
        PictureSelector.create(this).openGallery(PictureMimeType.ofAll())
                .selectionMode(PictureConfig.MULTIPLE).previewImage(true).previewVideo(true).maxSelectNum(9).sizeMultiplier(0.5f)
                .isCamera(false).isGif(true).isZoomAnim(true).compress(true).previewEggs(true).minimumCompressSize(100).selectionMedia(localMediaList)
                .synOrAsy(false).forResult(PictureConfig.CHOOSE_REQUEST);
    }


    @OnClick({R.id.issue_photo, R.id.tv_titleBar_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.issue_photo:
                openPhoto();
                break;
            case R.id.tv_titleBar_send:
                if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                    ToastUtils.showShort("请输入内容");
                } else {
                    ToastUtils.showShort("发送成功--" + type);
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null && requestCode == PictureConfig.CHOOSE_REQUEST) {
                List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
                localMediaList.clear();
                localMediaList.addAll(images);
                if (images != null && images.size() > 0) {
                    if (images.get(0).getPictureType().contains("video")) {
                        type = "video";
                        recyclerView.setVisibility(View.GONE);
                        jzvdStd.setVisibility(View.VISIBLE);
                        String videoUrl;
                        if (images.get(0).isCompressed()) {
                            videoUrl = images.get(0).getCompressPath();
                        } else {
                            videoUrl = images.get(0).getPath();
                        }
                        jzvdStd.setUp(videoUrl, "", Jzvd.SCREEN_WINDOW_NORMAL);
                        SystemUtils.loadVideoScreenshot(mContext, videoUrl, jzvdStd.thumbImageView, 1);
                        jzvdStd.setMediaInterface(new JZExoPlayer());  //exo
                    } else {
                        type = "image";
                        jzvdStd.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        articleIssueAdapter.notifyDataSetChanged();
                    }
                } else {
                    jzvdStd.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void emoticonSetChanged(PageSetEntity pageSetEntity) {
        mEmoticonsToolBarView.setToolBtnSelect(pageSetEntity.getUuid());
    }

    @Override
    public void playTo(int position, PageSetEntity pageSetEntity) {
        mEmoticonsIndicatorView.playTo(position, pageSetEntity);
    }

    @Override
    public void playBy(int oldPosition, int newPosition, PageSetEntity pageSetEntity) {
        mEmoticonsIndicatorView.playBy(oldPosition, newPosition, pageSetEntity);
    }

    @Override
    public void onToolBarItemClick(PageSetEntity pageSetEntity) {
        mEmoticonsFuncView.setCurrentPageSet(pageSetEntity);
    }
}
