package com.example.nompang;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nompang.Prevalent.Prevalent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import io.paperdb.Paper;
import okhttp3.internal.cache.DiskLruCache;

public class chosenompang_activity extends AppCompatActivity {

    NumberPicker numberpicker;
    RadioGroup rg1,rg2;
    Button button,button2;
    TextView show,show2,priceNom;
    ImageView image1,image2;
    NumberPicker n1,n2;
    String V1,V2,V3,V4="0",V5="0",V6,V7;
    public String name="",name2="",name3="",name4="";
    public int Value1,Value2,Value3,Value4,Value5,Value6;
    Date currenttime= Calendar.getInstance().getTime();
    String format1= DateFormat.getTimeInstance().format(currenttime);
    String format2= DateFormat.getDateInstance().format(currenttime);
    EditText e1,e2;
    public int sum1=0,sum2=0,sum3=0,allsum1=0,allsum2=0;
    int amount1=0,amount2=0;
    private FloatingActionButton mainButton, editProfileButton, homeButton,logOutbutton,informationButton,cartButton;
    private Animation openFloatAni,closeFloatAni;
    private boolean isOpen;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosenompang_activity);
        rg1=findViewById(R.id.rg1);
        rg2=findViewById(R.id.rg2);
        button=findViewById(R.id.button);
        button2=findViewById(R.id.button2);
        show=findViewById(R.id.show);
        show2=findViewById(R.id.show2);
        priceNom=findViewById(R.id.priceNom);
        image1=findViewById(R.id.image1);
        n1=findViewById(R.id.N1);
        n2=findViewById(R.id.N2);
        image2=findViewById(R.id.image2);
        e1=findViewById(R.id.comment1);
        e2=findViewById(R.id.comment2);

        n1.setMinValue(0);n2.setMinValue(0);
        n1.setMaxValue(10);n2.setMaxValue(10);
        priceNom.setText("");
        show.setText("");
        show2.setText("");

        n1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                amount1=newVal;
                V4=String.valueOf(amount1);
            }
        });

        n2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                amount2=newVal;
                V5=String.valueOf(amount2);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rg1.clearCheck();
                rg2.clearCheck();
                show.setText("");
                show2.setText("");
                priceNom.setText("");
                sum3=0;
                allsum1=0;
                allsum2=0;
                name="";
                name2="";
                name3="";
                name4="";
                e1.setText("");
                e2.setText("");
                image1.setImageDrawable(getResources().getDrawable(R.drawable.plate));
                image2.setImageDrawable(getResources().getDrawable(R.drawable.plate));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.equals("") && name2.equals("") || amount1==(0) && amount2==(0) || V4.equals("0") && V5.equals("0"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(chosenompang_activity.this);
                    builder.setMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
                    builder.setCancelable(true);

                    builder.setPositiveButton("ปิด", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else {
                    name3=e1.getText().toString().trim();
                    name4=e2.getText().toString().trim();
                    allsum1=sum1*amount1;
                    allsum2=sum2*amount2;
                    sum3=cal(allsum1,allsum2);
                    V3=String.valueOf(sum3);
                    V6=String.valueOf(allsum1);
                    V7=String.valueOf(allsum2);
                    SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(chosenompang_activity.this);
                    SharedPreferences pref2= PreferenceManager.getDefaultSharedPreferences(chosenompang_activity.this);
                    pref.edit().putString("",name).apply();
                    pref2.edit().putString("",name2).apply();
                    Log.d("ขนมปัง ",name3);
                    Log.d("สังขยา ",name4);

                    final DatabaseReference roota,rootb,earth;
                    roota= FirebaseDatabase.getInstance().getReference();
                    rootb=FirebaseDatabase.getInstance().getReference();
                    earth=FirebaseDatabase.getInstance().getReference();
                    roota.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            HashMap<String,Object> userdataMap =new HashMap<>();
                            if(snapshot.child("Product").child("Bread").child("1").child("status").getValue().toString().equals("stock") && amount1>=1){
                                userdataMap.put("imageuri","https://i.pinimg.com/736x/a7/7d/06/a77d062959b1aee044bc332416af8e90.jpg");
                                userdataMap.put("name",name);
                                userdataMap.put("amount",V4);
                                userdataMap.put("price",V1);
                                userdataMap.put("total",V6);
                                userdataMap.put("description",name3);
                                userdataMap.put("Date",format2);
                                userdataMap.put("Time",format1);
                                roota.child("Users").child(Prevalent.currentonlineUsers.getName()).child("product").child("Bread with sangkaya").updateChildren(userdataMap);
                                roota.child("Orders").child(Prevalent.currentonlineUsers.getName()).child("product").child("Bread with sangkaya").updateChildren(userdataMap);
                                Toast.makeText(chosenompang_activity.this, "ใส่ตะกร้าแล้ว", Toast.LENGTH_SHORT).show();
                            }
                            else if(snapshot.child("Product").child("Bread").child("1").child("status").getValue().toString().equals("out of stock")){
                                AlertDialog.Builder builder = new AlertDialog.Builder(chosenompang_activity.this);
                                builder.setMessage("สินค้าหมด");
                                builder.setCancelable(true);

                                builder.setPositiveButton("ปิด", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    rootb.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            HashMap<String,Object> userdataMap2 =new HashMap<>();
                            if(snapshot.child("Product").child("Bread").child("2").child("status").getValue().toString().equals("stock") && amount2>=1){
                                userdataMap2.put("imageuri","https://i.pinimg.com/564x/36/b7/1f/36b71f1ca2732ab5fea095240f08eef0.jpg");
                                userdataMap2.put("name",name2);
                                userdataMap2.put("amount",V5);
                                userdataMap2.put("price",V2);
                                userdataMap2.put("total",V7);
                                userdataMap2.put("description",name4);
                                userdataMap2.put("Date",format2);
                                userdataMap2.put("Time",format1);
                                roota.child("Users").child(Prevalent.currentonlineUsers.getName()).child("product").child("sangkaya").updateChildren(userdataMap2);
                                roota.child("Orders").child(Prevalent.currentonlineUsers.getName()).child("product").child("sangkaya").updateChildren(userdataMap2);
                                Toast.makeText(chosenompang_activity.this, "ใส่ตะกร้าแล้ว", Toast.LENGTH_SHORT).show();
                            }
                            else if(snapshot.child("Product").child("Bread").child("2").child("status").getValue().toString().equals("out of stock") ){
                                AlertDialog.Builder builder = new AlertDialog.Builder(chosenompang_activity.this);
                                builder.setMessage("สินค้าหมด");
                                builder.setCancelable(true);

                                builder.setPositiveButton("ปิด", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


            }
        });
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio1:
                        image1.setImageDrawable(getResources().getDrawable(R.drawable.nom2));
                        show2.setText("มี 6 ชิ้น");
                        priceNom.setText("20 บาท");
                        Value1=20;
                        sum1=Value1;;
                        V1=String.valueOf(Value1);
                        name="ขนมปังชุดเล็ก";
                        break;
                    case R.id.radio2:
                        image1.setImageDrawable(getResources().getDrawable(R.drawable.nom3));
                        show2.setText("มี 10 ชิ้น");
                        priceNom.setText("25 บาท");
                        Value2=25;
                        sum1=Value2;
                        V1=String.valueOf(Value2);
                        name="ขนมปังชุดกลาง";
                        break;
                    case R.id.radio3:
                        image1.setImageDrawable(getResources().getDrawable(R.drawable.nom1));
                        show2.setText("มี 15 ชิ้น");
                        priceNom.setText("30 บาท");
                        Value3=30;
                        sum1=Value3;
                        V1=String.valueOf(Value3);
                        name="ขนมปังชุดใหญ่";
                        break;
                }
            }
        });

        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId2) {
                switch(checkedId2){
                    case R.id.radio4:
                        image2.setImageDrawable(getResources().getDrawable(R.drawable.tuay1));
                        show.setText("15 บาท");
                        Value4=15;
                        sum2=Value4;
                        V2=String.valueOf(Value4);
                        name2="สังขยาชุดเล็ก";
                        break;
                    case R.id.radio5:
                        image2.setImageDrawable(getResources().getDrawable(R.drawable.tuay1));
                        show.setText("20 บาท");
                        Value5=20;
                        sum2=Value5;
                        V2=String.valueOf(Value5);
                        name2="สังขยาชุดกลาง";
                        break;
                    case R.id.radio6:
                        image2.setImageDrawable(getResources().getDrawable(R.drawable.tuay1));
                        show.setText("25 บาท");
                        Value6=25;
                        sum2=Value6;
                        V2=String.valueOf(Value6);
                        name2="สังขยาชุดใหญ่";
                        break;
                }
            }
        });





        mainButton = findViewById(R.id.main);
        editProfileButton = findViewById(R.id.editProfile);
        homeButton = findViewById(R.id.home);
        logOutbutton = findViewById(R.id.logOut);
        informationButton = findViewById(R.id.information);
        cartButton = findViewById(R.id.cart);
        openFloatAni = AnimationUtils.loadAnimation(chosenompang_activity.this,R.anim.floating_open);
        closeFloatAni = AnimationUtils.loadAnimation(chosenompang_activity.this,R.anim.floating_close);

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

                AlertDialog.Builder builder = new AlertDialog.Builder(chosenompang_activity.this);
                builder.setMessage("ต้องการออกจากระบบใช่หรือไม่");
                builder.setCancelable(true);
                builder.setNegativeButton("ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent logout = new Intent(getApplicationContext(),MainActivity.class);
                        Paper.book().destroy();
                        startActivities(new Intent[]{logout});
                        Toast.makeText(chosenompang_activity.this, "ออกจากระบบสำเร็จ", Toast.LENGTH_SHORT).show();
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


    public int cal(int x,int y){
        int Sum=x+y;
        return Sum;
    }




}