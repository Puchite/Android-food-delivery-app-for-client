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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
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

public class chosenompang_activity extends AppCompatActivity {
    RadioGroup radioGroup_Bread, radioGroup_Cream;
    Button button, refresh_button, Increase_Bread, Increase_Cream, Reduce_Bread, Reduce_Cream;
    TextView Show_Price_Cream, Show_Pieces, Show_Price_Bread, Volume1, Volume2;
    ImageView Image_Bread, Image_Cream;
    String Price_Bread, Price_Cream, Show_Amount_Bread = "0", Show_Amount_Cream = "0";
    public String Type_Bread = "", Type_Cream = "", Bread = "", Cream = "" ;
    public int Small_Bread_Price, Medium_Bread_Price, Large_Bread_Price,
            Small_Cream_Price, Medium_Cream_Price, Large_Cream_Price;
    Date Current_Time = Calendar.getInstance().getTime();
    String GetTime = DateFormat.getTimeInstance().format(Current_Time);
    String GetDay = DateFormat.getDateInstance().format(Current_Time);
    EditText e1, e2;
    public int Current_Price_Bread = 0, Current_Price_Cream = 0, Total_Price = 0, Total_Price_Bread = 0, Total_Price_Cream = 0;
    int amount_Bread = 0, amount_Cream = 0;
    private FloatingActionButton mainButton, editProfileButton, homeButton, logOutbutton, informationButton, cartButton;
    private Animation openFloatAni, closeFloatAni;
    private boolean isOpen;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosenompang_activity);
        radioGroup_Bread = findViewById(R.id.rg1);
        radioGroup_Cream = findViewById(R.id.rg2);
        button = findViewById(R.id.button);
        refresh_button = findViewById(R.id.button2);
        Increase_Bread = findViewById(R.id.Increase);
        Increase_Cream = findViewById(R.id.Increase2);
        Reduce_Bread = findViewById(R.id.Reduce);
        Reduce_Cream = findViewById(R.id.Reduce2);
        Volume1 = findViewById(R.id.Amount1);
        Volume2 = findViewById(R.id.Amount2);
        Show_Price_Bread = findViewById(R.id.priceNom);
        Show_Price_Cream = findViewById(R.id.show);
        Show_Pieces = findViewById(R.id.show2);
        Image_Bread = findViewById(R.id.image1);
        Image_Cream = findViewById(R.id.image2);
        e1 = findViewById(R.id.comment1);
        e2 = findViewById(R.id.comment2);
        mAuth = FirebaseAuth.getInstance();
        Show_Price_Bread.setText("");
        Show_Price_Cream.setText("");
        Show_Pieces.setText("");
        Increase_Bread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount_Bread >= 0 && amount_Bread <= 9) {
                    amount_Bread += 1;
                    Show_Amount_Bread = String.valueOf(amount_Bread);
                    Volume1.setText(Show_Amount_Bread);
                }
            }
        });

        Reduce_Bread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount_Bread <= 0){
                    amount_Bread = 0;
                    Show_Amount_Bread = String.valueOf(amount_Bread);
                    Volume1.setText(Show_Amount_Bread);
                }
                else{
                    amount_Bread -= 1;
                    Show_Amount_Bread = String.valueOf(amount_Bread);
                    Volume1.setText(Show_Amount_Bread);
                }
            }
        });

        Increase_Cream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount_Cream >= 0 && amount_Cream <= 9) {
                    amount_Cream += 1;
                    Show_Amount_Cream = String.valueOf(amount_Cream);
                    Volume2.setText(Show_Amount_Cream);
                }
            }
        });

        Reduce_Cream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount_Cream <= 0){
                    amount_Cream = 0;
                    Show_Amount_Cream = String.valueOf(amount_Cream);
                    Volume2.setText(Show_Amount_Cream);
                }
                else{
                    amount_Cream -= 1;
                    Show_Amount_Cream = String.valueOf(amount_Cream);
                    Volume2.setText(Show_Amount_Cream);
                }
            }
        });

        refresh_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioGroup_Bread.clearCheck();
                radioGroup_Cream.clearCheck();

                Show_Price_Cream.setText("");
                Show_Pieces.setText("");
                Show_Price_Bread.setText("");
                amount_Bread = 0;amount_Cream = 0;
                Volume2.setText("");
                Volume1.setText("");
                Total_Price = 0;
                Total_Price_Bread = 0;
                Total_Price_Cream = 0;

                amount_Cream = 0;
                amount_Bread = 0;

                Type_Bread = "";
                Type_Cream = "";

                Bread = "";
                Cream = "";

                e1.setText("");
                e2.setText("");

                Image_Bread.setImageDrawable(getResources().getDrawable(R.drawable.plate));
                Image_Cream.setImageDrawable(getResources().getDrawable(R.drawable.plate));
            }
        });

        radioGroup_Bread.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio1:
                        Image_Bread.setImageDrawable(getResources().getDrawable(R.drawable.nom2));
                        Show_Pieces.setText("มี 6 ชิ้น");
                        Show_Price_Bread.setText("20 บาท");
                        Small_Bread_Price = 20;
                        Current_Price_Bread = Small_Bread_Price;
                        Price_Bread = String.valueOf(Small_Bread_Price);
                        Type_Bread = "ขนมปังชุดเล็ก";
                        break;
                    case R.id.radio2:
                        Image_Bread.setImageDrawable(getResources().getDrawable(R.drawable.nom3));
                        Show_Pieces.setText("มี 10 ชิ้น");
                        Show_Price_Bread.setText("25 บาท");
                        Medium_Bread_Price = 25;
                        Current_Price_Bread = Medium_Bread_Price;
                        Price_Bread = String.valueOf(Medium_Bread_Price);
                        Type_Bread = "ขนมปังชุดกลาง";
                        break;
                    case R.id.radio3:
                        Image_Bread.setImageDrawable(getResources().getDrawable(R.drawable.nom1));
                        Show_Pieces.setText("มี 15 ชิ้น");
                        Show_Price_Bread.setText("30 บาท");
                        Large_Bread_Price = 30;
                        Current_Price_Bread = Large_Bread_Price;
                        Price_Bread = String.valueOf(Large_Bread_Price);
                        Type_Bread = "ขนมปังชุดใหญ่";
                        break;
                }
            }
        });

        radioGroup_Cream.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId2) {
                switch(checkedId2){
                    case R.id.radio4:
                        Image_Cream.setImageDrawable(getResources().getDrawable(R.drawable.tuay1));
                        Show_Price_Cream.setText("15 บาท");
                        Small_Cream_Price = 15;
                        Current_Price_Cream = Small_Cream_Price;
                        Price_Cream = String.valueOf(Small_Cream_Price);
                        Type_Cream = "สังขยาชุดเล็ก";

                        break;

                    case R.id.radio5:
                        Image_Cream.setImageDrawable(getResources().getDrawable(R.drawable.tuay1));
                        Show_Price_Cream.setText("20 บาท");
                        Medium_Cream_Price = 20;
                        Current_Price_Cream = Medium_Cream_Price;
                        Price_Cream = String.valueOf(Medium_Cream_Price);
                        Type_Cream = "สังขยาชุดกลาง";

                        break;

                    case R.id.radio6:
                        Image_Cream.setImageDrawable(getResources().getDrawable(R.drawable.tuay1));
                        Show_Price_Cream.setText("25 บาท");
                        Large_Cream_Price = 25;
                        Current_Price_Cream = Large_Cream_Price;
                        Price_Cream = String.valueOf(Large_Cream_Price);
                        Type_Cream = "สังขยาชุดใหญ่";

                        break;
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Type_Bread.equals("") && Type_Cream.equals("")
                        || amount_Bread == (0) && amount_Cream == (0)
                        || Show_Amount_Bread.equals("0") && Show_Amount_Cream.equals("0")) {
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
                    Total_Price_Bread = Current_Price_Bread * amount_Bread;
                    Total_Price_Cream = Current_Price_Cream * amount_Cream;
                    Total_Price = cal(Total_Price_Bread, Total_Price_Cream);
                final DatabaseReference Root_Update_Data, Root_Cream, Root_Bread;

                    Root_Update_Data = FirebaseDatabase.getInstance().getReference();
                    Root_Cream = FirebaseDatabase.getInstance().getReference();
                    Root_Bread = FirebaseDatabase.getInstance().getReference();

                    Root_Bread.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            HashMap<String,Object> userdataMap = new HashMap<>();
                            HashMap<String,Object> userdataMapAmount = new HashMap<>();

                            if(snapshot.child("Product").child("Bread").child("1").child("status").getValue().toString().equals("stock") && amount_Bread >= 1){
                                userdataMap.put("imageuri", "https://i.pinimg.com/736x/a7/7d/06/a77d062959b1aee044bc332416af8e90.jpg");
                                userdataMap.put("name", Type_Bread);
                                userdataMap.put("amount", Show_Amount_Bread);
                                userdataMap.put("price", Price_Bread);
                                userdataMap.put("total", String.valueOf(Total_Price_Bread));
                                userdataMap.put("description", Bread);
                                userdataMap.put("Date", GetDay);
                                userdataMap.put("Time", GetTime);
                                userdataMap.put("status", "n");

                                if(!snapshot.child("Users").child(mAuth.getCurrentUser().getUid()).child("product").child(Type_Bread.toString()).child("temp").exists()){
                                    userdataMap.put("temp", Show_Amount_Bread);

                                    Root_Update_Data.child("Users").child(mAuth.getCurrentUser().getUid()).child("product").child(Type_Bread.toString()).updateChildren(userdataMap);

                                    if(snapshot.child("Orders").child(mAuth.getCurrentUser().getUid()).child("product").child(Type_Bread.toString()).exists()){
                                        int Old_Amount = Integer.parseInt(snapshot.child("Orders").child(mAuth.getCurrentUser().getUid()).child("product").child(Type_Bread.toString())
                                                .child("amount").getValue().toString());
                                        int Add_Amount = Integer.parseInt(Show_Amount_Bread);
                                        int Latest_Amount = Old_Amount + Add_Amount;

                                        userdataMapAmount.put("amount",String.valueOf(Latest_Amount));

                                        Root_Update_Data.child("Orders").child(mAuth.getCurrentUser().getUid()).child("product").child(Type_Bread.toString()).updateChildren(userdataMapAmount);
                                    }

                                    else{
                                        Root_Update_Data.child("Orders").child(mAuth.getCurrentUser().getUid()).child("product").child(Type_Bread.toString()).updateChildren(userdataMap);
                                    }
                                }

                                else{
                                    String Amount = String.valueOf(Integer.parseInt(Show_Amount_Bread)
                                            - Integer.parseInt(snapshot.child("Users").child(mAuth.getCurrentUser().getUid()).
                                            child("product").child(Type_Bread.toString()).child("temp").getValue().toString()));

                                    userdataMap.put("temp",Show_Amount_Bread);

                                    Root_Update_Data.child("Users").child(mAuth.getCurrentUser().getUid()).child("product")
                                            .child(Type_Bread.toString()).updateChildren(userdataMap);

                                    if(snapshot.child("Orders").child(mAuth.getCurrentUser().getUid()).child("product").child(Type_Bread.toString()).exists()){
                                        HashMap<String,Object> userdata = new HashMap<>();

                                        int Replace_Amount = Integer.parseInt(snapshot.child("Orders").child(mAuth.getCurrentUser().getUid()).
                                                child("product").child(Type_Bread.toString()).child("amount").getValue().toString()) + Integer.parseInt(Amount);

                                        userdata.put("amount",String.valueOf(Replace_Amount));

                                        Root_Update_Data.child("Orders").child(mAuth.getCurrentUser().getUid()).child("product")
                                                .child(Type_Bread.toString()).updateChildren(userdata);
                                    }
                                }
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

                    Root_Cream.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            HashMap<String,Object> userdataMap = new HashMap<>();
                            HashMap<String,Object> userdataMapAmount = new HashMap<>();

                            if(snapshot.child("Product").child("Bread").child("2").child("status").getValue().toString().equals("stock") && amount_Cream >= 1){
                                userdataMap.put("imageuri", "https://i.pinimg.com/564x/36/b7/1f/36b71f1ca2732ab5fea095240f08eef0.jpg");
                                userdataMap.put("name", Type_Cream);
                                userdataMap.put("amount", Show_Amount_Cream);
                                userdataMap.put("price", Price_Cream);
                                userdataMap.put("total", String.valueOf(Total_Price_Cream));
                                userdataMap.put("description", Cream);
                                userdataMap.put("Date", GetDay);
                                userdataMap.put("Time", GetTime);
                                userdataMap.put("status", "n");
                                Root_Update_Data.child("Orders").child(mAuth.getCurrentUser().getUid()).child("product").child(Type_Cream.toString()).updateChildren(userdataMap);

                                if(!snapshot.child("Users").child(mAuth.getCurrentUser().getUid()).child("product").child(Type_Cream.toString())
                                        .child("temp").exists()){
                                    userdataMap.put("temp", Show_Amount_Cream);

                                    Root_Update_Data.child("Users").child(mAuth.getCurrentUser().getUid()).child("product").child(Type_Cream.toString()).updateChildren(userdataMap);

                                    if(snapshot.child("Orders").child(mAuth.getCurrentUser().getUid()).child("product").child(Type_Cream.toString()).exists()){
                                        int Old_Amount = Integer.parseInt(snapshot.child("Orders").child(mAuth.getCurrentUser().getUid()).child("product").child(Type_Cream.toString()).child("amount").getValue().toString());
                                        int Add_Amount = Integer.parseInt(Show_Amount_Cream);
                                        int Latest_Amount = Old_Amount + Add_Amount;

                                        userdataMapAmount.put("amount",String.valueOf(Latest_Amount));
                                        Root_Update_Data.child("Orders").child(mAuth.getCurrentUser().getUid()).child("product")
                                                .child(Type_Cream.toString()).updateChildren(userdataMapAmount);
                                    }
                                }

                                else{
                                    String Amount = String.valueOf(Integer.parseInt(Show_Amount_Cream) - Integer.parseInt(snapshot.child("Users").child(mAuth.getCurrentUser().getUid()).child("product").
                                            child(Type_Cream.toString()).child("temp").getValue().toString()));
                                    userdataMap.put("temp",Show_Amount_Cream);
                                    Root_Update_Data.child("Users").child(mAuth.getCurrentUser().getUid()).child("product")
                                            .child(Type_Cream.toString()).updateChildren(userdataMap);

                                    if(snapshot.child("Orders").child(mAuth.getCurrentUser().getUid()).child("product").child(Type_Cream.toString()).exists()){
                                        HashMap<String,Object> userdata = new HashMap<>();

                                        int Replace_Amount = Integer.parseInt(snapshot.child("Orders").child(mAuth.getCurrentUser().getUid()).
                                                child("product").child(Type_Cream.toString()).child("amount").getValue().toString()) + Integer.parseInt(Amount);

                                        userdata.put("amount",String.valueOf(Replace_Amount));
                                        Root_Update_Data.child("Orders").child(mAuth.getCurrentUser().getUid()).child("product")
                                                .child(Type_Cream.toString()).updateChildren(userdata);
                                    }
                                }
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
                if(isOpen){
                    editProfileButton.startAnimation(closeFloatAni);
                    homeButton.startAnimation(closeFloatAni);
                    logOutbutton.startAnimation(closeFloatAni);
                    informationButton.startAnimation(closeFloatAni);
                    cartButton.startAnimation(closeFloatAni);

                    isOpen = false;
                }
                else{
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

    public int cal(int x, int y){
        int Sum = x + y;
        return Sum;
    }
}