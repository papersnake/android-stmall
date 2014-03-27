package com.wjdrf.stmall.app.services;

/**
 * Created by papersnake on 14-3-23.
 */

import android.content.Context;

import com.wjdrf.stmall.app.StmallApp;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

public class StmallServices {

    private static final String API_URL="http://stmall.sinaapp.com";
    private RestAdapter restAdapter;
    private Stmall stmall;

    interface Stmall {
        //@Headers("Accept:text/html,application/xhtml+xml,*/*")
        @GET("/Api/Good/{barcode}")
        void getGood(@Path("barcode") String barcode, Callback<Good> callback);

        @GET("/Api/Good/search/{searchkey}")
        void getGoodSearchResult(@Path("searchkey") String searchKey,Callback<List<Good>> callback);
    }

    public StmallServices(Context context) {
        final StmallApp app = (StmallApp)context.getApplicationContext();
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Accept","text/html,application/xhtml+xml,*/*");
                request.addHeader("Authorization","Bearer " + app.getToken());
            }
        };
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setRequestInterceptor(requestInterceptor)
                .build();

        stmall = restAdapter.create(Stmall.class);

    }

    public void getGood(String codebar,Callback<Good> callback) {
        //RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(API_URL).build();
        stmall.getGood(codebar,callback);
    }

    public void getGoodSearchResult(String searchKey,Callback<List<Good>> callback) {
        stmall.getGoodSearchResult(searchKey,callback);
    }
}
