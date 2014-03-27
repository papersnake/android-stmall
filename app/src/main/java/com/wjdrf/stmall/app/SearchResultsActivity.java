package com.wjdrf.stmall.app;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wjdrf.stmall.app.services.Good;
import com.wjdrf.stmall.app.services.StmallServices;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.wjdrf.stmall.app.authenticator.StmallConstants.Extra.SEARCH_KEY;

public class SearchResultsActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        ActionBar actionBar=getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        String searchKey=null;
        if(intent !=null) {
            searchKey=intent.getStringExtra(SEARCH_KEY);
        }

        GoodListFragment fragment= new GoodListFragment(searchKey);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_results, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class GoodListFragment extends ListFragment {
        @InjectView(R.id.txt_searchkey) TextView txt_SearchKey;

        private String query;
        private String searchKey;
        ListView mListView;

        public GoodListFragment(String search_key) {
            searchKey=search_key;
            //mLis
        }

        Callback<List<Good>> callback= new Callback<List<Good>>() {
            @Override
            public void success(List<Good> goods, Response response) {
                ArrayAdapter<Good> adapter = new ArrayAdapter<Good>(getActivity().getApplicationContext(),
                        android.R.layout.simple_list_item_1,goods);
                getListView().setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        };


        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            if(Intent.ACTION_SEARCH.equals(getActivity().getIntent().getAction())) {
                query = getActivity().getIntent().getStringExtra(SearchManager.QUERY);
                txt_SearchKey.setText(query);

                StmallServices services=new StmallServices(getActivity());

                services.getGoodSearchResult(query,callback);

            }
        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_good_list, container, false);
            ButterKnife.inject(this, rootView);


            return rootView;
        }
    }
}
