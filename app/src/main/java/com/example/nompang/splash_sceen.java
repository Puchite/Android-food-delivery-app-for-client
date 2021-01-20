package com.example.nompang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class splash_sceen extends AppCompatActivity {
    TextView text;
    ImageView logo;
    int index;
    CharSequence charSequence;
    long delay = 200;
    Handler handle = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_sceen);
        getSupportActionBar().hide();
        text = findViewById(R.id.name_logo);


        logo = findViewById(R.id.logo_splash);
        Picasso.get().load(R.drawable.icond).fit().into(logo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(splash_sceen.this,MainActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();

            }
        },3000);
    }

}