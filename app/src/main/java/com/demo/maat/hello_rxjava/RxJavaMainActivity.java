package com.demo.maat.hello_rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RxJavaMainActivity extends AppCompatActivity {


    @BindView(R.id.btn_scheduler)
    Button mBtnScheduler;
    @BindView(R.id.btn_operators1)
    Button mBtnOperators1;
    @BindView(R.id.btn_operators2)
    Button mBtnOperators2;
    @BindView(R.id.btn_operators3)
    Button mBtnOperators3;
    @BindView(R.id.btn_operators4)
    Button mBtnOperators4;
    @BindView(R.id.activity_main)
    LinearLayout mActivityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rxjava_activity_main);
        ButterKnife.bind(this);
    }




    @OnClick({R.id.btn_scheduler,R.id.btn_operators1, R.id.btn_operators2, R.id.btn_operators3, R.id.btn_operators4, R.id.activity_main})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_scheduler:
                Intent intent = new Intent(this, SchedulerActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_operators1:
                break;
            case R.id.btn_operators2:
                break;
            case R.id.btn_operators3:
                break;
            case R.id.btn_operators4:
                break;
            case R.id.activity_main:
                break;
        }
    }
}
