package com.demo.maat.hello_rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Nullable @BindView(R.id.btn_rxjava)
    Button mBtnRxjava;
    @BindView(R.id.btn_retrofit)
    Button mBtnRetrofit;
    @BindView(R.id.btn_rxandroid)
    Button mBtnRxAndroid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.btn_rxjava, R.id.btn_retrofit,R.id.btn_rxandroid})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_rxjava:
                Intent intent=new Intent(MainActivity.this,RxJavaMainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_retrofit:
                Intent retrofit=new Intent(MainActivity.this,RxJavaRetrofitActivity.class);
                startActivity(retrofit);
                break;
            case R.id.btn_rxandroid:
                Intent rxandroid=new Intent(MainActivity.this,RxAndroidActivity.class);
                startActivity(rxandroid);
                break;
        }
    }
}
