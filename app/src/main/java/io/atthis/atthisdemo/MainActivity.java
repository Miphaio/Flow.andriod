package io.atthis.atthisdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

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
    private TextView T01;
    private TextView T02;
    private Button B02;
    public static final String TAG = "MainActivity";
    private OkHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Atthis");

        B01 = (Button) this.findViewById(R.id.btn01);
        B02 = (Button) this.findViewById(R.id.B02);
        E01 = (EditText) this.findViewById(R.id.username);
        E02 = (EditText)this.findViewById(R.id.password);
        T01 = (TextView) this.findViewById(R.id.hintusername);
        T02 = (TextView) this.findViewById(R.id.hintpassword);
        B01.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
//                T01.setText(E01.getText());
//                T02.setText(E02.getText());
                getLoginContent();
            }
        });
        B02.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
               Intent intent = new Intent();
                intent.setClass(MainActivity.this  , SwitchActivity.class);
                startActivity(intent);
            }
        });
        client = new OkHttpClient();
    }
    private void getLoginContent(){
        RequestBody formBody = new FormBody.Builder().add("username", E01.getText().toString()).add("password",E02.getText().toString()).add("mode","up")
                .build();
        final Request request = new Request.Builder().url("http://flow.sushithedog.com/test/Login.php").post(formBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        T01.setText("Failure!");
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
                                T01.setText(returnValue.authority);
                                Intent intent = new Intent();
                                intent.setClass(MainActivity.this  , TaskActivity.class);
                                intent.putExtra("username", returnValue.username).putExtra("authority",returnValue.authority)
                                        .putExtra("token", returnValue.token).putExtra("id", returnValue.id);
                                startActivity(intent);
                                MainActivity.this.finish();
                            }else{
                                T01.setText(returnValue.status);
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


