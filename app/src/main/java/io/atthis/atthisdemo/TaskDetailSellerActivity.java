package io.atthis.atthisdemo;

/*import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

public class TaskDetailSellerActivity extends AppCompatActivity {
    private TextView detailnotes;
    private EditText detailseller;
    private Button detailupdate;
    private EditText detailvin;
    private EditText detailinfo;
    private EditText detailcomments;
    private returnToken passedRToken;
    private OkHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail_seller);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        passedRToken = new returnToken(intent);
        detailseller = (EditText) this.findViewById(R.id.SDetailSeller);
        detailcomments = (EditText) this.findViewById(R.id.SDetailComments);
        detailvin = (EditText) this.findViewById(R.id.SDetailVin);
        detailupdate = (Button) this.findViewById(R.id.SDetailUpdate);
        detailinfo = (EditText) this.findViewById(R.id.SDetailInfo);
        detailnotes = (TextView) this.findViewById(R.id.SdetailNotes);
        setTitle(passedRToken.toString());
        detailseller.setText(passedRToken.seller);
        detailnotes.setText(passedRToken.notes);
        detailvin.setText(passedRToken.vin);
        detailinfo.setText(passedRToken.car_info);
        client = new OkHttpClient();
        detailupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUpdate();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void sendUpdate(){
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("mode", "Officer1Action")
                .add("action","submit")
//                .add("carinfo", )
                .add("note", detailcomments.getText().toString())
                .add("taskId", passedRToken.id)
                .add("id", passedRToken.officerid)
                .build();
        RequestBody formBody = builder.build();
        final Request request = new Request.Builder().url("http://flow.sushithedog.com/src/action.php").post(formBody).build();
        detailupdate.setVisibility(View.INVISIBLE);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setTitle("Error");
                        detailupdate.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response){
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
        public String officerid;
        public String notes;
        public returnToken(Intent intent){
            id = intent.getStringExtra("id");
            seller = intent.getStringExtra("seller");
            car_info = intent.getStringExtra("car_info");
            vin = intent.getStringExtra("vin");
            stage = intent.getStringExtra("stage");
            stage1Created = intent.getStringExtra("stage1Created");
            stage2Created = intent.getStringExtra("stage2Created");
            stage3Created = intent.getStringExtra("stage3Created");
            status = intent.getStringExtra("status");
            stage1Officer_id = intent.getStringExtra("stage1Officer_id");
            stage2Officer_id = intent.getStringExtra("stage2Officer_id");
            stage3Officer_id = intent.getStringExtra("stage3Officer_id");
            stage1Note = intent.getStringExtra("stage1Note");
            stage2Note = intent.getStringExtra("stage2Note");
            stage3Note = intent.getStringExtra("stage3Note");
            closeTime = intent.getStringExtra("closeTime");
            officerid = intent.getStringExtra("userInfoId");
            notes = intent.getStringExtra("notes");
        }
        public String toString(){
            return id+seller+car_info;
        }
    }
}*/
