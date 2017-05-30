package io.atthis.atthisdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class TaskActivity extends AppCompatActivity {
    TextView T01;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        T01 = (TextView) this.findViewById(R.id.T01);
        setTitle("My Tasks");
        Intent intent = getIntent();
        UserInfo userinfo = new UserInfo();
        userinfo.authority = intent.getStringExtra("authority");
        userinfo.username = intent.getStringExtra("username");
        userinfo.token = intent.getStringExtra("token");
        userinfo.id = intent.getStringExtra("id");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        T01.setText(userinfo.toString());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
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

}
