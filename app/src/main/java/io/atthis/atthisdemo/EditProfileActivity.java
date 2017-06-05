package io.atthis.atthisdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditProfileActivity extends AppCompatActivity {
    private EditText firstname;
    private EditText lastname;
    private EditText password;
    private Button btnsubmit;
    private OkHttpClient client;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        firstname = (EditText) this.findViewById(R.id.EPfirstname);
        lastname = (EditText) this.findViewById(R.id.EPlastname);
        password = (EditText) this.findViewById(R.id.EPpassword);
        btnsubmit = (Button) this.findViewById(R.id.EPSubmit);
        userInfo = new UserInfo(getIntent());
        client = new OkHttpClient();
        firstname.setText(userInfo.getFirstname());
        lastname.setText(userInfo.getLastname());
        btnsubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }
    private void updateProfile(){
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("mode", "EditAction")
                .add("firstname",firstname.getText().toString())
                .add("lastname", lastname.getText().toString())
                .add("password", password.getText().toString())
                .add("authority", userInfo.getAuthority())
                .add("id", userInfo.getId())
                .build();
        RequestBody formBody = builder.build();
        final Request request = new Request.Builder().url("http://flow.sushithedog.com/src/action.php").post(formBody).build();
        btnsubmit.setVisibility(View.INVISIBLE);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setTitle("Error");
                        btnsubmit.setVisibility(View.VISIBLE);
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
}
