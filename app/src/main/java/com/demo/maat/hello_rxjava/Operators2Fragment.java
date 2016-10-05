package com.demo.maat.hello_rxjava;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.demo.maat.hello_rxjava.common.logger.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class Operators2Fragment extends Fragment {


    static final String TAG = "Operators2Fragment";
    @BindView(R.id.btn_buffer)
    Button mBtnBuffer;
    @BindView(R.id.btn_flarmap)
    Button mBtnFlarmap;
    @BindView(R.id.btn_concatmap)
    Button mBtnConcatmap;
    @BindView(R.id.btn_switchmap)
    Button mBtnSwitchmap;


    private CompositeSubscription mCompositeSubscription;
    Subscription mSubscription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.operators2_fragment, container, false);
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



    @OnClick({R.id.btn_buffer, R.id.btn_flarmap, R.id.btn_concatmap, R.id.btn_switchmap})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_buffer:
                doBufferOperation();
                break;
            case R.id.btn_flarmap:
                doflatMap();
                break;
            case R.id.btn_concatmap:
                doConcatmap();
                break;
            case R.id.btn_switchmap:
                doSwitchmap();
                break;
        }
    }

    private void doSwitchmap() {
        Observable.just(10, 20, 30).switchMap(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer integer) {
                //10的延迟执行时间为200毫秒、20和30的延迟执行时间为180毫秒
                int delay = 200;
                if (integer > 10)
                    delay = 180;

                return Observable.from(new Integer[]{integer, integer / 2}).delay(delay, TimeUnit.MILLISECONDS);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                printLog("Switch Next"+integer);
            }
        });
    }

    //与flatmap大致相同,只是产生的顺序不同;
    private void doConcatmap() {
        Observable.just(10, 20, 30).concatMap(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer integer) {
                //10的延迟执行时间为200毫秒、20和30的延迟执行时间为180毫秒
                int delay = 200;
                if (integer > 10)
                    delay = 180;

                return Observable.from(new Integer[]{integer, integer / 2}).delay(delay, TimeUnit.MILLISECONDS);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                printLog("Concat Next"+integer);
            }
        });

    }

    //与map一样,都是对数据转化,map是一对一,flatmap一对多;
    private void doflatMap() {
        Bean bean=new Bean();
        Observable.just(bean)
                .flatMap(new Func1<Bean, Observable<String>>() {
                    @Override
                    public Observable<String> call(Bean bean) {
                        return Observable.from(bean.getData());
                    }

                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        printLog("Flat Next"+s);
                    }
                });

    }

    //一共发送10次信息,Observable每次缓存3秒一起发送
    private void doBufferOperation() {
        mSubscription=Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 0; i <10   ; i++) {
                    subscriber.onNext(" "+i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                 .buffer(3,TimeUnit.SECONDS)
                 .subscribe(new Action1<List<String>>() {
                     @Override
                     public void call(List<String> list) {
                         printLog("接收到 "+list.size()+"次");
                         for (int i = 0; i < list.size(); i++){
                             printLog(list.get(i));
                         }

                     }
                 });
        mCompositeSubscription.add(mSubscription);


    }

    private class Bean{
        String[] data={"1","2","3"};

        public String[] getData(){
            return data;
        }
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




