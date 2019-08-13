package com.lentimosystems.licio.blesbokandroid;

import com.google.gson.JsonObject;
import com.lentimosystems.licio.blesbokandroid.Model.BraintreeToken;
import com.lentimosystems.licio.blesbokandroid.Model.BraintreeTransaction;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RequestInterface {
    @GET("checkouts/new")
    Call<String> getClientToken();

    @POST("checkouts")
    @Headers("Content-Type: application/json")
    Call<ResponseBody> getPayment(@Body JsonObject jsonObject);

}
