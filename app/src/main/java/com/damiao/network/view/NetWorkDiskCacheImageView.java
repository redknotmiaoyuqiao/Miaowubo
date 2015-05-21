package com.damiao.network.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.damiao.network.http.HttpUtil;
import com.damiao.network.http.Util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by qiaoyao on 15/5/21.
 */
public class NetWorkDiskCacheImageView extends ImageView {

    private Context context;

    public NetWorkDiskCacheImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    public NetWorkDiskCacheImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    public NetWorkDiskCacheImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    public void setUrl(String url) {

        Bitmap bitmap = getImage(url);

        if (bitmap != null) {
            this.setImageBitmap(bitmap);
        } else if (bitmap == null) {
            MyHandler handler = new MyHandler(url);
            ImageThread t = new ImageThread(handler, url);
            new Thread(t).start();
        }
    }

    private class ImageThread implements Runnable {

        private Handler handler;
        private String url;

        public ImageThread(Handler handler, String url) {
            this.url = url;
            this.handler = handler;
        }

        @Override
        public void run() {
            HttpUtil httpUtil = new HttpUtil();
            Message msg = new Message();
            try {
                Bitmap bitmap = httpUtil.loadImage(this.url);
                if (bitmap != null) {
                    msg.what = 200;
                    msg.obj = bitmap;
                } else {
                    msg.what = 400;
                }
            } catch (Exception e) {
                msg.what = 400;
            } finally {
                this.handler.sendMessage(msg);
            }
        }
    }

    private class MyHandler extends Handler {

        private String url;

        public MyHandler(String url) {
            this.url = url;
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 200) {
                Bitmap bitmap = (Bitmap) msg.obj;
                if (bitmap != null) {
                    saveImg(bitmap, url);
                    NetWorkDiskCacheImageView.this.setImageBitmap(bitmap);
                }
            } else if (msg.what == 400) {

            }
        }
    }

    private Bitmap getImage(String url) {
        url = Util.MD5(url);

        try {
            FileInputStream fis = context.openFileInput(url);
            BufferedInputStream bs = new BufferedInputStream(fis);
            Bitmap btp = BitmapFactory.decodeStream(bs);

            fis.close();

            return btp;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private void saveImg(Bitmap bitmap, String url) {

        url = Util.MD5(url);

        try {
            FileOutputStream fos = context.openFileOutput(
                    url, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
