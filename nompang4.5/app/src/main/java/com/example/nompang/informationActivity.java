package com.example.nompang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.paperdb.Paper;


public class informationActivity extends AppCompatActivity {

    private FloatingActionButton mainButton, editProfileButton, homeButton,logOutbutton,informationButton;
    private Animation openFloatAni,closeFloatAni;
    private boolean isOpen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informarion);

        mainButton = findViewById(R.id.main);
        editProfileButton = findViewById(R.id.editProfile);
        homeButton = findViewById(R.id.home);
        logOutbutton = findViewById(R.id.logOut);
        informationButton = findViewById(R.id.information);
        openFloatAni = AnimationUtils.loadAnimation(informationActivity.this,R.anim.floating_open);
        closeFloatAni = AnimationUtils.loadAnimation(informationActivity.this,R.anim.floating_close);

        editProfileButton.setVisibility(View.INVISIBLE);
        homeButton.setVisibility(View.INVISIBLE);
        logOutbutton.setVisibility(View.INVISIBLE);
        informationButton.setVisibility(View.INVISIBLE);
        Paper.init(this);
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
                Intent logout = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(logout);
            }
        });
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(getApplicationContext(),profileActivity.class);
                startActivity(profile);
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getApplicationContext(),home_activity.class);
                startActivity(home);
            }
        });
        informationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent informationpage = new Intent(getApplicationContext(),informationActivity.class);
                startActivity(informationpage);
            }
        });
    }
}
