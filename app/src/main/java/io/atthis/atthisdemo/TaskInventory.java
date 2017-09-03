package io.atthis.atthisdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

/**
 * Created by Jacky on 9/2/17.
 */

public class TaskInventory extends AppCompatActivity {

    private returnToken passedToken;
    private EditText vinSearch;
    private TextView carMake;
    private TextView carModel;
    private TextView carDataId;
    private TextView carVin;
    private TextView carYear;
    private TextView carMileage;
    private OkHttpClient client;

    private final static String ATTHIS_CAR_INVENTORY = "/Car/GetAllCars.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_inventroy);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        carMake = (TextView)findViewById(R.id.tv_car_make);
        carModel = (TextView)findViewById(R.id.tv_car_model);
        carDataId = (TextView)findViewById(R.id.tv_car_id);
        carVin = (TextView)findViewById(R.id.tv_car_vin);
        carYear = (TextView)findViewById(R.id.tv_car_year);
        carMileage = (TextView)findViewById(R.id.tv_car_mileage);

        Intent intent = getIntent();
        passedToken = new returnToken(intent);
        if (intent != null) {     //more expection need to be handled here, come back later
            fillInLayoutText(passedToken);
        }

        client = new OkHttpClient();
        getCarInfo();


    }

    private void getCarInfo() {
        RequestBody formBody = new FormBody.Builder()
                .add("car", ATTHIS_CAR_INVENTORY)
                .build();
        final Request request = new Request.Builder().url("http://atthis.sushithedog.com/src/api").post(formBody).build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getCarInfo();
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        gson.toJson(passedToken); //learning gson, come back later
                    }
                });


            }
        });
    }

    private void fillInLayoutText(returnToken reT) {    //orgnize and fill in the info here

    }

    public class returnToken {
        public String id;
        public String make;
        public String model;
        public String year;
        public String vin;
        public String mileage;
        public String exteriorColor;
        public String interiorColor;
        public String fuel;
        public String engine;
        public String transmission;
        public String driveType;
        public String bodyStyle;
        public String comments;
        public String picture;
        public returnToken(Intent intent) {
            id = intent.getStringExtra("id");
            make = intent.getStringExtra("make");
            model = intent.getStringExtra("model");
            year = intent.getStringExtra("year");
            vin = intent.getStringExtra("vin");
            mileage = intent.getStringExtra("mileage");
            exteriorColor = intent.getStringExtra("exteriorColor");
            interiorColor = intent.getStringExtra("interiorColor");
            fuel = intent.getStringExtra("fuel");
            engine = intent.getStringExtra("engine");
            transmission = intent.getStringExtra("transmission");
            driveType = intent.getStringExtra("driveType");
            bodyStyle = intent.getStringExtra("bodyStyle");
            comments = intent.getStringExtra("comments");
            picture = intent.getStringExtra("picture"); //url to image
        }
    }
}
