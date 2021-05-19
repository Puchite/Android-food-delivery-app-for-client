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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
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
    private TextView productName , productPrice,showVolume;
    private Spinner productSpinner;
    private RadioGroup productRadiogroup ;
    private FirebaseAuth mAuth;
    private RadioButton productRadioButton1, productRadioButton2 ;
    private EditText productDescription ;
    private Button productConfirmed,Volumeup,Volumedown;
    public String amount = "0";
    public int A = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_drink);

        mainButton = findViewById(R.id.main);
        editProfileButton = findViewById(R.id.editProfile);
        homeButton = findViewById(R.id.home);
        logOutbutton = findViewById(R.id.logOut);
        Volumeup=findViewById(R.id.Volumeup);
        Volumedown=findViewById(R.id.Volumedown);
        showVolume=findViewById(R.id.VolumeP);
        informationButton = findViewById(R.id.information);
        cartButton = findViewById(R.id.cart);
        openFloatAni = AnimationUtils.loadAnimation(CustomizeDrink.this,R.anim.floating_open);
        closeFloatAni = AnimationUtils.loadAnimation(CustomizeDrink.this,R.anim.floating_close);
        productImage = findViewById(R.id.product_image_details);
        productName = findViewById(R.id.product_name_details);
        productSpinner = findViewById(R.id.product_spinner);
        productRadiogroup = findViewById(R.id.product_radioGroup);
        productDescription = findViewById(R.id.product_description_details);
        productConfirmed = findViewById(R.id.product_Confirm);
        productPrice = findViewById(R.id.product_price_details);
        Sweetness = findViewById(R.id.product_spinner);
        Type = "เย็น";
        editProfileButton.setVisibility(View.INVISIBLE);
        homeButton.setVisibility(View.INVISIBLE);
        logOutbutton.setVisibility(View.INVISIBLE);
        informationButton.setVisibility(View.INVISIBLE);
        cartButton.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();
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

            }
        });

        RadioButton Hot = findViewById(R.id.product_radioButton2);
        Hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hot = productRadiogroup.getCheckedRadioButtonId();
                productRadioButton2 = findViewById(hot);
                Type = productRadioButton2.getText().toString();

            }
        });

        productConfirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IT = new Intent(getApplicationContext(),CustomizeDrink.class);
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


                    UpData();
                    Toast.makeText(CustomizeDrink.this, "ใส่ตระกร้าสำเร็จ", Toast.LENGTH_SHORT).show();
                    finish();
                }
                Prevalent.currentProduct.setName(productName.getText().toString());

            }
        });

        Picasso.get().load(Prevalent.currentProduct.getImageuri()).into(productImage);
        productName.setText(Prevalent.currentProduct.getName());
        productPrice.setText("ราคา : "+Prevalent.currentProduct.getPrice()+" บาท");

        Volumeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(A>=0&&A<=9){
                    A+=1;
                    amount=String.valueOf(A);
                    showVolume.setText(amount);
                }
            }
        });
        Volumedown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(A<=0){
                    A=0;
                    amount=String.valueOf(A);
                    showVolume.setText(amount);
                }
                else{
                    A-=1;
                    amount=String.valueOf(A);
                    showVolume.setText(amount);
                }
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
                    if(!snapshot.child("Users").child(mAuth.getCurrentUser().getUid()).child("product").child(productName.getText().toString()+Type+sweet)
                        .child("temp").exists()){
                        userdataMap.put("temp",amount);
                        Rootref.child("Users").child(mAuth.getCurrentUser().getUid()).child("product")
                            .child(productName.getText().toString()+Type+sweet).updateChildren(userdataMap);
                        if(snapshot.child("Orders").child(mAuth.getCurrentUser().getUid()).child("product").child(productName.getText().toString()+Type+
                            sweet).exists()){
                            int temp = Integer.parseInt(snapshot.child("Orders").child(mAuth.getCurrentUser().getUid()).
                                child("product").child(productName.getText().toString()+Type+ sweet).child("amount").getValue().toString());
                            int ramount = Integer.parseInt(amount)+temp;
                            HashMap<String,Object> userdata = new HashMap<>();
                            userdata.put("amount",String.valueOf(ramount));
                            Rootref.child("Orders").child(mAuth.getCurrentUser().getUid()).child("product")
                                .child(productName.getText().toString()+Type+sweet).updateChildren(userdata);

                        }
                        else{
                            Rootref.child("Orders").child(mAuth.getCurrentUser().getUid()).child("product")
                                    .child(productName.getText().toString()+Type+sweet).updateChildren(userdataMap);
                        }
                    }
                    else{
                        String am = String.valueOf(Integer.parseInt(amount)-Integer.parseInt(snapshot.child("Users").child(mAuth.getCurrentUser().getUid()).child("product").
                                child(productName.getText().toString()+Type+sweet).child("temp").getValue().toString()));


                        userdataMap.put("temp",amount);
                            Rootref.child("Users").child(mAuth.getCurrentUser().getUid()).child("product")
                                    .child(productName.getText().toString()+Type+sweet).updateChildren(userdataMap);
                        if(snapshot.child("Orders").child(mAuth.getCurrentUser().getUid()).child("product").child(productName.getText().toString()+Type+
                            sweet).exists()){
                            HashMap<String,Object> userdata = new HashMap<>();
                            int ramount = Integer.parseInt(snapshot.child("Orders").child(mAuth.getCurrentUser().getUid()).
                                child("product").child(productName.getText().toString()+Type+ sweet).child("amount").getValue().toString())+Integer.parseInt(am);
                            userdata.put("amount",String.valueOf(ramount));
                            Rootref.child("Orders").child(mAuth.getCurrentUser().getUid()).child("product")
                                .child(productName.getText().toString()+Type+sweet).updateChildren(userdata);

                    }

                }

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