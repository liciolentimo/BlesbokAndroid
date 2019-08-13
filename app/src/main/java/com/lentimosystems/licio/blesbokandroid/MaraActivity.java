package com.lentimosystems.licio.blesbokandroid;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MaraActivity extends AppCompatActivity {
    Button btn_book_mara;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mara);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_book_mara = (Button)findViewById(R.id.btn_book_mara);
        btn_book_mara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MaraActivity.this,PaymentActivity.class);
                startActivity(intent);
            }
        });
    }

}
