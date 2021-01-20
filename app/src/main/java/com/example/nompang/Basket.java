package com.example.nompang;

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
import com.example.nompang.BasketVIewHolder;
import com.example.nompang.save.Productviewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    int CCCheck = 0;
    private Button ConfirmedBtn;
    private TextView TotalPrice;
    public static int total = 0;
    int aam;
    int totalz = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.recycle_basket_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        TotalPrice = findViewById(R.id.TotalPrice);
        ConfirmedBtn = findViewById(R.id.ConfirmedBtn_basket);

        ConfirmedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });


        final DatabaseReference refr = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentonlineUsers.getName());
        refr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if( snapshot.child("amount").exists()){
                    aam = Integer.parseInt(snapshot.child("amount").getValue().toString());
                    System.out.println(aam);
                    System.out.println("-ขขขขขขขขขขขขขขขขขขขขขขขขขขขขขขขขขขขขขขขขขขข");
                }
               else{
                   aam =0;
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
        System.out.println("11");
        final DatabaseReference cartreft = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentonlineUsers.getName())
                .child("product");
        final DatabaseReference A = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentonlineUsers.getName())
                .child("product");

        FirebaseRecyclerOptions<Cart>options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartreft,Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter
                = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Cart cart) {
                System.out.println("6");

                cartViewHolder.productname.setText(cart.getName());
                System.out.println(cart.getPrice());
                System.out.println(cart.getPrice());
                System.out.println("000000000000");
                System.out.println(cart.getPrice());
                System.out.println(cart.getPrice());

                cartViewHolder.ProductAmount.setText(cart.getAmount());
//                System.out.println("here"+total);
//                System.out.println((Integer.parseInt(cart.getPrice())*(Integer.parseInt(cart.amount))));

                CCCheck = CCCheck + (Integer.parseInt(cart.getPrice())*(Integer.parseInt(cart.amount)));
                totalz = totalz + (Integer.parseInt(cart.getPrice())*(Integer.parseInt(cart.amount)));
                System.out.println("total = " + total);
                Picasso.get().load(cart.getImageuri()).into(cartViewHolder.productimager);
                TotalPrice.setText(String.valueOf(CCCheck)+"baht");

                cartViewHolder.deletebut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uid = getRef(i).getKey();
                        cartreft.child(uid).removeValue();
                        A.child(uid).removeValue();
                        startActivity(new Intent(Basket.this,Basket.class));
                    }
                });
                System.out.println("3");
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                System.out.println("4");
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_item_layout, parent, false);
//                deletebut  = itemView.findViewById(R.id.deletebtn);
                CartViewHolder  holder = new  CartViewHolder(view);
                System.out.println("5");
                return  holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        System.out.println("6666");
    }

    private void check() {
        if(Prevalent.currentonlineUsers.getLocation().equals("")){
            Toast.makeText(this, "กรุณาใส่ที่อยู่ในหน้า profile ก่อน", Toast.LENGTH_SHORT).show();
        }
        else{
            total = CCCheck;
            CCCheck = 0;
            confirmorder();
        }
    }

    private void confirmorder() {
        final DatabaseReference datareference = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(Prevalent.currentonlineUsers.getName());


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
                            .child(Prevalent.currentonlineUsers.getName()).child("product").removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        Toast.makeText(Basket.this, "confirm order successful", Toast.LENGTH_SHORT).show();
                                        Intent intent =new Intent(Basket.this,paymentActivity.class);
//                                        intent.putExtra("pricez",CCCheck);
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

