package com.demo.maat.hello_rxjava;

import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.demo.maat.hello_rxjava.common.logger.Log;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.OnErrorThrowable;
import rx.functions.Func0;
import rx.subscriptions.CompositeSubscription;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;


public class RxAndroidFragment extends Fragment {


    static final String TAG = "RxAndroidFragment";
    @BindView(R.id.btn_rxandroid)
    Button mBtnPolling;
    @BindView(R.id.progress_operation_two_running)
    ProgressBar mProgressOperationTwoRunning;

    private Looper backgroundLooper;


    private Subscription subscribe;
    private CompositeSubscription mCompositeSubscription;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rxandroid_fragment, container, false);
        ButterKnife.bind(this, view);
        mCompositeSubscription = new CompositeSubscription();

        BackgroundThread backgroundThread = new BackgroundThread();
        backgroundThread.start();
        backgroundLooper = backgroundThread.getLooper();

        return view;
    }


    @OnClick(R.id.btn_rxandroid)
    public void onClick() {
        onRunSchedulerExampleButtonClicked();
    }

    void onRunSchedulerExampleButtonClicked() {

        sampleObservable()
                // Run on a background thread
                .subscribeOn(AndroidSchedulers.from(backgroundLooper))
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        printLog("Completed");
                        mProgressOperationTwoRunning.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        printLog("Error");
                        mProgressOperationTwoRunning.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onNext(String string) {
                        mProgressOperationTwoRunning.setVisibility(View.INVISIBLE);
                        printLog("Next " + string);
                    }
                });
    }

    Observable<String> sampleObservable() {
        mProgressOperationTwoRunning.setVisibility(View.VISIBLE);

        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                try {
                    // Do some long running operation
                    Thread.sleep(TimeUnit.SECONDS.toMillis(5));
                } catch (InterruptedException e) {
                    throw OnErrorThrowable.from(e);
                }
                return Observable.just("one", "two", "three", "four", "five");
            }
        });
    }

    private void printLog(String s) {
        Log.i(TAG, s);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }


    static class BackgroundThread extends HandlerThread {
        BackgroundThread() {
            super("SchedulerSample-BackgroundThread", THREAD_PRIORITY_BACKGROUND);
        }
    }
}




