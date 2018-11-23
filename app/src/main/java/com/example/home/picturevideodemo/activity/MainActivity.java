package com.example.home.picturevideodemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.home.picturevideodemo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt1, R.id.bt2, R.id.bt3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                startActivity(new Intent(getApplicationContext(), PictureVideoActivity.class));
                break;
            case R.id.bt2:
                break;
            case R.id.bt3:
                startActivity(new Intent(getApplicationContext(), ScrollRvActivity.class));
                break;
        }
    }
}
