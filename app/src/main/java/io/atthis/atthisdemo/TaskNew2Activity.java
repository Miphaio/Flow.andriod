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

public class TaskNew2Activity extends AppCompatActivity {
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
        setContentView(R.layout.activity_task_new2);
        Intent intent = getIntent();
        userinfo = new UserInfo(intent);
        B01 = (Button) this.findViewById(R.id.flowbutton);
        refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
        //Ok Http
        client = new OkHttpClient();
        //Initial Customize ListView
        mListViewArray = (ListView) findViewById(R.id.newlist);
        B01.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TaskNew2Activity.this, SettingActivity.class);
                userinfo.setIntent(intent);
                startActivity(intent);
            }
        });
        mData = new ArrayList<>();
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initData();
                        }
                    });
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        });
    }

    private void initData() {
        setTitle("Atthis");
        RequestBody formBody = new FormBody.Builder()
                .add("id", userinfo.getId())
                .build();
        final Request request = new Request.Builder().url("http://atthis.sushithedog.com/src/api/GetTask.php").post(formBody).build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        });
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setTitle("Network Error");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                try {
                    final String Jre = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (Jre.length() < 10) {
                                setTitle("All Done");

                                clean();
                                refresh();
                            } else {
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
                        }
                    });
                } catch (IOException e) {
                    setTitle("Error");
                }

            }
        });
    }

    private void clean() {
        mData = new ArrayList<>();
    }

    private void refresh() {
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

    private void jumpWithToken(returnToken reT) {
        Intent intent = new Intent();
        intent.setClass(TaskNew2Activity.this, WebTaskDetailActivity.class);
        reT.addExtra(intent);
        startActivity(intent);
        clean();
        refresh();
    }

    private class returnToken {
        public int id;
        public String stage;
        public String from;
        public String type;

        String getTitle() {
            return type;
        }

        String getSubTitle() {
            return "From: " + from;
        }

        String getThirdSubTitle() {
            return "Type: " + type + "   "+ "Stage: " + stage;
        }

        void addExtra(Intent intent) {
            intent.putExtra("id", id+"")
                    .putExtra("type", type);
        }
    }

}
