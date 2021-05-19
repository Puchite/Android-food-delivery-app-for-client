package com.example.nompang;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nompang.Prevalent.Prevalent;
import com.example.nompang.models.product;
//import com.example.nompang.BasketVIewHolder;
import com.example.nompang.save.Productviewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Basket extends AppCompatActivity {

    private TextView NameDrink;

    private ImageView ImageDrink;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    int CCCheck = 0;int co = 0;
    private Button ConfirmedBtn;
    private TextView TotalPrice;
    private String[] ar = new String[100];
    public static int total = 0;
    int aam;
    private FirebaseAuth mAuth;
    int totalz = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        recyclerView = findViewById(R.id.recycle_basket_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        TotalPrice = findViewById(R.id.TotalPrice);
        ConfirmedBtn = findViewById(R.id.ConfirmedBtn_basket);
        mAuth = FirebaseAuth.getInstance();
        ConfirmedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });

        final DatabaseReference refr = FirebaseDatabase.getInstance().getReference().child("Orders").child(mAuth.getCurrentUser().getUid());
        refr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if( snapshot.child("amount").exists()){
                    aam = Integer.parseInt(snapshot.child("amount").getValue().toString());;
                    //amount of product in data
                }
               else{
                   aam = 0;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference cartreft = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid())
                .child("product");
        final DatabaseReference A = FirebaseDatabase.getInstance().getReference().child("Orders").child(mAuth.getCurrentUser().getUid())
                .child("product");
        CCCheck = 0;
        FirebaseRecyclerOptions<Cart>options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartreft,Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter
                = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Cart cart) {

                String nameUID = getRef(i).getKey();
                if(nameUID.compareTo(("ขนมปังชุดใหญ่")) == 0 ||
                        nameUID.compareTo(("ขนมปังชุดกลาง")) == 0 ||
                        nameUID.compareTo(("ขนมปังชุดเล็ก")) == 0 ||
                        nameUID.compareTo(("สังขยาชุดใหญ่")) == 0 ||
                        nameUID.compareTo(("สังขยาชุดกลาง")) == 0 ||
                        nameUID.compareTo(("สังขยาชุดเล็ก")) == 0 ){
                    // Set text for UI Basket //
                    cartViewHolder.productname.setText(cart.getName());
                    cartViewHolder.ProductAmount.setText(cart.getAmount());
                    cartViewHolder.ProductSweet.setText("");
                    cartViewHolder.ProductType.setText("");
                    ar[co] = nameUID;
                    co++;
                    CCCheck = CCCheck + (Integer.parseInt(cart.getPrice())*(Integer.parseInt(cart.amount)));
                    totalz = totalz + (Integer.parseInt(cart.getPrice())*(Integer.parseInt(cart.amount)));
                    Picasso.get().load(cart.getImageuri()).into(cartViewHolder.productimager);
                    TotalPrice.setText("ราคารวม = "+String.valueOf(CCCheck)+" บาท");

                    cartViewHolder.deletebut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String uid = getRef(i).getKey();
                            cartreft.child(uid).removeValue();
                            A.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int s = Integer.parseInt(snapshot.child(uid).child("amount").getValue().toString());
                                    int t = Integer.parseInt(cart.amount);
                                    if(s-t>0){

                                        HashMap<String, Object> userdataMapaa = new HashMap<>();
                                        userdataMapaa.put("amount",String.valueOf(Integer.parseInt(snapshot.child(uid).
                                                child("amount").getValue().toString())-Integer.parseInt(cart.amount)));
                                        A.child(uid).updateChildren(userdataMapaa);
                                    }
                                    else{

                                        A.child(uid).removeValue();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
//                                A.child(uid).removeValue();
                            startActivity(new Intent(Basket.this,Basket.class));
                        }
                    });
                }
                else {
                    cartViewHolder.productname.setText(cart.getName());
                    cartViewHolder.ProductAmount.setText(cart.getAmount());
                    cartViewHolder.ProductSweet.setText(" ๐ ความหวาน : "+cart.getSweet());
                    cartViewHolder.ProductType.setText("๐ ประเภท : "+cart.getType());
                    CCCheck = CCCheck + (Integer.parseInt(cart.getPrice())*(Integer.parseInt(cart.amount)));
                    totalz = totalz + (Integer.parseInt(cart.getPrice())*(Integer.parseInt(cart.amount)));
                    Picasso.get().load(cart.getImageuri()).into(cartViewHolder.productimager);
                    TotalPrice.setText("ราคารวม = "+String.valueOf(CCCheck)+" บาท");
                    cartViewHolder.deletebut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String uid = getRef(i).getKey();
                            cartreft.child(uid).removeValue();
                            A.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int s = Integer.parseInt(snapshot.child(uid).child("amount").getValue().toString());
                                    int t = Integer.parseInt(cart.amount);
                                    if(s-t>0){
                                        HashMap<String, Object> userdataMapaa = new HashMap<>();
                                        userdataMapaa.put("amount",String.valueOf(Integer.parseInt(snapshot.child(uid).child("amount").
                                                getValue().toString())-Integer.parseInt(cart.amount)));
                                        A.child(uid).updateChildren(userdataMapaa);
                                    }
                                    else{
                                        A.child(uid).removeValue();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            startActivity(new Intent(Basket.this,Basket.class));
                        }
                    });
                }

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_item_layout, parent, false);
                CartViewHolder  holder = new  CartViewHolder(view);

                return  holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    private void check() {
        if(Prevalent.currentonlineUsers.getLocation().equals("")){
            Toast.makeText(this, "กรุณาใส่ที่อยู่ในหน้าโปร์ไฟล์ก่อน", Toast.LENGTH_SHORT).show();
        }
        else{

            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(!snapshot.child("product").exists()){
                        Toast.makeText(Basket.this, "ไม่มีสินค้าในตระกร้า", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        total = CCCheck;
                        CCCheck = 0;
                        confirmorder();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    private void confirmorder() {
        final DatabaseReference datareference = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(mAuth.getCurrentUser().getUid());


        HashMap<String, Object> usermap = new HashMap<>();
        usermap.put("name",Prevalent.currentonlineUsers.getName());
        usermap.put("amount",String.valueOf(totalz+aam));
        usermap.put("location",Prevalent.currentonlineUsers.getLocation());
        usermap.put("phone",Prevalent.currentonlineUsers.getPhone());

        datareference.updateChildren(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference().child("Users")
                            .child(mAuth.getCurrentUser().getUid()).child("product").removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        Toast.makeText(Basket.this, "confirm order successful", Toast.LENGTH_SHORT).show();
                                        Intent intent =new Intent(Basket.this,paymentActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }
    public void onBackPressed() {
        Intent it = new Intent(getApplicationContext(),home_activity.class);
        startActivity(it);
    }
}

