package com.example.nompang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class chosedrink_activity extends AppCompatActivity {
    ImageButton d1,d2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosedrink_activity);
        d1=findViewById(R.id.d1);
        d2=findViewById(R.id.d2);
//        d1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1=new Intent(getApplicationContext(),test.class);
//                startActivity(intent1);
//            }
//        });
//        d2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent2=new Intent(getApplicationContext(),test.class);
//                startActivity(intent2);
//            }
//        });
    }
}