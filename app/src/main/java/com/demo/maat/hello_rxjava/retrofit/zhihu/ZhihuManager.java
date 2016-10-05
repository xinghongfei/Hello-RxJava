package com.demo.maat.hello_rxjava.retrofit.zhihu;

import com.demo.maat.hello_rxjava.retrofit.ZhihuApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xinghongfei on 16/10/5.
 */

public class ZhihuManager {
    private static ZhihuManager mManager;

    public ZhihuApi zhihuApi;
    public static ZhihuManager getInstance(){

        if (mManager==null){
            mManager= new ZhihuManager();
        }
        return mManager;
    }

    public ZhihuApi getZhihuApiService() {
        if (zhihuApi == null) {
                if (zhihuApi == null) {
                    zhihuApi = new Retrofit.Builder()
                            .baseUrl("http://news-at.zhihu.com")
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(ZhihuApi.class);

            }
        }

        return zhihuApi;
    }

}
