package com.redknot.miaowubo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.redknot.adapter.MainAdapter;
import com.redknot.javabean.Status;
import com.redknot.weibo.AccessTokenKeeper;
import com.redknot.weibo.Constants;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ListView main_listview;
    private SwipeRefreshLayout main_listview_refresh;

    private MainAdapter mainAdapter;
    private List<Status> data = new ArrayList<Status>();


    private StatusesAPI mStatusesAPI;
    private Oauth2AccessToken mAccessToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        Oauth2AccessToken token_bundle = AccessTokenKeeper.readAccessToken(MainActivity.this);
        String token = token_bundle.getToken();

        if (token.equals("")) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        mStatusesAPI = new StatusesAPI(this, Constants.APP_KEY, mAccessToken);

        data.clear();
        mStatusesAPI.friendsTimeline(0L, 0L, 10, 1, false, 0, false, mListener);
        main_listview_refresh.setRefreshing(true);

    }


    private void init() {
        main_listview = (ListView) findViewById(R.id.main_listview);

        main_listview_refresh = (SwipeRefreshLayout) findViewById(R.id.main_listview_refresh);
        mainAdapter = new MainAdapter(data, MainActivity.this);


        main_listview.setAdapter(mainAdapter);
        main_listview_refresh.setColorScheme(R.color.china_red, R.color.china_green,
                R.color.china_yellow, R.color.china_blue);

        main_listview_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                data.clear();
                mStatusesAPI.friendsTimeline(0L, 0L, 10, 1, false, 0, false, mListener);
            }
        });


    }


    private RequestListener mListener = new RequestListener() {
        @Override
        public void onComplete(String response) {
            if (!TextUtils.isEmpty(response)) {
                System.out.println(response);

                try {
                    JSONObject statuses = new JSONObject(response);
                    JSONArray ja = statuses.getJSONArray("statuses");

                    for(int i=0;i<ja.length();i++){
                        JSONObject jo = ja.getJSONObject(i);
                        data.add(new Status(jo));
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }


            mainAdapter.notifyDataSetChanged();
            main_listview_refresh.setRefreshing(false);

        }

        @Override
        public void onWeiboException(WeiboException e) {

            Toast.makeText(MainActivity.this, "network", Toast.LENGTH_LONG).show();
            main_listview_refresh.setRefreshing(false);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
