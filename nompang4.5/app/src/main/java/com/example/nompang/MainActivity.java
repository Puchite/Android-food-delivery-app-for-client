package com.example.nompang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.nompang.Prevalent.Prevalent;
import com.example.nompang.models.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;
import maes.tech.intentanim.CustomIntent;
import okhttp3.internal.cache.DiskLruCache;

public class MainActivity extends AppCompatActivity {
    Button buttonregister,buttoncomfirm;
    private EditText inputusername,inputpassword;
    private ProgressDialog loadingbar;
    private CheckBox rememberme;
    private TextView forgotpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        buttonregister = findViewById(R.id.register);
        buttoncomfirm = findViewById(R.id.comfirm);
        inputusername = findViewById(R.id.input_user);
        inputpassword = findViewById(R.id.input_password);
        loadingbar = new ProgressDialog(this);
        rememberme = (CheckBox)findViewById(R.id.rememberme_chk);
        forgotpass = findViewById(R.id.forgotpsw);


        Paper.init(this);
        String Username = Paper.book().read(Prevalent.Usernamekey);
        String Userpass= Paper.book().read(Prevalent.Userpasskey);
        if(Username!=""&&Userpass!=""){
            if(!TextUtils.isEmpty(Username)&&!TextUtils.isEmpty(Userpass)){
                allowacess(Username,Userpass);

                loadingbar.setMessage("กรุณารอ");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();
            }
        }
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,sendtomail.class));
            }
        });
        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,registerActivity.class);

                startActivity(intent);
                CustomIntent.customType(MainActivity.this,"bottom-to-up");


            }
        });
        buttoncomfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loginuser();
            }
        });
    }

    private void Loginuser() {
        String username = inputusername.getText().toString();
        String password = inputpassword.getText().toString();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"กรุณากรอกชื่อของคุณ",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"กรุณากรอกรหัสผ่านของคุณ",Toast.LENGTH_SHORT).show();
        }
        else{

            loadingbar.setTitle("ลงชื่อเข้าใช้");
            loadingbar.setMessage("กรุณารอ");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            allowacess(username,password);



        }
    }

    private void allowacess(String username, String password) {
        if(rememberme.isChecked()){
            Paper.book().write(Prevalent.Usernamekey,username);
            Paper.book().write(Prevalent.Userpasskey,password);
        }
        final DatabaseReference Rootref,Rootreffer;
        Rootref = FirebaseDatabase.getInstance().getReference();


        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.child("Users").child(username).exists()){


                    Users usersData = snapshot.child("Users").child(username).getValue(Users.class);



                        if(usersData.getPassword().equals(password)){
                            Toast.makeText(MainActivity.this, "ลงชื่อเข้าใช้สำเร็จ", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();

                            Prevalent.currentonlineUsers = usersData;


                            setonline(username);
                            startActivity( new Intent(MainActivity.this,home_activity.class));



                        }
                        else{
                            Toast.makeText(MainActivity.this, "รหัสผ่านไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                            Toast.makeText(MainActivity.this, "กรุณาลองอีกครั้ง", Toast.LENGTH_SHORT).show();
                        }

                }
                else{
                    Toast.makeText(MainActivity.this, "ชื่อนี้ยังไม่มีการลงทะเบียน", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                    Toast.makeText(MainActivity.this, "กรุณาลงทะเบียน", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setonline(String user) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Prevalent.currentonlineUsers.setPhone(snapshot.child("phone").getValue().toString());
                Prevalent.currentonlineUsers.setName(snapshot.child("name").getValue().toString());
                Prevalent.currentonlineUsers.setPassword(snapshot.child("password").getValue().toString());
                Prevalent.currentonlineUsers.setLocation(snapshot.child("location").getValue().toString());
                Prevalent.currentonlineUsers.setRealname(snapshot.child("realname").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}