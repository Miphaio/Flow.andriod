package io.atthis.atthisdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private Button B01;
    private EditText E01;
    private EditText E02;
    private TextView Terror;
    public static final String TAG = "MainActivity";
    private OkHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //J push initial
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        setTitle("Atthis");
        // initial buttons
        B01 = (Button) this.findViewById(R.id.btn01);
        E01 = (EditText) this.findViewById(R.id.username);
        E02 = (EditText)this.findViewById(R.id.password);
        //Error log label
        Terror = (TextView) this.findViewById(R.id.Terror);
        client = new OkHttpClient();
        if(testwithtoken()){
            sendLoginWithToken();
        }else {
            B01.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    getLoginContent();
                }
            });
        }
    }
    private boolean testwithtoken(){
        SharedPreferences preferences = getSharedPreferences("userInfo",  Activity.MODE_PRIVATE);
        String token = preferences.getString("token", "");
        if(token.length()>0){
            return true;
        }
        return false;
    }
    private void sendLoginWithToken(){
        SharedPreferences preferences = getSharedPreferences("userInfo",  Activity.MODE_PRIVATE);
        String token = preferences.getString("token", "");
        RequestBody formBody = new FormBody.Builder().add("token", token).add("mode","token")
                .build();
        final Request request = new Request.Builder().url("http://flow.sushithedog.com/src/Login.php").post(formBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Terror.setText("Network Error");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                if(response.code() != 200){
                                    Terror.setText("Server Error code "+response.code()+", Connect Admin");
                                }else{
                                    Gson gson = new Gson();
                                    ReturnValue returnValue = gson.fromJson(response.body().string(), ReturnValue.class);
                                    if(returnValue.status.equals("succeed")){
                                        Intent intent = new Intent();
                                        Terror.setText("Success, Loading");
                                        intent.setClass(MainActivity.this, TaskNew2Activity.class);
                                        intent.putExtra("username", returnValue.username).putExtra("authority",returnValue.authority)
                                                .putExtra("token", returnValue.token).putExtra("id", returnValue.id)
                                                .putExtra("firstname", returnValue.firstname)
                                                .putExtra("lastname", returnValue.lastname);
                                        startActivity(intent);
                                        MainActivity.this.finish();
                                    }else{
                                        Terror.setText("Token out of date, using Password");
                                        B01.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                getLoginContent();
                                            }
                                        });
                                    }
                                }


                            }catch(IOException e){

                            }
                        }
                    });
            }
        });
    }
    private void getLoginContent(){
        RequestBody formBody = new FormBody.Builder().add("username", E01.getText().toString()).add("password",E02.getText().toString()).add("mode","up")
                .build();
        final Request request = new Request.Builder().url("http://flow.sushithedog.com/src/Login.php").post(formBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Terror.setText("Network Error");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            if(response.code() != 200){
                                Terror.setText("Server Error code "+response.code()+", Connect Admin");
                            }else {
                                Gson gson = new Gson();
                                ReturnValue returnValue = gson.fromJson(response.body().string(), ReturnValue.class);
                                Terror.setText(returnValue.toString());
                            if(returnValue.status.equals("succeed")){
                                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                                editor.putString("token", returnValue.token);
                                editor.commit();//提交修改
                                SharedPreferences preferences = getSharedPreferences("userInfo",  Activity.MODE_PRIVATE);
                                String token = preferences.getString("token", "");
                                Terror.setText(token);
                                Intent intent = new Intent();
                                Terror.setText("Success, Loading");
                                intent.setClass(MainActivity.this, TaskNew2Activity.class);
                                intent.putExtra("username", returnValue.username)
                                        .putExtra("authority",returnValue.authority)
                                        .putExtra("token", returnValue.token)
                                        .putExtra("id", returnValue.id)
                                        .putExtra("firstname", returnValue.firstname)
                                        .putExtra("lastname", returnValue.lastname);
                                JPushInterface.setAlias(MainActivity.super.getBaseContext(), returnValue.token, new TagAliasCallback() {
                                    @Override
                                    public void gotResult(int i, String s, Set<String> set) {
                                        Terror.setText(s + i);
                                    }
                                });
                                startActivity(intent);
                                MainActivity.this.finish();
                            }else{
                                Terror.setText(returnValue.status);
                            }
                            }
                        }catch(IOException e){

                        }

                    }
                });
            }
        });
    }
    public class ReturnValue{
        public String status;
        public String authority;
        public String username;
        public String firstname;
        public String lastname;
        public String token;
        public String id;

        public String toString(){
            if(status == null){
                return "TTT";
            }
            return status+firstname+lastname;
        }
    }
}


