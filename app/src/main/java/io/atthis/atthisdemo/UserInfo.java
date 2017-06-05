package io.atthis.atthisdemo;

import android.content.Intent;

/**
 * Created by WMPCXPY on 2017/6/5.
 */

public class UserInfo {
    private String authority;
    private String username;
    private String token;
    private String id;
    private String firstname;
    private String lastname;
    public void setIntent(Intent intent){
        intent.putExtra("authority", authority)
                .putExtra("username", username)
                .putExtra("token", token)
                .putExtra("id", id)
                .putExtra("firstname", firstname)
                .putExtra("lastname", lastname);
    }
    public String getAuthority(){
        return authority;
    }
    public String getUsername(){
        return username;
    }
    public String getToken(){
        return token;
    }
    public String getId(){
        return id;
    }
    public String getFirstname(){
        return firstname;
    }
    public String getLastname(){
        return lastname;
    }
    public UserInfo(Intent intent){
        authority = intent.getStringExtra("authority");
        username = intent.getStringExtra("username");
        token = intent.getStringExtra("token");
        id = intent.getStringExtra("id");
        firstname = intent.getStringExtra("firstname");
        lastname = intent.getStringExtra("lastname");

    }
    public String toString(){
        return firstname + lastname;
    }
}
