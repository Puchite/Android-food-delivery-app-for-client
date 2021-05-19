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


import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.nompang.Prevalent.Prevalent;
import com.example.nompang.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import io.paperdb.Paper;
import maes.tech.intentanim.CustomIntent;
import okhttp3.internal.cache.DiskLruCache;

public class MainActivity extends AppCompatActivity {
    Button buttonregister,buttoncomfirm;
    private EditText inputemail,inputpassword;
    private ProgressDialog loadingbar;
    private CheckBox rememberme;
    private TextView forgotpass;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonregister = findViewById(R.id.register);
        buttoncomfirm = findViewById(R.id.comfirm);
        inputemail = findViewById(R.id.input_user);
        inputpassword = findViewById(R.id.input_password);
        loadingbar = new ProgressDialog(this);
        rememberme = (CheckBox)findViewById(R.id.rememberme_chk);
        forgotpass = findViewById(R.id.forgotpsw);
        mAuth = FirebaseAuth.getInstance();


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
                Animatoo.animateFade(MainActivity.this);
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
        String emailaddress =inputemail.getText().toString();
        String password = inputpassword.getText().toString();

        if(TextUtils.isEmpty(emailaddress)){
            Toast.makeText(this,"กรุณากรอกอีเมลของคุณ",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"กรุณากรอกรหัสผ่านของคุณ",Toast.LENGTH_SHORT).show();
        }
        else{

            loadingbar.setTitle("ลงชื่อเข้าใช้");
            loadingbar.setMessage("กรุณารอ");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            allowacess(emailaddress,password);



        }
    }

    private void allowacess(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            final DatabaseReference Rootref;
                            Rootref = FirebaseDatabase.getInstance().getReference();
                            Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Users usersData = snapshot.child("Users").child(mAuth.getUid()).getValue(Users.class);
                                    Prevalent.currentonlineUsers = usersData;
                                    if(rememberme.isChecked()){
                                          Paper.book().write(Prevalent.Usernamekey,email);
                                          Paper.book().write(Prevalent.Userpasskey,password);
                                        }
                                    HashMap<String,Object> userdataMap = new HashMap<>();
                                    userdataMap.put("password",password);
                                    Rootref.child("Users").child(mAuth.getUid()).updateChildren(userdataMap);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            Toast.makeText(MainActivity.this, "ล็อคอินสำเร็จ", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                            startActivity(new Intent(MainActivity.this,home_activity.class));
                        }
                        else {
                            String message  = task.getException().getMessage();
                            Toast.makeText(MainActivity.this, "error "+ message, Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                        }
                    }

                }
                );

    }


}