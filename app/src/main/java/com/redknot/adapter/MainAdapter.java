package com.redknot.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.damiao.network.view.NetWorkDiskCacheImageView;
import com.damiao.network.view.NetWorkImageView;
import com.redknot.javabean.Status;
import com.redknot.javabean.User;
import com.redknot.miaowubo.ImgActivity;
import com.redknot.miaowubo.R;

import java.util.List;

/**
 * Created by qiaoyao on 15/5/14.
 */

public class MainAdapter extends BaseAdapter {

    private List<Status> data;
    private Context context;

    public MainAdapter(List<Status> data, Context context) {
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
//////
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = null;
        view = inflater.inflate(R.layout.listview_main, null);

        Status status = this.data.get(position);
        User user = status.getUser();

        TextView text = (TextView) view.findViewById(R.id.listview_main_text);
        TextView name = (TextView) view.findViewById(R.id.listview_main_name);
        TextView created_at = (TextView) view.findViewById(R.id.listview_main_created_at);

        TextView reposts_count = (TextView) view.findViewById(R.id.listview_main_reposts_count);
        TextView comments_count = (TextView) view.findViewById(R.id.listview_main_comments_count);
        TextView attitudes_count = (TextView) view.findViewById(R.id.listview_main_attitudes_count);


        NetWorkDiskCacheImageView img = (NetWorkDiskCacheImageView) view.findViewById(R.id.listview_main_img);
        img.setUrl(user.getProfile_image_url());


        text.setText(status.getText());
        created_at.setText(status.getCreated_at());


        reposts_count.setText("转发：" + status.getReposts_count() + "");
        comments_count.setText("评论：" + status.getComments_count() + "");
        attitudes_count.setText("点赞：" + status.getAttitudes_count() + "");

        name.setText(user.getName());


        return view;
    }

    private class ImgOnClickListener implements View.OnClickListener{

        private String img;//

        public ImgOnClickListener(String img){
            this.img = img;
        }
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ImgActivity.class);
            intent.putExtra("img_url",this.img);
            context.startActivity(intent);
        }
    }

}
