package io.atthis.atthisdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
    private List<returnToken> syncToken;
    //定义ListView对象
    private ListView mListViewArray;
    private RefreshableView refreshableView;
    private OkHttpClient client;
    private UserInfo userinfo;
    private Button B01;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_new);
        userinfo = new UserInfo();
        Intent intent = getIntent();
        B01 = (Button) this.findViewById(R.id.flowbutton);
        userinfo.authority = intent.getStringExtra("authority");
        userinfo.username = intent.getStringExtra("username");
        userinfo.token = intent.getStringExtra("token");
        userinfo.id = intent.getStringExtra("id");
        refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
        //Ok Http
        client = new OkHttpClient();
        //Initial Customize ListView
        mListViewArray = (ListView) findViewById(R.id.newlist);
        B01.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TaskNewActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        mData = new ArrayList<>();
        new Thread(new Runnable(){
            @Override
            public void run() {
                initData();
            }
        });
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    initData();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        }, 0);

    }
    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
    private void initData() {
        setTitle("Atthis");
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
                            String Jre = response.body().string();
                            if(Jre.length()<10){
                                setTitle("All Done");
                                clean();
                                refresh();
                            }else{
                                Gson gson = new Gson();
                                Type type = new TypeToken<List<returnToken>>() {
                                }.getType();
                                syncToken = new ArrayList<>();
                                List<returnToken> rToken = gson.fromJson(Jre, type);
                                clean();
                                for (int i = 0; i < rToken.size(); i++) {
                                    mData.add(new TaskDetail(rToken.get(i).getTitle(), rToken.get(i).getSubTitle(), rToken.get(i).getThirdSubTitle()));
                                    syncToken.add(rToken.get(i));
                                }
                                refresh();
                            }
                        }catch(IOException e){
                            setTitle("Error");
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
        //generate layout
        LayoutInflater inflater = getLayoutInflater();
        TaskAdapter adapter = new TaskAdapter(inflater, mData);
        mListViewArray.setAdapter(adapter);
        //Click function of List view
        mListViewArray.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg) {
                jumpWithToken(syncToken.get(position));
            }
        });
    }
    private void jumpWithToken(returnToken reT){
        Intent intent = new Intent();
        switch (reT.stage){
            case "0":
            case "1":
                intent.setClass(TaskNewActivity.this, TaskDetailActivity.class);
                break;
            case "2":
                intent.setClass(TaskNewActivity.this, TaskDetailSellerActivity.class);
                break;
            case "3":
                intent.setClass(TaskNewActivity.this, TaskDetailDoneActivity.class);
                break;
            default:
                return;
        }
        reT.addExtra(intent, userinfo);
        startActivity(intent);
        clean();
        refresh();
        // TODO when came back, refresh and DONE
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
        public ArrayList<returnNote> notes;
        public String getTitle(){
            return "Selling Process";
        }
        public String getSubTitle(){
            return "From: "+seller;
        }
        public String getThirdSubTitle(){
            return "Car VIN: "+vin;
        }
        public void addExtra(Intent intent, UserInfo userinfo){
            intent.putExtra("id", id).putExtra("seller",seller)
                    .putExtra("car_info", car_info).putExtra("vin", vin)
                    .putExtra("stage", stage).putExtra("stage1Created", stage1Created)
                    .putExtra("stage2Created", stage2Created).putExtra("stage3Created", stage3Created)
                    .putExtra("status", status).putExtra("stage1Officer_id", stage1Officer_id)
                    .putExtra("stage2Officer_id", stage2Officer_id).putExtra("stage3Officer_id", stage3Officer_id)
                    .putExtra("stage1Note", stage1Note).putExtra("stage3Officer_id", stage3Officer_id)
                    .putExtra("stage3Officer_id", stage3Officer_id).putExtra("stage2Note", stage2Note)
                    .putExtra("stage3Note", stage3Note).putExtra("closeTime", closeTime)
                    .putExtra("userInfoId", userinfo.id).putExtra("notes", notesToAllString());
        }
        private String notesToAllString(){
            String re = "";
            for(int i=0;i<notes.size();i++){
                re+=notes.get(i).toString();
            }
            return re;
        }
        public String toString(){
            return id+seller+car_info+vin;
        }
    }
    public class returnNote{
        public String msg;
        public String stage;
        public String firstname;
        public String lastname;

        public String toString(){
            return firstname+lastname+"@"+stage+":\n"+msg+"\n";
        }
    }
}
