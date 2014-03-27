package com.wjdrf.stmall.app;

import android.app.Application;

/**
 * Created by papersnake on 14-3-26.
 */
public class StmallApp extends Application {
    private String str_token;
    public void setToken(String token) {
        str_token=token;
    }

    public String getToken(){
        return str_token;
    }
}
