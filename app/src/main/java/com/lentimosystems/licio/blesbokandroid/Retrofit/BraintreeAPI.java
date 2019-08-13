package com.lentimosystems.licio.blesbokandroid.Retrofit;

import com.lentimosystems.licio.blesbokandroid.Model.BraintreeToken;
import com.lentimosystems.licio.blesbokandroid.Model.BraintreeTransaction;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface BraintreeAPI {
    @GET("checkouts/new")
    Observable<BraintreeToken> getToken();

    @POST("checkouts")
    @FormUrlEncoded
    @Headers("Content-Type: application/json")
    Observable<BraintreeTransaction> submitPayment(@Field("amount") String amount,
                                                   @Field("payment_method_nonce") String nonce);
}
