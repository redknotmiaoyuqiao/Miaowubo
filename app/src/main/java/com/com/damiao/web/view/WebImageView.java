package com.com.damiao.web.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.redknot.httputil.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiaoyao on 15/5/16.
 */
public class WebImageView extends ImageView {


    private static Map<String,Bitmap> res = new HashMap<String,Bitmap>();
    private String url;

    public WebImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public WebImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public WebImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public void setUrl(String url) {
        Bitmap b = this.res.get(url);

        if(b != null){
            this.setImageBitmap(b);
        }
        else{
            this.url = url;
            MyHandle handler = null;
            handler = new MyHandle();
            ImageThread t = new ImageThread(url, handler);
            new Thread(t).start();
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
                Bitmap bitmap = HttpUtil.loadImage(this.url);
                if (bitmap != null) {
                    msg.what = 200;
                    msg.obj = bitmap;
                }
            } catch (Exception e) {
                e.printStackTrace();
                msg.what = 400;
            } finally {
                this.handler.sendMessage(msg);
            }
        }
    }

    private class MyHandle extends Handler {
        public void handleMessage(Message msg) {
            if (msg.what == 200) {
                Bitmap bitmap = (Bitmap) msg.obj;
                WebImageView.this.setImageBitmap(bitmap);
                WebImageView.this.res.put(WebImageView.this.url,bitmap);
            } else {

            }
        }
    }
}
