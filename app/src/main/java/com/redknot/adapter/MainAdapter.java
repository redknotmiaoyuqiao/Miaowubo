package com.redknot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.redknot.javabean.Status;
import com.redknot.miaowubo.R;

import java.util.List;

/**
 * Created by qiaoyao on 15/5/14.
 */

public class MainAdapter extends BaseAdapter {

    private List<Status> data;
    private Context context;

    public MainAdapter(List<Status> data,Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = null;
        view = inflater.inflate(R.layout.listview_main, null);
        return view;
    }
}
