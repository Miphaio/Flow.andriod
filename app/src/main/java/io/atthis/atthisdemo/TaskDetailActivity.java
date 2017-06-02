package io.atthis.atthisdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TaskDetailActivity extends AppCompatActivity {
    private returnToken passedRToken;
    private TextView detailseller;
    private TextView detailVin;
    private TextView detailinfo;
    private EditText notetext;
    private Button detailaccept;
    private Button detailreject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        passedRToken = new returnToken(intent);
        detailseller = (TextView) this.findViewById(R.id.DetailSeller);
        detailVin = (TextView) this.findViewById(R.id.DetailVIN);
        detailinfo = (TextView) this.findViewById(R.id.DetailInfo);
        notetext = (EditText) this.findViewById(R.id.NoteText);
        detailaccept = (Button) this.findViewById(R.id.DetailAccept);
        detailreject = (Button) this.findViewById(R.id.DetailReject);
        setTitle(passedRToken.toString());
        detailseller.setText(passedRToken.seller);
        detailinfo.setText(passedRToken.car_info);
        detailVin.setText(passedRToken.vin);
        detailaccept.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                setTitle(notetext.getText().toString());
            }
        });
        detailreject.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                setTitle("Rejt");
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
        }
        public String toString(){
            return id+seller+car_info;
        }
    }
}
