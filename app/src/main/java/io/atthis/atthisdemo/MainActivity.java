package io.atthis.atthisdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;

import cn.jpush.android.api.JPushInterface;
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
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        setTitle("Atthis");
        B01 = (Button) this.findViewById(R.id.btn01);
        E01 = (EditText) this.findViewById(R.id.username);
        E02 = (EditText)this.findViewById(R.id.password);
        Terror = (TextView) this.findViewById(R.id.Terror);
        B01.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                getLoginContent();
            }
        });
        client = new OkHttpClient();
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
                            Gson gson = new Gson();
                            ReturnValue returnValue = gson.fromJson(response.body().string(), ReturnValue.class);
                            if(returnValue.status.equals("succeed")){
                                Intent intent = new Intent();
                                Terror.setText("Success, Loading");
                                intent.setClass(MainActivity.this  , TaskActivity.class);
                                intent.putExtra("username", returnValue.username).putExtra("authority",returnValue.authority)
                                        .putExtra("token", returnValue.token).putExtra("id", returnValue.id);
                                startActivity(intent);
                                MainActivity.this.finish();
                            }else{
                                Terror.setText(returnValue.status);
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
        public String token;
        public String id;
    }
}


