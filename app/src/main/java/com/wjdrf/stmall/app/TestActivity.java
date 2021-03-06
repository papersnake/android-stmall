package com.wjdrf.stmall.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.wjdrf.stmall.app.services.Good;
import com.wjdrf.stmall.app.services.StmallServices;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.wjdrf.stmall.app.authenticator.StmallConstants.Extra.GOOD_ITEM;
import static com.wjdrf.stmall.app.authenticator.StmallConstants.Extra.GOOD_OBJ;

import com.wjdrf.stmall.app.authenticator.StmallConstants.Action;

public class TestActivity extends Activity {

    @InjectView(R.id.txt_good_name)
    TextView txtGoodName;

    @InjectView(R.id.txt_yuanjia)
    TextView txtYuanJia;

    @InjectView(R.id.txt_jinjia)
    TextView txtJinJia;

    @InjectView(R.id.txt_tejia)
    TextView txtTeJia;

    @InjectView(R.id.txt_goodspec)
    TextView txtGoodSpec;

    @InjectView(R.id.txt_untis)
    TextView txtUnits;

    @InjectView(R.id.txt_qty)
    TextView txtQTY;

    @InjectView(R.id.txt_belong)
    TextView txtBelong;

    @InjectView(R.id.txt_codebar)
    TextView txtCodebar;

    @InjectView(R.id.txt_goodid)
    TextView txtGoodId;

    private ProgressDialog dialog;

    public void fillData(Good good){
        if(dialog.isShowing()){
            dialog.dismiss();

        }
        if(good!=null) {
            setTitle(good.getGood_name());
            txtGoodName.setText(good.getGood_name());
            txtYuanJia.append(good.getGood_price().toString());
            txtJinJia.append(good.getGood_pur_price().toString());
            if(good.getGood_tj_price()!=null) {
                txtTeJia.append(good.getGood_tj_price().toString());
            }else{
                txtTeJia.setVisibility(View.GONE);
            }
            txtCodebar.append(good.getBarcode());
            txtGoodId.append(String.valueOf(good.getGood_id()));

            txtGoodSpec.append(good.getGood_spec());
            txtUnits.append(good.getUnits());
            txtQTY.append(String.valueOf(good.getGood_num()));
            txtBelong.append(good.getGood_belong());
        }
    }

    Callback<Good> callback = new Callback<Good>() {
        @Override
        public void success(Good good, Response response) {
            if(good!=null) {
                fillData(good);
            }else{
                if(dialog.isShowing())
                    dialog.dismiss();
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


        dialog = ProgressDialog.show(this,"加载中","正在加载信息，请稍候...");
        dialog.setCancelable(true);

        ActionBar actionBar = getActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        if(intent.getAction()!=null && intent.getAction().equals(Action.ACTION_GOOD_VIEW)){
            Good good = (Good) intent.getExtras().getSerializable(GOOD_OBJ);
            fillData(good);
        }else{
            String codebar = intent.getStringExtra(GOOD_ITEM);
            StmallServices services = new StmallServices(this);
            services.getGood(codebar, callback);
        }

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
        //int id = item.getItemId();
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
