package io.atthis.atthisdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class TaskNewActivity extends AppCompatActivity {
    private List<TaskDetail> mData;
    //定义ListView对象
    private ListView mListViewArray;
    private OkHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_new);
        //Get Data from pre page
        UserInfo userinfo = new UserInfo();
        Intent intent = getIntent();
        userinfo.authority = intent.getStringExtra("authority");
        userinfo.username = intent.getStringExtra("username");
        userinfo.token = intent.getStringExtra("token");
        userinfo.id = intent.getStringExtra("id");
        //Ok Http
        client = new OkHttpClient();
        //Initial Customize ListView
        mListViewArray = (ListView) findViewById(R.id.newlist);
        LayoutInflater inflater = getLayoutInflater();
        initData();
        TaskAdapter adapter = new TaskAdapter(inflater, mData);
        mListViewArray.setAdapter(adapter);
        //Click function of List view
        mListViewArray.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg) {
                setTitle(position+"Clicked");
            }
        });
    }
    private void initData() {
        mData = new ArrayList<>();
        mData.add(new TaskDetail("title test", 150));
        mData.add(new TaskDetail("title test", 15123));
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
