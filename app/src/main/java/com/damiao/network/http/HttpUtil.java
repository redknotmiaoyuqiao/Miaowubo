package com.damiao.network.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiaoyao on 15/5/17.
 */
public class HttpUtil {




    public String getRequest(String url, Map<String, String> rawparams) throws Exception {
        String last = "?";
        for (String key : rawparams.keySet()) {
            last = last + key + "=" + rawparams.get(key) + "&";
        }
        last = last.substring(0, last.length() - 1);

        url = url + last;

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);

        HttpResponse httpResponse = httpClient.execute(get);

        String res = "";

        res = EntityUtils.toString(httpResponse.getEntity());

        return res;
    }

    public Bitmap loadImage(String url) throws Exception {
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = null;
        InputStream inputStream = null;

        response = client.execute(new HttpGet(url));
        HttpEntity entity = response.getEntity();
        inputStream = entity.getContent();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        Bitmap b = BitmapFactory.decodeStream(inputStream, null, options);

        return b;
    }
}
