package com.demo.maat.hello_rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.demo.maat.hello_rxjava.common.logger.Log;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;


public class DebounceEditTextFragment extends Fragment {


    static final String TAG = "DebounceEditTextFragment";

    @BindView(R.id.edit_search)
    EditText mEditSearch;
    int N=0;
    private Subscription subscribe;
    private CompositeSubscription mCompositeSubscription;
    ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.debounce_fragment, container, false);
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



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        subscribe= RxTextView.textChangeEvents(mEditSearch)//
                .debounce(400, TimeUnit.MILLISECONDS)// default Scheduler is Computation
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Observer<TextViewTextChangeEvent>() {
                               @Override
                               public void onCompleted() {
                                   printLog("Completed");
                               }

                               @Override
                               public void onError(Throwable e) {
                                   printLog("Error");
                               }

                               @Override
                               public void onNext(TextViewTextChangeEvent onTextChangeEvent) {
                                printLog(onTextChangeEvent.text().toString());
                               }
                           }
                );
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




