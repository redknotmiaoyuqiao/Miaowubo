package com.damiao.network.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiaoyao on 15/5/17.
 */
public class NetWorkImageView extends ImageView {

    private Map<String, Bitmap> res = new HashMap<String, Bitmap>();
    private String url = "";

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
        if (!url.equals("")) {
            Bitmap bitmap = this.res.get(url);

            if (bitmap == null) {
                MyHandler handler = new MyHandler();
                ImageThread img = new ImageThread(url, handler);
                new Thread(img).start();
            } else {
                this.setImageBitmap(bitmap);
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
                msg.what = 200;
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
                if (bitmap != null && (!NetWorkImageView.this.url.equals(""))) {
                    NetWorkImageView.this.res.put(NetWorkImageView.this.url, bitmap);
                    NetWorkImageView.this.setImageBitmap(bitmap);
                }
            } else if (msg.what == 400) {

            }
        }
    }

}

