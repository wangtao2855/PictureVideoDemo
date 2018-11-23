package com.example.home.picturevideodemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.home.picturevideodemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GreenDaoActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bt_net)
    Button btNet;
    @BindView(R.id.bt_zhanshi)
    Button btZhanshi;
    @BindView(R.id.tv_textView)
    TextView tvTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back,R.id.bt_net,R.id.bt_zhanshi})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_net:
                break;
            case R.id.bt_zhanshi:
                break;
        }
    }
}
