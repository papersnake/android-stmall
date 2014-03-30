package com.wjdrf.stmall.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.wjdrf.stmall.app.services.Good;
import com.wjdrf.stmall.app.services.StmallServices;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.wjdrf.stmall.app.authenticator.StmallConstants.Extra.GOOD_ITEM;

public class TestActivity extends Activity {

    @InjectView(R.id.txt_good_name)
    TextView txtGoodName;

    Callback<Good> callback = new Callback<Good>() {
        @Override
        public void success(Good good, Response response) {
            if(good!=null) {
                txtGoodName.setText(good.getGood_name());
            }else{
                txtGoodName.setText(R.string.empty);
            }
        }

        @Override
        public void failure(RetrofitError error) {
            //txtGoodName.setText(error.getMessage());
            Log.d("TEST", error.getUrl());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        String codebar = intent.getStringExtra(GOOD_ITEM);
        StmallServices services = new StmallServices(this);
        services.getGood(codebar, callback);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
