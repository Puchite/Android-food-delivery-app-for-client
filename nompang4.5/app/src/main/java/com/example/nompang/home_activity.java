package com.example.nompang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;

public class home_activity extends AppCompatActivity {

    FloatingActionButton mainButton, editProfileButton, homeButton,logOutbutton,informationButton;
    private Animation openFloatAni,closeFloatAni;
    boolean isOpen;

    Button order,orderdrink,recommend;
    ImageView image1;
    int curent_img;
    //int[] photo =int[R.drawable.coco2,R.drawable.chanom];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);

        order =findViewById(R.id.orders);
        orderdrink=findViewById(R.id.orderdrink);
        recommend=findViewById(R.id.recommend);
        Paper.init(this);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(), chosenompang_activity.class);
                startActivity(intent1);
            }
        });
        orderdrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(getApplicationContext(),chosedrink_activity.class);
                startActivity(intent2);
            }
        });

        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getApplicationContext(),profileActivity.class);
                startActivity(intent3);
            }
        });

//        setonline();
        //Floating button Action

        openFloatAni = AnimationUtils.loadAnimation(home_activity.this,R.anim.floating_open);
        closeFloatAni = AnimationUtils.loadAnimation(home_activity.this,R.anim.floating_close);

        mainButton = findViewById(R.id.main);
        editProfileButton = findViewById(R.id.editProfile);
        homeButton = findViewById(R.id.home);
        logOutbutton = findViewById(R.id.logOut);
        informationButton = findViewById(R.id.information);

        editProfileButton.setVisibility(View.INVISIBLE);
        homeButton.setVisibility(View.INVISIBLE);
        logOutbutton.setVisibility(View.INVISIBLE);
        informationButton.setVisibility(View.INVISIBLE);

        isOpen = false;

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen)
                {
                    editProfileButton.startAnimation(closeFloatAni);
                    homeButton.startAnimation(closeFloatAni);
                    logOutbutton.startAnimation(closeFloatAni);
                    informationButton.startAnimation(closeFloatAni);

                    isOpen = false;
                }
                else
                {
                    editProfileButton.startAnimation(openFloatAni);
                    homeButton.startAnimation(openFloatAni);
                    logOutbutton.startAnimation(openFloatAni);
                    informationButton.startAnimation(openFloatAni);

                    isOpen = true;
                }
            }
        });

        logOutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().destroy();
                Intent floatLogMain = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(floatLogMain);
            }
        });
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent floatProfileMain = new Intent(getApplicationContext(),profileActivity.class);
                startActivity(floatProfileMain);
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent floatHomeMain = new Intent(getApplicationContext(),home_activity.class);
                startActivity(floatHomeMain);
            }
        });
        informationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent floatInforMain = new Intent(getApplicationContext(),informationActivity.class);
                startActivity(floatInforMain);
            }
        });










    }
//    private void setonline() {
//        final DatabaseReference Rootref;
//        Rootref = FirebaseDatabase.getInstance().getReference();
//
//        Rootref.addValueEventListener(new)
//    }
}