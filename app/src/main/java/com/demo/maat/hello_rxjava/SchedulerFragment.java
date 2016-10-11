package com.demo.maat.hello_rxjava;

import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.demo.maat.hello_rxjava.common.logger.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class SchedulerFragment extends Fragment {


    static final String TAG = "SchedulerFragment";

    String[] data = {"1", "2", "3"};
    @BindView(R.id.btn_no_map)
    Button mBtnNoMap;
    @BindView(R.id.btn_just)
    Button mBtnJust;
    @BindView(R.id.btn_just_one)
    Button mBtnJustone;
    @BindView(R.id.btn_just_two)
    Button mBtnJusttwo;
    @BindView(R.id.btn_just_three)
    Button mBtnJustthree;
    @BindView(R.id.btn_just_four)
    Button mBtnJustfour;
    @BindView(R.id.btn_just_five)
    Button mBtnJustfive;
    @BindView(R.id.btn_just_six)
    Button mBtnJustsix;
    @BindView(R.id.btn_one_map)
    Button mBtnOneMap;
    @BindView(R.id.btn_two_map)
    Button mBtnTwoMap;
    @BindView(R.id.btn_long_operation)
    Button mBtnLongOperation;
    @BindView(R.id.progress_operation_running)
    ProgressBar mProgressOperationRunning;
    @BindView(R.id.progress_operation_two_running)
    ProgressBar mProgressOperationTwoRunning;
    private Subscription baseSubscription;
    private Subscription oneMapSubscription;
    private Subscription twoMapSubscription;
    private Subscription longOpeSubscription;
    private CompositeSubscription mCompositeSubscription;
    ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scheduler_fragment, container, false);
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


    @OnClick({R.id.btn_just, R.id.btn_just_one,  R.id.btn_just_two,
            R.id.btn_just_three,R.id.btn_just_four, R.id.btn_just_five,
            R.id.btn_just_six,R.id.btn_no_map, R.id.btn_one_map, R.id.btn_two_map, R.id.btn_long_operation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_just:
                doJust();
                break;
            case R.id.btn_just_one:
                doJustOne();
                break;
            case R.id.btn_just_two:
                doJustTwo();
                break;
            case R.id.btn_just_three:
                doJustThree();
                break;
            case R.id.btn_just_four:
                doJustFour();
                break;
            case R.id.btn_just_five:
                doJustFive();
                break;
            case R.id.btn_just_six:
                doJustSix();
                break;
            case R.id.btn_no_map:
                doBaseOperation();
                break;
            case R.id.btn_one_map:
                doOnemapOperation();
                break;
            case R.id.btn_long_operation:
                doLongOperation();
                break;
            case R.id.btn_two_map:
                doTwomapWithLongOperation();
                break;
        }
    }



    private void doJust() {
        Observable.just(1, 2, 3)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        printLog("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        printLog("onError");

                    }

                    @Override
                    public void onNext(Integer integer) {
                        printLog("Next " + integer + " ");
                    }
                });
    }






    private void doJustOne() {
        Observable.just(1, 2, 3)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        printLog("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        printLog("onError");

                    }

                    @Override
                    public void onNext(Integer integer) {
                        printLog("Next " + integer + " ");
                    }
                });
    }




    private void doJustTwo() {
        Observable.just(1, 2, 3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        printLog("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        printLog("onError");

                    }

                    @Override
                    public void onNext(Integer integer) {
                        printLog("Next " + integer + " ");
                    }
                });
    }





    private void doJustThree() {
        Observable.just(1, 2, 3)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        printLog("map "+integer+"a");
                        return integer+"a";
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        printLog("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        printLog("onError");

                    }

                    @Override
                    public void onNext(String s) {
                        printLog("Next " +s);

                    }

                });
    }
    private void doJustFour() {
        Observable.just(1, 2, 3)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        printLog("map1 "+integer+"a");
                        return integer+"a";
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        printLog("map2 "+s+"b ");
                        return s+"b";
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        printLog("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        printLog("onError");

                    }

                    @Override
                    public void onNext(String s) {
                        printLog("Next " +s);

                    }

                });
    }










    private void doJustFive() {
        Observable.just(1, 2, 3)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        printLog("map1 "+integer+"a");
                        return integer+"a";
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        printLog("map2 "+s+"b ");
                        return s+"b";
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        printLog("map3 "+s+"c ");
                        return s+"c";
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        printLog("Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        printLog("onError");

                    }

                    @Override
                    public void onNext(String s) {
                        printLog("Next " +s);

                    }

                });
    }





    private void doJustSix() {
        Observable.just(1, 2, 3)
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        printLog("map1 "+integer+"a");
                        return integer+"a";
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        printLog("map2 "+s+"b ");
                        return s+"b";
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        printLog("map3 "+s+"c ");
                        return s+"c";
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        printLog("Completed");
                    }
                    @Override
                    public void onError(Throwable e) {
                        printLog("onError");

                    }
                    @Override
                    public void onNext(String s) {
                        printLog("Next " +s);

                    }

                });
    }


    /**
     * Subscriber加Onsubscribe基本使用,没有线程切换,没有过滤等操作
     *
     */

    private void doBaseOperation() {
        baseSubscription = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                printLog("onStart in OnSubscribe");
                subscriber.onStart();
                int N = data.length;
                for (int i = 0; i < N; i++) {
                    printLog("onNext " + data[i] + " in OnSubscribe");
                    subscriber.onNext(data[i]);

                }
                printLog("OnCompleted in OnSubscribe");
                subscriber.onCompleted();

            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                printLog("OnCompleted in Subscriber");


            }

            @Override
            public void onError(Throwable e) {
                printLog("onError in Subscriber");

            }

            @Override
            public void onNext(String s) {
                printLog("onNext " + s + " in Subscriber");

            }
        });
        mCompositeSubscription.add(baseSubscription);

    }

    /**
     * 对数据做一次map操作,给每一个数据后面加一个"a",没有线程调度;
     */
    private void doOnemapOperation() {
        oneMapSubscription = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                printLog("onStart in OnSubscribe");
                subscriber.onStart();
                int N = data.length;
                for (int i = 0; i < N; i++) {
                    printLog("onNext " + data[i] + " in OnSubscribe");
                    subscriber.onNext(data[i]);
                }
                printLog("OnCompleted in OnSubscribe");
                subscriber.onCompleted();

            }
        }).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                String result = s + "a";
                printLog(result);
                return result;
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                printLog("OnCompleted in Subscriber");


            }

            @Override
            public void onError(Throwable e) {
                printLog("onError in Subscriber");

            }

            @Override
            public void onNext(String s) {
                printLog("onNext " + s + " in Subscriber");

            }
        });
        mCompositeSubscription.add(oneMapSubscription);
    }


    /**
     * 模拟阻操作,如网络请求,文件读取,再加上scheduler,切换线程。
     */
    private void doLongOperation() {
        mProgressOperationRunning.setVisibility(View.VISIBLE);
        longOpeSubscription = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                printLog("onStart in OnSubscribe");
                subscriber.onStart();
                int N = data.length;
                for (int i = 0; i < N; i++) {
                    dosomethingBlockThread();
                    printLog("onNext" + data[i] + " in OnSubscribe");
                    subscriber.onNext(data[i]);

                }
                printLog("OnCompleted in OnSubscribe");
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        printLog("OnCompleted in Subscriber");
                        mProgressOperationRunning.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        printLog("onError in Subscriber");
                        mProgressOperationRunning.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onNext(String s) {
                        printLog("onNext " + s + " in Subscriber");

                    }
                });
        mCompositeSubscription.add(longOpeSubscription);
    }

    /**
     * 对数据进行两次map操作并加上线程调度,第一次map操作每个数据后面+"a",第二次map操作每个数据后面+"b".
     */
    private void doTwomapWithLongOperation() {
        mProgressOperationTwoRunning.setVisibility(View.VISIBLE);
        twoMapSubscription = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                printLog("onStart in OnSubscribe");
                subscriber.onStart();
                int N = data.length;
                for (int i = 0; i < N; i++) {
                    dosomethingBlockThread();
                    printLog("onNext " + data[i] + " in OnSubscribe");
                    subscriber.onNext(data[i]);

                }
                printLog("OnCompleted in OnSubscribe");
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        String result = s + "a";
                        printLog("Map1 " + result);
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        String result = s + "b";
                        printLog("Map2 " + result);
                        return result;
                    }
                })
//                你可以把上面的注释了,使用这个.observeOn(AndroidSchedulers.mainThread())看看map2有什么变化
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        printLog("OnCompleted in Subscriber");
                        mProgressOperationTwoRunning.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        printLog("onError in Subscriber");
                        mProgressOperationTwoRunning.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onNext(String s) {
                        printLog("onNext " + s + " in Subscriber");

                    }
                });
        mCompositeSubscription.add(twoMapSubscription);
    }

    private void dosomethingBlockThread() {
        printLog("blocking....");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void printLog(String s) {
        Log.i(TAG, s + getThreadMessage());
    }

    private String getThreadMessage() {
        if (Looper.myLooper() == Looper.getMainLooper())
            return " MainThread";
        else return " Not-MainThread";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }


}


