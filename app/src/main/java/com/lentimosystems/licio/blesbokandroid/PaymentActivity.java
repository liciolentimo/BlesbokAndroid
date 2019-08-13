package com.lentimosystems.licio.blesbokandroid;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.google.gson.JsonObject;
import com.lentimosystems.licio.blesbokandroid.Model.BraintreeToken;
import com.lentimosystems.licio.blesbokandroid.Model.BraintreeTransaction;
import com.lentimosystems.licio.blesbokandroid.Retrofit.BraintreeAPI;
import com.lentimosystems.licio.blesbokandroid.Retrofit.RetrofitClient;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentActivity extends AppCompatActivity {
    Button btn_pay;
    EditText edtAmount;
    LinearLayout group_waiting,group_payment;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    BraintreeAPI myApi;

    String token;

    BraintreeToken braintreeToken;

    private static final int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        myApi = RetrofitClient.getInstance().create(BraintreeAPI.class);

        group_payment = (LinearLayout)findViewById(R.id.payment_group);
        group_waiting = (LinearLayout)findViewById(R.id.waiting_group);
        btn_pay = (Button)findViewById(R.id.btn_pay);
        edtAmount = (EditText)findViewById(R.id.edtAmount);

        //token = braintreeToken.getClientToken();

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBraintreeSubmit("eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiJleUowZVhBaU9pSktWMVFpTENKaGJHY2lPaUpGVXpJMU5pSXNJbXRwWkNJNklqSXdNVGd3TkRJMk1UWXRjMkZ1WkdKdmVDSjkuZXlKbGVIQWlPakUxTmpVM09Ea3pORGdzSW1wMGFTSTZJbVZrT1RsbE16WmlMVGd4TVRRdE5HRTJZeTA1WWpsbExUVXlNbUV6TjJNM05tRTFNQ0lzSW5OMVlpSTZJamc1TTJoaWJqUjZhSGg2TW5wb2VYRWlMQ0pwYzNNaU9pSkJkWFJvZVNJc0ltMWxjbU5vWVc1MElqcDdJbkIxWW14cFkxOXBaQ0k2SWpnNU0yaGlialI2YUhoNk1ucG9lWEVpTENKMlpYSnBabmxmWTJGeVpGOWllVjlrWldaaGRXeDBJanBtWVd4elpYMHNJbkpwWjJoMGN5STZXeUp0WVc1aFoyVmZkbUYxYkhRaVhTd2liM0IwYVc5dWN5STZlMzE5Lk1BUHItOUJ6emwwWkdKaENIT0J1bmI1MkJDcEx4S1lyeVVJNGRwQXUxeS14ODl1cGNGV2o2SWFldDdDdHVjOGRRNElqc0MxMlR5MHl1ZE9pU0w3X0VBIiwiY29uZmlnVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzLzg5M2hibjR6aHh6MnpoeXEvY2xpZW50X2FwaS92MS9jb25maWd1cmF0aW9uIiwiZ3JhcGhRTCI6eyJ1cmwiOiJodHRwczovL3BheW1lbnRzLnNhbmRib3guYnJhaW50cmVlLWFwaS5jb20vZ3JhcGhxbCIsImRhdGUiOiIyMDE4LTA1LTA4In0sImNoYWxsZW5nZXMiOltdLCJlbnZpcm9ubWVudCI6InNhbmRib3giLCJjbGllbnRBcGlVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvODkzaGJuNHpoeHoyemh5cS9jbGllbnRfYXBpIiwiYXNzZXRzVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhdXRoVXJsIjoiaHR0cHM6Ly9hdXRoLnZlbm1vLnNhbmRib3guYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhbmFseXRpY3MiOnsidXJsIjoiaHR0cHM6Ly9vcmlnaW4tYW5hbHl0aWNzLXNhbmQuc2FuZGJveC5icmFpbnRyZWUtYXBpLmNvbS84OTNoYm40emh4ejJ6aHlxIn0sInRocmVlRFNlY3VyZUVuYWJsZWQiOnRydWUsInBheXBhbEVuYWJsZWQiOnRydWUsInBheXBhbCI6eyJkaXNwbGF5TmFtZSI6IkxlbnRpbW8gU3lzdGVtcyIsImNsaWVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJtZXJjaGFudEFjY291bnRJZCI6ImxlbnRpbW9zeXN0ZW1zIiwiY3VycmVuY3lJc29Db2RlIjoiVVNEIn0sIm1lcmNoYW50SWQiOiI4OTNoYm40emh4ejJ6aHlxIiwidmVubW8iOiJvZmYifQ==");
            }
        });

//        compositeDisposable.add(myApi.getToken().subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe(new Consumer<BraintreeToken>() {
//            @Override
//            public void accept(BraintreeToken braintreeToken) throws Exception {
//                if (braintreeToken.isSuccess()){
//                    group_waiting.setVisibility(View.INVISIBLE);
//                    group_payment.setVisibility(View.VISIBLE);
//
//                    token = braintreeToken.getClientToken();
//                }
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                Toast.makeText(PaymentActivity.this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }));
    }

//    @Override
//    protected void onDestroy() {
//        compositeDisposable.clear();
//        super.onDestroy();
//    }
//
//    private void submitPayment(String token) {
//        DropInRequest dropInRequest = new DropInRequest().clientToken(token);
//        startActivityForResult(dropInRequest.getIntent(this),REQUEST_CODE);
//    }
//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            if (resultCode == RESULT_OK){
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();

                if (!TextUtils.isEmpty(edtAmount.getText().toString())){
                    String amount = edtAmount.getText().toString();
                    compositeDisposable.add(myApi
                    .submitPayment(amount,nonce.getNonce())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<BraintreeTransaction>() {
                        @Override
                        public void accept(BraintreeTransaction braintreeTransaction) throws Exception {
                            if (braintreeTransaction.isSuccess()){
                                Toast.makeText(PaymentActivity.this, "Payment Successful!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PaymentActivity.this, "Payment Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Toast.makeText(PaymentActivity.this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }));
                }
            }
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_CODE) {
//            if (resultCode == Activity.RESULT_OK) {
//                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
//                String payment_method_nonce=result.getPaymentMethodNonce().getNonce();
////                submitNonce(payment_method_nonce); -->> create server side api to accept payment amount and PaymentNonce
//                // use the result to update your UI and send the payment method nonce to your server
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                // the user canceled
//            } else {
//                // handle errors here, an exception may be available in
//                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
//            }
//        }
//    }

    private void submitNonce(String payment_method_nonce) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:3000")  // http://www.example.com
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("AmountDebit", "");
        jsonObject.addProperty("PaymentNonce",payment_method_nonce);

        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<ResponseBody> call1=request.getPayment(jsonObject);
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }

        });
    }

    public void onBraintreeSubmit(String token) {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(token);
        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
    }
}
