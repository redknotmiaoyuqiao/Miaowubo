package com.redknot.javabean;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qiaoyao on 15/5/16.
 */
public class User {
    private long id;
    private String idstr;
    private String screen_name;
    private String name;
    private String profile_image_url;

    private Bitmap profile_image;


    public User(JSONObject jo){
        try {
            this.id = jo.getInt("id");
            this.idstr = jo.getString("idstr");
            this.screen_name = jo.getString("screen_name");
            this.name = jo.getString("name");
            this.profile_image_url = jo.getString("profile_image_url");
        }catch(JSONException e){
            e.printStackTrace();
        }

        this.profile_image = null;
    }

    public Bitmap getProfile_image(){
        return this.profile_image;
    }

    public void setProfile_image(Bitmap profile_image){
        this.profile_image = profile_image;
    }


    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
