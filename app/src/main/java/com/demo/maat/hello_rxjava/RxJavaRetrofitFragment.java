package com.demo.maat.hello_rxjava;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.demo.maat.hello_rxjava.common.logger.Log;
import com.demo.maat.hello_rxjava.retrofit.zhihu.ZhihuDaily;
import com.demo.maat.hello_rxjava.retrofit.zhihu.ZhihuDailyItem;
import com.demo.maat.hello_rxjava.retrofit.zhihu.ZhihuManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class RxJavaRetrofitFragment extends Fragment {


    static final String TAG = "RxJavaRetrofitFragment";
    @BindView(R.id.btn_get)
    Button mBtnGet;
    private Subscription subscription;

    private CompositeSubscription mCompositeSubscription;
    ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.retrofit_fragment, container, false);
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
    @OnClick(R.id.btn_get)
    public void onClick() {

       subscription= ZhihuManager.getInstance()
                .getZhihuApiService()
                .getLastDaily()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhihuDaily>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ZhihuDaily daily) {
                        //get first bean
                        ZhihuDailyItem item=daily.getStories().get(0);
                        printLog("Title:"+item.getTitle());
                        printLog("Data:  "+item.getDate());
                        printLog("Id:    "+item.getId());
                        printLog("Type:  "+item.getType());


                    }
                });
        mCompositeSubscription.add(subscription);

    }

    private void printLog(String s) {
        Log.i(TAG, s );
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }



}


