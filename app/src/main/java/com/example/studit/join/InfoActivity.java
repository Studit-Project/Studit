package com.example.studit.join;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ArrayAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.studit.login.Login2Activity;
import com.example.studit.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.studit.R;
import com.example.studit.retrofit.RetrofitInterface;
import com.example.studit.retrofit.join.ModelUserJoinInfo;

import com.example.studit.retrofit.join.Model_ValidatePhone;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class InfoActivity extends AppCompatActivity {

    String BASE_URL = "http://34.64.52.84:8081/";

    private ArrayAdapter adapter;
    private Spinner sp_age_y;
    private Spinner sp_age_m;
    private Spinner sp_age_d;
    private String UserGender;
    private RadioGroup genderGroup;
    private Button bt_submit;
    private AlertDialog dialog;


    private final String TAG = this.getClass().getSimpleName();

    Intent intent;

    String phone = getIntent().getStringExtra("phone");

    Model_ValidatePhone getPhone = new Model_ValidatePhone(phone);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Gson gson = new Gson();

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(loggingInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        //id??? ??????
        final EditText nickname = findViewById(R.id.nickname);

        genderGroup = findViewById(R.id.gender);

        //spinner ?????? ??????, id ????????????
        sp_age_y = findViewById(R.id.sp_age_y);
        adapter = ArrayAdapter.createFromResource(this, R.array.age_year, android.R.layout.simple_dropdown_item_1line);
        sp_age_y.setAdapter(adapter);

        sp_age_m = findViewById(R.id.sp_age_m);
        adapter = ArrayAdapter.createFromResource(this, R.array.age_month, android.R.layout.simple_dropdown_item_1line);
        sp_age_m.setAdapter(adapter);

        sp_age_d = findViewById(R.id.sp_age_d);
        adapter = ArrayAdapter.createFromResource(this, R.array.age_day, android.R.layout.simple_dropdown_item_1line);
        sp_age_d.setAdapter(adapter);


        //radio ?????? ??? ???????????????
        genderGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            if(i==R.id.MALE){
                UserGender = "MALE";
            } else if(i==R.id.FEMALE){
                UserGender = "FEMALE";
            }
        });


        //???????????? ????????? ????????? ???
        bt_submit = findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(view -> {
            final String UserNick = nickname.getText().toString();
            final String UserYear = sp_age_y.getSelectedItem().toString();
            final String UserMonth = sp_age_m.getSelectedItem().toString();
            final String UserDay = sp_age_d.getSelectedItem().toString();
            final String UserBirth = UserYear + "-" + UserMonth + "-" + UserDay;

            //????????? ?????? ??????
            if(UserNick.equals("") || UserYear.equals("") || UserMonth.equals("") || UserDay.equals("")){
                AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);
                dialog = builder.setMessage(("?????? ??????????????????.")).setNegativeButton("??????",null).create();
                dialog.show();
                return;
            }

            //radio ???????????? ????????? ??????
            if(UserGender == null){
                AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);
                dialog = builder.setMessage(("?????? ??????????????????.")).setNegativeButton("??????",null).create();
                dialog.show();
            }

            RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
            ModelUserJoinInfo userJoinInfo = new ModelUserJoinInfo(UserBirth, UserGender, UserNick);
            Call<ModelUserJoinInfo> call = retrofitInterface.getUserInfo(userJoinInfo);

            intent = getIntent();

            call.enqueue(new Callback<ModelUserJoinInfo>() {
                @Override
                public void onResponse(@NonNull Call<ModelUserJoinInfo> call, @NonNull Response<ModelUserJoinInfo> response) {
                    if(response.isSuccessful() && response.body() != null){ //????????????
                        Log.e(TAG, "???????????? ??????!");
                        Toast.makeText(getApplicationContext(), "????????????! ??????????????????.", Toast.LENGTH_LONG).show();
                        intent = new Intent(InfoActivity.this, Login2Activity.class); //????????????????????? ?????????
                        startActivity(intent);
                    } else {
                        try {
                            String body = response.errorBody().string();
                            Log.e(TAG, "error!!! - body : " + body);
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelUserJoinInfo> call, Throwable t) {
                    Log.e(TAG, "fail!!!!! " + t.getMessage());
                }
            });


        });

    }
}