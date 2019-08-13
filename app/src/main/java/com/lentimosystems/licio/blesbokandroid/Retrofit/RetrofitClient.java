package com.lentimosystems.licio.blesbokandroid.Retrofit;




import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit instance;



    public static Retrofit getInstance() {
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
        if (instance == null)
            instance = new Retrofit.Builder()
                    .baseUrl("http://localhost:3000")
                    //.baseUrl("http://10.0.2.2:3000/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        return instance;
    }
}
