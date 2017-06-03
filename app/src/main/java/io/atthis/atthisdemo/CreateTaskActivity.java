package io.atthis.atthisdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateTaskActivity extends AppCompatActivity {
    private EditText seller;
    private EditText info;
    private EditText vin;
    private EditText assignto;
    private EditText notes;
    private Button submitbtn;
    private OkHttpClient client;
    private UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        seller = (EditText) this.findViewById(R.id.CTseller);
        info = (EditText) this.findViewById(R.id.CTcarinfo);
        vin = (EditText) this.findViewById(R.id.CTvin);
        assignto = (EditText) this.findViewById(R.id.CTassignto);
        notes = (EditText) this.findViewById(R.id.CTnotes);
        submitbtn = (Button) this.findViewById(R.id.CTButtonAssign);
        userInfo = new UserInfo(getIntent());
        client = new OkHttpClient();
        setTitle(userInfo.toString());
        submitbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                submittask();
            }
        });

    }
    private void submittask(){
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("mode", "createTask")
                .add("officer1",userInfo.id)
                .add("officer2", assignto.getText().toString())
                .add("carInfo", info.getText().toString())
                .add("seller", seller.getText().toString())
                .add("vin", vin.getText().toString())
                .add("note", notes.getText().toString())
                .build();
        RequestBody formBody = builder.build();
        final Request request = new Request.Builder().url("http://flow.sushithedog.com/src/action.php").post(formBody).build();
        submitbtn.setVisibility(View.INVISIBLE);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setTitle("Error");
                        submitbtn.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            setTitle(response.body().string());
                            onBackPressed();
                        }catch(IOException e){
                            setTitle(e.toString());
                        }
                    }
                });
            }
        });
    }
    public class UserInfo{
        public String authority;
        public String username;
        public String token;
        public String id;
        public String firstname;
        public String lastname;
        public void setIntent(Intent intent){
            intent.putExtra("authority", authority)
                    .putExtra("username", username)
                    .putExtra("token", token)
                    .putExtra("id", id)
                    .putExtra("firstname", firstname)
                    .putExtra("lastname", lastname);
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
}
