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
import rx.functions.Func1;
import rx.functions.Func2;
import rx.subscriptions.CompositeSubscription;

import static rx.Observable.timer;


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
                doJustOperation();
                break;
            case R.id.btn_from:
                doFromOperation();
                break;
            case R.id.btn_repeat:
                doRepeatOperation();
                break;
            case R.id.btn_repeatwhen:
                doRepeatWhenOperation();
                break;
            case R.id.btn_interval:
                doIntervalOperation();
                break;
            case R.id.btn_timer:
                doTimerOperation();
                break;
            case R.id.btn_range:
                doRangeOperation();
                break;
        }
    }

    /**
     * 从3开始连续产生10个数字
     */

    private void doRangeOperation() {
        Subscription rangeSub =Observable.range(3,10).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
            printLog("Completed");
            }

            @Override
            public void onError(Throwable e) {
            printLog("Error");
            }

            @Override
            public void onNext(Integer i) {
            printLog("Next"+i);
            }
        });
        mCompositeSubscription.add(rangeSub);

    }

    /**
     * 每隔1秒输出一个数字
     */
    private void doTimerOperation() {

        Subscription timerSub=Observable.timer(1, 1, TimeUnit.SECONDS).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                printLog("Completed");
            }

            @Override
            public void onError(Throwable e) {
                printLog("Error");
            }

            @Override
            public void onNext(Long aLong) {
                printLog("Next" + aLong);
            }
        });
        mCompositeSubscription.add(timerSub);
    }

    private void doIntervalOperation() {
        Subscription intervalSub=Observable.interval(1, TimeUnit.SECONDS).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                printLog("Completed");

            }

            @Override
            public void onError(Throwable e) {
                printLog("Error");

            }

            @Override
            public void onNext(Long aLong) {
                printLog("Next" + aLong);
            }
        });
        mCompositeSubscription.add(intervalSub);
    }

    /**
     * 完成一次之后,再每隔1秒重复1次,共重复3次。
     */

    private void doRepeatWhenOperation() {
        Observable.just(1, 2, 3).repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Void> observable) {
                //重复3次
                return observable.zipWith(Observable.range(1, 3), new Func2<Void, Integer, Integer>() {
                    @Override
                    public Integer call(Void aVoid, Integer integer) {
                        return integer;
                    }
                }).flatMap(new Func1<Integer, Observable<?>>() {
                    @Override
                    public Observable<?> call(Integer integer) {
                        printLog("delay repeat" + integer);
                        //1秒钟重复一次
                        return timer(1, TimeUnit.SECONDS);
                    }
                });
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                printLog("Completed");
            }

            @Override
            public void onError(Throwable e) {
                printLog("Error");
            }

            @Override
            public void onNext(Integer value) {
                printLog("Next" + value);
            }
        });
    }
    // 从1开始输出3个数,重复2次
    private void doRepeatOperation() {
        Observable.range(1, 3).repeat(2).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                printLog("Completed");
            }

            @Override
            public void onError(Throwable e) {
                printLog("Error");
            }

            @Override
            public void onNext(Integer i) {
                printLog("Nest" + i);
            }
        });
    }

    /**
     * 接受一个数组,与just的区别就是接受的参数不同
     */
    private void doFromOperation() {
        Integer[] items = {0, 1, 2, 3, 4, 5};
        Observable from = Observable.from(items);

        from.subscribe(
                new Action1<Integer>() {
                    @Override
                    public void call(Integer item) {
                        printLog("Next" + item);
                    }
                },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable error) {
                        printLog("Error");
                    }
                },
                new Action0() {
                    @Override
                    public void call() {
                        printLog("Compeleted");
                    }
                }
        );

    }

    private void doJustOperation() {
        Subscription subscription = Observable.just(1, 2, 3, 4, 5)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onNext(Integer item) {
                        printLog("Next" + item);
                    }

                    @Override
                    public void onError(Throwable error) {
                        printLog("Error");
                    }

                    @Override
                    public void onCompleted() {
                        printLog("Completed");
                    }
                });
        mCompositeSubscription.add(subscription);
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




