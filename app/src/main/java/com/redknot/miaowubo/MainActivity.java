package com.redknot.miaowubo;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.redknot.adapter.MainAdapter;
import com.redknot.javabean.Status;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ListView main_listview;
    private SwipeRefreshLayout main_listview_refresh;

    private MainAdapter mainAdapter;
    private List<Status> data = new ArrayList<Status>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        main_listview = (ListView) findViewById(R.id.main_listview);
        main_listview_refresh = (SwipeRefreshLayout) findViewById(R.id.main_listview_refresh);
        mainAdapter = new MainAdapter(data,MainActivity.this);

        main_listview_refresh.setColorScheme(R.color.china_red, R.color.china_green,
                R.color.china_yellow, R.color.china_blue);

        main_listview_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }


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
