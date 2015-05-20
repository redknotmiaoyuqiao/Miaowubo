package com.damiao.network.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.damiao.network.http.HttpUtil;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by qiaoyao on 15/5/20.
 */
public class NetWorkDiskCatchImageView extends ImageView {

    private static Map<String, Bitmap> res = new HashMap<String, Bitmap>();
    private Context context;

    public NetWorkDiskCatchImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        // TODO Auto-generated constructor stub
    }

    public NetWorkDiskCatchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        // TODO Auto-generated constructor stub
    }

    public NetWorkDiskCatchImageView(Context context) {
        super(context);
        this.context = context;
        // TODO Auto-generated constructor stub
    }

    public void setUrl(String url) {
        if (!url.equals("")) {
            Bitmap bitmap = res.get(url);
            if (bitmap == null) {
                if (bitmap == null) {
                    MyHandler handler = new MyHandler(url);
                    ImageThread img = new ImageThread(url, handler);
                    new Thread(img).start();
                } else {
                    this.setImageBitmap(bitmap);
                }
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

        public MyHandler(String url) {
            this.url = url;
        }

        public void handleMessage(Message msg) {
            if (msg.what == 200) {
                Bitmap bitmap = (Bitmap) msg.obj;
                if (bitmap != null) {
                    NetWorkDiskCatchImageView.this.setImageBitmap(bitmap);
                    res.put(this.url, bitmap);
                    //setImg(bitmap, url);
                }
            } else if (msg.what == 400) {

            }
        }


        private void setImg(Bitmap bitmap, String name) {
            FileOutputStream fos = null;
            try {
                fos = context.openFileOutput(
                        name, Context.MODE_PRIVATE);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


    private Bitmap getImg(String img_name) {
        try {
            FileInputStream fis = context.openFileInput(img_name);
            BufferedInputStream bs = new BufferedInputStream(fis);
            Bitmap btp = BitmapFactory.decodeStream(bs);
            fis.close();
            bs.close();
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


}
