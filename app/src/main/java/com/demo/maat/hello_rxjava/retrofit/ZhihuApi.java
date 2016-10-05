package com.demo.maat.hello_rxjava.retrofit;


import com.demo.maat.hello_rxjava.retrofit.zhihu.ZhihuDaily;

import retrofit2.http.GET;
import rx.Observable;


public interface ZhihuApi {

    @GET("/api/4/news/latest")
    Observable<ZhihuDaily> getLastDaily();

}
