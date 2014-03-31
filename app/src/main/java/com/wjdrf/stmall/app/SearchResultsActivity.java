package com.wjdrf.stmall.app;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.devspark.progressfragment.ProgressListFragment;
import com.wjdrf.stmall.app.authenticator.StmallConstants;
import com.wjdrf.stmall.app.services.Good;
import com.wjdrf.stmall.app.services.StmallServices;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.wjdrf.stmall.app.authenticator.StmallConstants.Extra.GOOD_OBJ;

public class SearchResultsActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        ActionBar actionBar=getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        GoodListFragment fragment= new GoodListFragment();


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
        //int id = item.getItemId();
        //if (id == R.id.action_settings) {
        //    return true;
        //}
        //return super.onOptionsItemSelected(item);

        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class GoodAdapter extends ArrayAdapter<Good> {

        private final LayoutInflater mInflater;

        public GoodAdapter(Context context) {
            super(context, R.layout.goot_list_item);
            mInflater = LayoutInflater.from(context);
        }

        public void setData(List<Good> goods){
            addAll(goods);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView ==null) {
                convertView=mInflater.inflate(R.layout.goot_list_item,parent,false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            Good good = getItem(position);

            holder.goodName.setText(good.getGood_name());
            holder.goodCodeBar.setText(good.getBarcode());

            return convertView;
        }

        static class ViewHolder {
            @Optional @InjectView(R.id.txt_good_name) TextView goodName;
            @Optional @InjectView(R.id.txt_goodcodebar) TextView goodCodeBar;

            public ViewHolder(View view) {
                ButterKnife.inject(this,view);
            }
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class GoodListFragment extends ProgressListFragment {

        private String query;

        public GoodListFragment() {
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Good good = ((Good) l.getItemAtPosition(position));

            Intent intent=new Intent(getActivity(),TestActivity.class);
            intent.setAction(StmallConstants.Action.ACTION_GOOD_VIEW);
            intent.putExtra(GOOD_OBJ,good);

            startActivity(intent);
        }

        Callback<List<Good>> callback= new Callback<List<Good>>() {
            @Override
            public void success(List<Good> goods, Response response) {
                if(goods!=null) {
                    //ArrayAdapter<Good> adapter = new ArrayAdapter<Good>(getActivity().getApplicationContext(),
                    //        android.R.layout.simple_list_item_1, goods);
                    GoodAdapter adapter = new GoodAdapter(getActivity());
                    adapter.setData(goods);
                    getListView().setAdapter(adapter);
                    setListShown(true);
                }else{
                    setListShown(true);
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        };


        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            setEmptyText(R.string.empty);
            setListShown(false);
            if(Intent.ACTION_SEARCH.equals(getActivity().getIntent().getAction())) {
                query = getActivity().getIntent().getStringExtra(SearchManager.QUERY);
                //txt_SearchKey.setText(query);

                StmallServices services=new StmallServices(getActivity());

                services.getGoodSearchResult(query,callback);

            }
        }


        /*
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_good_list, container, false);
            ButterKnife.inject(this, rootView);


            return rootView;
        }*/
    }
}
