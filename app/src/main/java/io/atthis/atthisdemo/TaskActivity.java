package io.atthis.atthisdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TaskActivity extends AppCompatActivity {
    private ListView listView;
    private OkHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        setTitle("My Tasks");
        listView = (ListView) this.findViewById(R.id.mainlist);
        Intent intent = getIntent();
        UserInfo userinfo = new UserInfo();
        userinfo.authority = intent.getStringExtra("authority");
        userinfo.username = intent.getStringExtra("username");
        userinfo.token = intent.getStringExtra("token");
        userinfo.id = intent.getStringExtra("id");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getData()));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        client = new OkHttpClient();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg) {
                setTitle(position+"Clicked");
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }
    private void getTaskContent(){
        RequestBody formBody = new FormBody.Builder().add("username", "").add("password","").add("mode","up")
                .build();
        final Request request = new Request.Builder().url("http://flow.sushithedog.com/src/Login.php").post(formBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Terror.setText("Network Error");
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
                            MainActivity.ReturnValue returnValue = gson.fromJson(response.body().string(), MainActivity.ReturnValue.class);
                            if(returnValue.status.equals("succeed")){
                                Intent intent = new Intent();
//                                Terror.setText("Success, Loading");
                                intent.setClass(TaskActivity.this  , DetailActivity.class);
                                intent.putExtra("username", returnValue.username).putExtra("authority",returnValue.authority)
                                        .putExtra("token", returnValue.token).putExtra("id", returnValue.id);
                                startActivity(intent);
                                TaskActivity.this.finish();
                            }else{
//                                Terror.setText(returnValue.status);
                            }

                        }catch(IOException e){

                        }

                    }
                });
            }
        });
    }
    private List<String> getData(){

        List<String> data = new ArrayList<String>();
        data.add("测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");

        return data;
    }
    public class UserInfo{
        public String authority;
        public String username;
        public String token;
        public String id;

        public String toString(){
            return authority+username+token+id;
        }
    }

}
