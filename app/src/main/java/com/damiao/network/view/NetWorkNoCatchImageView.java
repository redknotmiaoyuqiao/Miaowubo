package com.damiao.network.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.damiao.network.http.HttpUtil;

/**
 * Created by qiaoyao on 15/5/20.
 */
public class NetWorkNoCatchImageView extends ImageView {
    public NetWorkNoCatchImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public NetWorkNoCatchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public NetWorkNoCatchImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public void setUrl(String url) {
        if (!url.equals("")) {
            MyHandler handler = new MyHandler();
            ImageThread img = new ImageThread(url, handler);
            new Thread(img).start();
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
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 200) {
                Bitmap bitmap = (Bitmap) msg.obj;
                if (bitmap != null) {
                    NetWorkNoCatchImageView.this.setImageBitmap(bitmap);
                }
            } else if (msg.what == 400) {

            }
        }
    }
}
