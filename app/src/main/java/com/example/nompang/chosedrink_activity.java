package com.example.nompang;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nompang.Prevalent.Prevalent;
import com.example.nompang.models.product;
import com.example.nompang.save.Productviewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import io.paperdb.Paper;

public class chosedrink_activity extends AppCompatActivity {

    RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton mainButton, editProfileButton, homeButton,logOutbutton,informationButton,cartButton;
    private Animation openFloatAni,closeFloatAni;
    private boolean isOpen;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    private  RecyclerView recyclerView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosedrink_activity);
        ref = FirebaseDatabase.getInstance().getReference().child("Product").child("Drinks");
        recyclerView1 =findViewById(R.id.recycle_menu);
        recyclerView1.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(layoutManager);
        mAuth = FirebaseAuth.getInstance();
        mainButton = findViewById(R.id.main);
        editProfileButton = findViewById(R.id.editProfile);
        homeButton = findViewById(R.id.home);
        logOutbutton = findViewById(R.id.logOut);
        informationButton = findViewById(R.id.information);
        cartButton = findViewById(R.id.cart);
        openFloatAni = AnimationUtils.loadAnimation(chosedrink_activity.this,R.anim.floating_open);
        closeFloatAni = AnimationUtils.loadAnimation(chosedrink_activity.this,R.anim.floating_close);

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

                AlertDialog.Builder builder = new AlertDialog.Builder(chosedrink_activity.this);
                builder.setMessage("ต้องการออกจากระบบใช่หรือไม่");
                builder.setCancelable(true);
                builder.setNegativeButton("ใช่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent logout = new Intent(getApplicationContext(),MainActivity.class);
                        Paper.book().destroy();
                        startActivities(new Intent[]{logout});
                        Toast.makeText(chosedrink_activity.this, "ออกจากระบบสำเร็จ", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<product>options =
                new FirebaseRecyclerOptions.Builder<product>()
                        .setQuery(ref,product.class).build();
        FirebaseRecyclerAdapter<product, Productviewholder> adapter =
                new FirebaseRecyclerAdapter<product, Productviewholder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull Productviewholder productviewholder, int i, @NonNull product product) {
//                        if(product.getStatus().equals("stock")){
                        productviewholder.ProductName.setText(product.getName());
                        productviewholder.Productdescription.setText(" รายละเอียด : " + product.getDescription());
                        productviewholder.Productstatus.setText("สถานะ : " + product.getStatus());
                        productviewholder.ProductPrice.setText("ราคา : " + product.getPrice() + " บาท");

                        Picasso.get().load(product.getImageuri()).into(productviewholder.ProductImage);
                        productviewholder.ProductImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                                        .child("Product").child("Drinks");
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String UID = getRef(i).getKey();
                                        String STATUS = snapshot.child(UID).child("status").getValue().toString();
                                        if (STATUS.equals("stock")) {
                                            product pro = snapshot.child(UID).getValue(product.class);
                                            Prevalent.currentProduct = pro;
                                            Intent it = new Intent(getApplicationContext(), CustomizeDrink.class);
                                            startActivity(it);
                                        } else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(chosedrink_activity.this);
                                            builder.setMessage("out of stock");
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
                        });
//                        }
                    }
                    @NonNull
                    @Override
                    public Productviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        Productviewholder holder = new Productviewholder(view);
                        return holder;
                    }
                };
        recyclerView1.setAdapter(adapter);
        adapter.startListening();
    }
};