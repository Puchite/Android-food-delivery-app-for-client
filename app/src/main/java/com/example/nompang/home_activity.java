package com.example.nompang;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.*;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Process;

import io.paperdb.Paper;

public class home_activity extends AppCompatActivity {
    private long presstime;
    FloatingActionButton mainButton, editProfileButton, homeButton,logOutbutton,informationButton,cartButton;
    private Animation openFloatAni,closeFloatAni;
    boolean isOpen;
    private  Toast backtoast;
    Button order,orderdrink,recommend;
    ImageView image1;
    int curent_img;

    @Override
    public void onBackPressed() {

        if(presstime+2000>System.currentTimeMillis()){
            backtoast.cancel();
            finishAffinity();

            System.exit(0);

            super.onBackPressed();
            return;
        }
        else{
            backtoast =  Toast.makeText(this, "กดอีกครั้งเพื่อออก", Toast.LENGTH_SHORT);
            backtoast.show();
        }
        presstime = System.currentTimeMillis();
    }

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
                Intent intent3 = new Intent(getApplicationContext(),ShowRecomActivity.class);
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
        cartButton = findViewById(R.id.cart);

        editProfileButton.setVisibility(View.INVISIBLE);
        homeButton.setVisibility(View.INVISIBLE);
        logOutbutton.setVisibility(View.INVISIBLE);
        informationButton.setVisibility(View.INVISIBLE);
        cartButton.setVisibility(View.INVISIBLE);

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
                    cartButton.startAnimation(closeFloatAni);

                    isOpen = false;
                }
                else
                {
                    editProfileButton.startAnimation(openFloatAni);
                    homeButton.startAnimation(openFloatAni);
                    logOutbutton.startAnimation(openFloatAni);
                    informationButton.startAnimation(openFloatAni);
                    cartButton.startAnimation(openFloatAni);

                    isOpen = true;
                }
            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCartPage = new Intent(getApplicationContext(),Basket.class);
                startActivity(goToCartPage);
            }
        });

        logOutbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(home_activity.this);
                builder.setMessage("ต้องการออกจากระบบใช่หรือไม่");
                builder.setCancelable(true);
                builder.setNegativeButton("ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent logout = new Intent(getApplicationContext(),MainActivity.class);
                        Paper.book().destroy();
                        startActivities(new Intent[]{logout});
                        Toast.makeText(home_activity.this, "ออกจากระบบสำเร็จ", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setPositiveButton("ปิด", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

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