package com.example.studit.retrofit;


import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;
@RequiresApi(api = Build.VERSION_CODES.O)
public class RetrofitClient {
    private static RetrofitClient instance = null;
    private static RetrofitInterface.initMyApi initMyApi;
//    private final Object OkHttpClient; // 현재 동작 안되는 것 같음.. 무시해 주세요!
    public static String BASE_URL = "http:///54.180.115.224/:8081/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

//    public static <S> S createService(Class<S> serviceClass) {
//        return createService(serviceClass, null);
//    }
//
//    private static <S> S createService(
//            Class<S> serviceClass, final String authToken) {
//        if(!TextUtils.isEmpty(authToken)) {
//            AuthenticationInterceptor interceptor = new AuthenticationInterceptor("Bearer " + authToken);
//
//            if(!httpClient.interceptors().contains(interceptor)) {
//                httpClient.addInterceptor(interceptor);
//
//                builder.client(httpClient.build());
//                retrofit = builder.build();
//            }
//        }
//
//        return retrofit.create(serviceClass);
//    }
//    )

    private RetrofitClient() {
        //로그를 보기 위한 Interceptor
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        //retrofit 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client) //로그 기능 추가
                .build();

        initMyApi = retrofit.create(RetrofitInterface.initMyApi.class);
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public static RetrofitInterface.initMyApi getRetrofitInterface() {
        return initMyApi;
    }





    public String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaW5hIiwicm9sZSI6InVzZXIiLCJteU5hbWUiOiJtaW5hIiwiZXhwIjoxNjU1Mzg2OTU1LCJpYXQiOjE2NTUzODUxNTV9.83vHzTrfygd45lmyt5Qb__NJclidb5myZkteQ0XMt2I";

//    private static RetrofitClient instance = null;
//    private static RetrofitInterface.initMyApi initMyApi;

//    Gson gson = new GsonBuilder()
//            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context)
//                    -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")))
//            .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context)
//                    -> LocalDate.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
//            .registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (json, typeOfT, context)
//                    -> LocalTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("HH:mm:ss")))
//            .create();
    private Object OkHttpClient;

    private OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        okhttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);
        return okhttpClientBuilder.build();
    }
//    private RetrofitClient() {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(interceptor)
//                .build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client((okhttp3.OkHttpClient) OkHttpClient)
//                .build();
//
//        initMyApi = retrofit.create(RetrofitInterface.initMyApi.class);
    }

//    public static RetrofitClient getInstance() {
//        if (instance == null) {
//            instance = new RetrofitClient();
//        }
//        return instance;
//    }
////
////    public static RetrofitInterface.initMyApi getRetrofitInterface() {
////        return initMyApi;
////    }



