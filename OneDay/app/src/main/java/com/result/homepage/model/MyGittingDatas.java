package com.result.homepage.model;

import com.result.tools.OkHttp;

import java.io.IOException;

import okhttp3.Request;

/**
 * autour: 刘东东
 * date: 2016/12/15 14:05
 * update: 2016/12/15
 */
public class MyGittingDatas implements GettingData{
    @Override
    public void gettingDatas(String url) {
        OkHttp.getAsync(url, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {

            }
        });
    }
}
