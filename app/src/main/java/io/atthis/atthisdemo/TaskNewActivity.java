package io.atthis.atthisdemo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TaskNewActivity extends AppCompatActivity {
    private List<TaskDetail> mData;
    //定义ListView对象
    private ListView mListViewArray;
    private OkHttpClient client;
    private UserInfo userinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_new);
        userinfo = new UserInfo();
        Intent intent = getIntent();
        userinfo.authority = intent.getStringExtra("authority");
        userinfo.username = intent.getStringExtra("username");
        userinfo.token = intent.getStringExtra("token");
        userinfo.id = intent.getStringExtra("id");
        //Ok Http
        client = new OkHttpClient();
        //Initial Customize ListView
        mListViewArray = (ListView) findViewById(R.id.newlist);

        mData = new ArrayList<>();
        initData();

    }
    private void initData() {
        RequestBody formBody = new FormBody.Builder().add("id", userinfo.id).add("authority", userinfo.authority).add("mode","getTask")
                .build();
        final Request request = new Request.Builder().url("http://flow.sushithedog.com/src/action.php").post(formBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<returnToken>>(){}.getType();
                            List<returnToken> rToken = gson.fromJson(response.body().string(), type);
                            clean();
                            for(int i=0;i<rToken.size();i++){
                                mData.add(new TaskDetail(rToken.get(i).getTitle(),rToken.get(i).getSubTitle(),rToken.get(i).getThirdSubTitle()));
                            }
                            refresh();
                        }catch(IOException e){

                        }
                    }
                });
            }
        });
    }
    private void clean(){
        mData = new ArrayList<>();
    }
    private void refresh(){
//        mData.add(0, new TaskDetail("Logout", ""));
        LayoutInflater inflater = getLayoutInflater();
        TaskAdapter adapter = new TaskAdapter(inflater, mData);
        mListViewArray.setAdapter(adapter);
        //Click function of List view
        mListViewArray.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg) {
                setTitle(position + "Clicked");
            }
        });
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
    public class returnToken{
        public String id;
        public String seller;
        public String car_info;
        public String vin;
        public String stage;
        public String stage1Created;
        public String stage2Created;
        public String stage3Created;
        public String status;
        public String stage1Officer_id;
        public String stage2Officer_id;
        public String stage3Officer_id;
        public String stage1Note;
        public String stage2Note;
        public String stage3Note;
        public String closeTime;
        public String getTitle(){
            return "Selling Process";
        }
        public String getSubTitle(){
            return "From: "+seller;
        }
        public String getThirdSubTitle(){
            return "Car VIN: "+vin;
        }
        public String toString(){
            return id+seller+car_info+vin;
        }
    }
}
