package com.wjdrf.stmall.app;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.util.Lists;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.wjdrf.stmall.app.authenticator.OAuth;
import com.wjdrf.stmall.app.authenticator.StmallConstants;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.wjdrf.stmall.app.authenticator.StmallConstants.Extra.GOOD_ITEM;

public class MainActivity extends FragmentActivity {

    private static final String FRA_TAG="main_fragment";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //ButterKnife.inject(this);

        FragmentManager fm = getSupportFragmentManager();

        if(fm.findFragmentById(android.R.id.content) == null ){
            OAuthFragment frg=new OAuthFragment();
            fm.beginTransaction().add(android.R.id.content,frg,FRA_TAG).commit();
        }


        //Credential credential = oauth.authorizeExplicitly("stmall").getResult();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRA_TAG);
        if(fragment!=null) {
            fragment.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView=
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();
        //if (id == R.id.action_settings) {
            //mainText.setText("papersnake");
            //return true;
        //}

        //if(id == R.id.action_search) {
        //    Log.v("MAIN","search buttn click");
        //}
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public static class OAuthFragment extends Fragment implements
            LoaderManager.LoaderCallbacks<Credential> {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);


            if(getLoaderManager().getLoader(0) == null) {
                getLoaderManager().initLoader(0,null,this);
            }
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
            if(scanResult !=null && !TextUtils.isEmpty(scanResult.getContents())) {
                //txt_Oauth_Token.setText(scanResult.getContents());
                Intent intent=new Intent(getActivity(),TestActivity.class);
                intent.putExtra(GOOD_ITEM,scanResult.getContents());
                getActivity().startActivity(intent);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view= inflater.inflate(R.layout.oauth_fragment,container,false);
            ButterKnife.inject(this,view);
            //txt_Oauth_Token.setText("hello fragment");

            return view;
        }




        @OnClick(R.id.btn_scan)
        public void scan() {
            IntentIntegrator.initiateScan(getActivity());
        }

        @Override
        public Loader<Credential> onCreateLoader(int id, Bundle args) {
            getActivity().setProgressBarIndeterminateVisibility(true);
            return new GetTokenLoader(getActivity(),1);
        }

        @Override
        public void onLoadFinished(Loader<Credential> loader, Credential data) {

            StmallApp app=(StmallApp)getActivity().getApplicationContext();
            app.setToken(data.getAccessToken());
            //txt_Oauth_Token.setText(data.getAccessToken());
            getActivity().setProgressBarIndeterminateVisibility(false);
        }

        @Override
        public void onLoaderReset(Loader<Credential> loader) {

        }
    }

    public static class GetTokenLoader extends AsyncTaskLoader<Credential> {

        private final OAuth oauth;
        Credential credential;
        //public GetTokenLoader(Context context) {
        //    super(context);
        //}

        public GetTokenLoader(FragmentActivity activity,int since) {
            super(activity);
            this.oauth = OAuth.newInstance(activity.getApplicationContext(),
                    activity.getSupportFragmentManager(),
                    new ClientParametersAuthentication(StmallConstants.CLIENT_ID,StmallConstants.CLIENT_SECRET),
                    StmallConstants.AUTHORIZATION_CODE_SERVER_URL,
                    StmallConstants.TOKEN_SERVER_URL,
                    StmallConstants.REDIRECT_URL,
                    Lists.<String> newArrayList());
        }

        @Override
        protected void onStartLoading() {
            if(credential != null) {
                deliverResult(credential);
            }else{
                forceLoad();
            }
        }

        @Override
        public Credential loadInBackground() {
            //Credential credential = null;
            try {
                credential = oauth.authorizeExplicitly(
                        getContext().getString(R.string.token_stmall)).getResult();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return credential;
        }
    }

}
