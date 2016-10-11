package com.demo.maat.hello_rxjava;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.demo.maat.hello_rxjava.common.logger.Log;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class PollingFragment extends Fragment {


    static final String TAG = "PollingFragment";
    @BindView(R.id.btn_polling)
    Button mBtnPolling;

    int N=0;
    private Subscription subscribe;
    private CompositeSubscription mCompositeSubscription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.polling_fragment, container, false);
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


    @OnClick(R.id.btn_polling)
    public void onClick() {

        //此处有bug,subscribe无法释放,望高人pull requests
        subscribe=Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> observer) {

                Schedulers.newThread().createWorker()
                        .schedulePeriodically(new Action0() {
                            @Override
                            public void call() {
                                observer.onNext(" "+(N++));
                            }
                        }, 0, 1000, TimeUnit.MILLISECONDS);
            }
        })
                .subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
            printLog("polling"+s);
            }
        });
        mCompositeSubscription.add(subscribe);
    }
    private void printLog(String s) {
        Log.i(TAG, s);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }


}




