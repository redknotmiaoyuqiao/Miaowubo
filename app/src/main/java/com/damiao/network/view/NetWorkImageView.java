package com.damiao.network.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.damiao.network.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiaoyao on 15/5/17.
 */
public class NetWorkImageView extends ImageView {

    public static Map<String, Bitmap> res = new HashMap<String, Bitmap>();

    public NetWorkImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public NetWorkImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public NetWorkImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public void setUrl(String url) {

        if(!url.equals("")){
            Bitmap bitmap = res.get(url);

            if(bitmap != null){
                this.setImageBitmap(bitmap);
            }else{
                MyHandler handler = new MyHandler(url);
                ImageThread img = new ImageThread(url, handler);
                new Thread(img).start();
            }
        }


    }

    private class ImageThread implements Runnable {

        private String url;
        private Handler handler;

        public ImageThread(String url, Handler handler) {
            this.url = url;
            this.handler = handler;
        }

        @Override
        public void run() {

            Message msg = new Message();

            try {
                HttpUtil httpUtil = new HttpUtil();
                Bitmap bitmap = httpUtil.loadImage(this.url);

                msg.what = 200;
                msg.obj = bitmap;
            } catch (Exception e) {
                msg.what = 400;
            } finally {
                this.handler.sendMessage(msg);
            }
        }
    }

    private class MyHandler extends Handler {

        private String url;

        public MyHandler(String url){
            this.url = url;
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 200) {
                Bitmap bitmap = (Bitmap) msg.obj;
                if (bitmap != null) {
                    NetWorkImageView.this.setImageBitmap(bitmap);
                    res.put(this.url,bitmap);
                }
            } else if (msg.what == 400) {

            }
        }
    }

}

