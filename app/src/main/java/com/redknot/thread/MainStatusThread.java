package com.redknot.thread;

import android.content.Context;
import android.os.Handler;

import com.redknot.httputil.HttpUtil;
import com.redknot.httputil.UrlUtil;
import com.redknot.weibo.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiaoyao on 15/5/15.
 */
public class MainStatusThread implements Runnable {

    private Handler handler;
    private Context context;

    public MainStatusThread(Handler handler,Context context) {
        this.handler = handler;
        this.context = context;
    }

    @Override
    public void run() {
        String res = "";
        try {

            Oauth2AccessToken token_bundle = AccessTokenKeeper.readAccessToken(context);
            String token = token_bundle.getToken();

            Map<String, String> rawparams = new HashMap<String ,String>();
            rawparams.put("access_token",token);
            rawparams.put("count","20");
            rawparams.put("page","0");
            rawparams.put("trim_user","0");

            res = HttpUtil.getRequest(UrlUtil.FRIENDS_TIMELINE,rawparams);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(res);
        }
    }
}
