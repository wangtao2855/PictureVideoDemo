package com.example.home.picturevideodemo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.home.picturevideodemo.R;

import java.util.List;

public class ScrollRvAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ScrollRvAdapter(List<String> data) {
        super(R.layout.adapter_scroll_rv, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_textView, item);
    }
}
