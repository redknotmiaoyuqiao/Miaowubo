package com.redknot.miaowubo;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;

import com.damiao.network.view.NetWorkImageView;

/**
 * Created by qiaoyao on 15/5/19.
 */
public class ImgActivity extends ActionBarActivity {

    private NetWorkImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);

        img = (NetWorkImageView) findViewById(R.id.img);

        String url = getIntent().getStringExtra("img_url");


        img.setUrl(url);
    }
}
