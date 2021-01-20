package com.example.nompang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nompang.Prevalent.Prevalent;
import com.example.nompang.models.product;
import com.example.nompang.save.Productviewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import io.paperdb.Paper;

public class CustomizeDrink extends AppCompatActivity implements AdapterView.OnItemSelectedListener,NumberPicker.OnValueChangeListener {

    private FloatingActionButton mainButton, editProfileButton, homeButton, logOutbutton, informationButton, cartButton;
    private Animation openFloatAni,closeFloatAni;
    private boolean isOpen;
    private Spinner Sweetness;
    private NumberPicker productAmount;
    private String Type;
    public String sweet;
    private ImageView productImage;
    private TextView productName , productPrice;
    private Spinner productSpinner;
    private RadioGroup productRadiogroup ;
    private RadioButton productRadioButton1, productRadioButton2 ;
    private EditText productDescription ;
    private Button productConfirmed;
    private String amount = "0";
    private int A = 0;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_drink);

        mainButton = findViewById(R.id.main);
        editProfileButton = findViewById(R.id.editProfile);
        homeButton = findViewById(R.id.home);
        logOutbutton = findViewById(R.id.logOut);
        informationButton = findViewById(R.id.information);
        cartButton = findViewById(R.id.cart);
        openFloatAni = AnimationUtils.loadAnimation(CustomizeDrink.this,R.anim.floating_open);
        closeFloatAni = AnimationUtils.loadAnimation(CustomizeDrink.this,R.anim.floating_close);

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

                AlertDialog.Builder builder = new AlertDialog.Builder(CustomizeDrink.this);
                builder.setMessage("ต้องการออกจากระบบใช่หรือไม่");
                builder.setCancelable(true);
                builder.setNegativeButton("ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent logout = new Intent(getApplicationContext(),MainActivity.class);
                        Paper.book().destroy();
                        startActivities(new Intent[]{logout});
                        Toast.makeText(CustomizeDrink.this, "ออกจากระบบสำเร็จ", Toast.LENGTH_SHORT).show();
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

        productImage = findViewById(R.id.product_image_details);

        productName = findViewById(R.id.product_name_details);

        productSpinner = findViewById(R.id.product_spinner);

        productRadiogroup = findViewById(R.id.product_radioGroup);

        productDescription = findViewById(R.id.product_description_details);

        productConfirmed = findViewById(R.id.product_Confirm);

        productPrice = findViewById(R.id.product_price_details);

        Sweetness = findViewById(R.id.product_spinner);

        productAmount = findViewById(R.id.numberPicker);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.Sweet
                        , android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Sweetness.setAdapter(adapter);
        Sweetness.setOnItemSelectedListener(this);

        RadioButton Cold = findViewById(R.id.product_radioButton1);
        Cold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cold = productRadiogroup.getCheckedRadioButtonId();
                productRadioButton1 = findViewById(cold);
                Type = productRadioButton1.getText().toString();
                System.out.println(Type);
            }
        });

        RadioButton Hot = findViewById(R.id.product_radioButton2);
        Hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hot = productRadiogroup.getCheckedRadioButtonId();
                productRadioButton2 = findViewById(hot);
                Type = productRadioButton2.getText().toString();
                System.out.println(Type);
            }
        });

        productConfirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IT = new Intent(getApplicationContext(),Basket.class);
                if(amount.equals("0"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CustomizeDrink.this);
                    builder.setMessage("กรุณาเลือกระบุจำนวนก่อนใส่ตะกร้า");
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
                    startActivity(IT);
                    UpData();
                }
                Prevalent.currentProduct.setName(productName.getText().toString());
//                System.out.println(Prevalent.currentProduct.getImageuri());
            }
        });

        Picasso.get().load(Prevalent.currentProduct.getImageuri()).into(productImage);
        productName.setText(Prevalent.currentProduct.getName());
        productPrice.setText("Price : "+Prevalent.currentProduct.getPrice());

        productAmount.setMinValue(0);
        productAmount.setMaxValue(10);
        productAmount.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(i == 0){
                    A = newVal;
                    amount=String.valueOf(A);
                }
                else{
                    A = newVal;
                    amount=String.valueOf(A-1);
                    if(A == 0){
                        amount=String.valueOf(10);
                    }
                }
                i++;
            }
        });
    }

    private void UpData(){
        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("name", productName.getText().toString());
                    userdataMap.put("sweet", sweet);
                    userdataMap.put("type", Type);
                    userdataMap.put("description", productDescription.getText().toString());
                    userdataMap.put("price", Prevalent.currentProduct.getPrice().toString());
                    userdataMap.put("imageuri", Prevalent.currentProduct.getImageuri());
                    userdataMap.put("amount", amount);
                    Rootref.child("Users").child(Prevalent.currentonlineUsers.getName()).child("product")
                            .child(productName.getText().toString()).updateChildren(userdataMap);
                    Rootref.child("Orders").child(Prevalent.currentonlineUsers.getName()).child("product")
                        .child(productName.getText().toString()).updateChildren(userdataMap);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sweet = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }
}