package com.demo.maat.hello_rxjava;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.subscriptions.CompositeSubscription;


public class Operators1Fragment extends Fragment {


    static final String TAG = "Operators1Fragment";
    @BindView(R.id.btn_just)
    Button mBtnJust;
    @BindView(R.id.btn_from)
    Button mBtnFrom;
    @BindView(R.id.btn_repeat)
    Button mBtnRepeat;
    @BindView(R.id.btn_repeatwhen)
    Button mBtnRepeatwhen;
    @BindView(R.id.btn_interval)
    Button mBtnInterval;
    @BindView(R.id.btn_timer)
    Button mBtnTimer;
    @BindView(R.id.btn_range)
    Button mBtnRange;


    private CompositeSubscription mCompositeSubscription;
    ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.operators1_fragment, container, false);
        ButterKnife.bind(this, view);
        mCompositeSubscription = new CompositeSubscription();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @OnClick({R.id.btn_just, R.id.btn_from, R.id.btn_repeat, R.id.btn_repeatwhen, R.id.btn_interval, R.id.btn_timer, R.id.btn_range})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_just:
                break;
            case R.id.btn_from:
                break;
            case R.id.btn_repeat:
                break;
            case R.id.btn_repeatwhen:
                break;
            case R.id.btn_interval:
                break;
            case R.id.btn_timer:
                break;
            case R.id.btn_range:
                break;
        }
    }
}




