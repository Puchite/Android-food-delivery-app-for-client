package com.example.nompang;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nompang.Prevalent.Prevalent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.paperdb.Paper;

public class paymentActivity extends AppCompatActivity {

    FloatingActionButton mainButton, editProfileButton, homeButton,logOutbutton,informationButton,cartButton;
    private Animation openFloatAni,closeFloatAni;
    boolean isOpen;
    Button buttonHome,buttonShop,comfirm;
    RadioGroup radioGroup1,radioGroup2;
    EditText addressUserPayment_layout;
    TextView addressShopPayment_layout,money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        comfirm = findViewById(R.id.paymentButton);
        money = findViewById(R.id.money);
        buttonHome = findViewById(R.id.radioButtontake_home);
        buttonShop = findViewById(R.id.radioButtonShop);
        radioGroup1 = findViewById(R.id.radioGroupPayment);
        radioGroup2 = findViewById(R.id.radioGroupPayment2);
        addressUserPayment_layout = findViewById(R.id.addressUserPayment);
        addressShopPayment_layout = findViewById(R.id.addressShopPayment);
        addressUserPayment_layout.setVisibility(View.INVISIBLE);
        addressShopPayment_layout.setVisibility(View.INVISIBLE);

        money.setText("จำนวนเงิน "+String.valueOf(Basket.total)+"บาท");

        comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(paymentActivity.this);
                builder.setMessage("การสั่งซื้อสำเร็จแล้ว ขอบคุณที่ใช้บริการ");
                builder.setCancelable(true);
                builder.setPositiveButton("กลับไปหน้าแรก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent backToHome = new Intent(getApplicationContext(),home_activity.class);
//                        Basket.total = 0;
//                        money.setText("0"+"บาท");
                        startActivity(backToHome);
                        finish();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId== R.id.radioButtonShop)
                {
                    addressUserPayment_layout.setVisibility(View.INVISIBLE);
                    addressShopPayment_layout.setVisibility(View.VISIBLE);
                    addressShopPayment_layout.setText("ร้านอยู่ที่ 7 ถนน ลูกหลวง แขวง ดุสิต เขตดุสิต กรุงเทพมหานคร ประเทศไทย 10300 กรุงเทพมหานคร (ถนนลูกหลวงใกล้กับธนาคารไทยพาณิชย์)");
                    addressUserPayment_layout.setText("");

                }
                else if(checkedId== R.id.radioButtontake_home)
                {
                    addressShopPayment_layout.setVisibility(View.INVISIBLE);
                    addressUserPayment_layout.setVisibility(View.VISIBLE);
                    addressUserPayment_layout.setText(Prevalent.currentonlineUsers.getLocation());
                }
            }
        });


        mainButton = findViewById(R.id.main);
        editProfileButton = findViewById(R.id.editProfile);
        homeButton = findViewById(R.id.home);
        logOutbutton = findViewById(R.id.logOut);
        informationButton = findViewById(R.id.information);
        cartButton = findViewById(R.id.cart);
        openFloatAni = AnimationUtils.loadAnimation(paymentActivity.this,R.anim.floating_open);
        closeFloatAni = AnimationUtils.loadAnimation(paymentActivity.this,R.anim.floating_close);

        editProfileButton.setVisibility(View.INVISIBLE);
        homeButton.setVisibility(View.INVISIBLE);
        logOutbutton.setVisibility(View.INVISIBLE);
        informationButton.setVisibility(View.INVISIBLE);
        cartButton.setVisibility(View.INVISIBLE);

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

                AlertDialog.Builder builder = new AlertDialog.Builder(paymentActivity.this);
                builder.setMessage("ต้องการออกจากระบบใช่หรือไม่");
                builder.setCancelable(true);
                builder.setNegativeButton("ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent logout = new Intent(getApplicationContext(),MainActivity.class);
                        Paper.book().destroy();
                        startActivities(new Intent[]{logout});
                        Toast.makeText(paymentActivity.this, "ออกจากระบบสำเร็จ", Toast.LENGTH_SHORT).show();
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