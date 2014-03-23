package com.wjdrf.stmall.app.services;

/**
 * Created by papersnake on 14-3-23.
 */

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

public class StmallServices {

    private static final String API_URL="http://stmall.sinaapp.com";

    interface Stmall {
        @GET("/Api/Good/{barcode}")
        void getGood(@Path("barcode") String barcode, Callback<Good> callback);
    }

    public static void getGood(Callback<Good> callback) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(API_URL).build();

        Stmall stmall = restAdapter.create(Stmall.class);

        stmall.getGood("6902890234418",callback);
    }
}
