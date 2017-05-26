package io.atthis.atthisdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    Button B01;
    EditText E01;
    EditText E02;
    TextView T01;
    TextView T02;
    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        B01 = (Button) this.findViewById(R.id.btn01);
        E01 = (EditText) this.findViewById(R.id.username);
        E02 = (EditText)this.findViewById(R.id.password);
        T01 = (TextView) this.findViewById(R.id.hintusername);
        T02 = (TextView) this.findViewById(R.id.hintpassword);
        B01.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                T01.setText(E01.getText());
                T02.setText(E02.getText());
            }
        });
    }
}

