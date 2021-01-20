package com.example.nompang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.drawable.CircularProgressDrawable;

import java.util.HashMap;
import java.util.regex.Pattern;

import maes.tech.intentanim.CustomIntent;

public class registerActivity extends AppCompatActivity {
    private Button backregister,confirmb;
    private EditText InputName,Inputpassword,Inputcpassword,InputPhone,Inputemail;
    private ProgressDialog loadingbar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        confirmb = findViewById(R.id.comfirm_regis);
        backregister = findViewById(R.id.backregister);
        InputName = findViewById(R.id.input_user_regis);
        Inputpassword = findViewById(R.id.input_pass_regis);
        Inputcpassword = findViewById(R.id.input_cpass_regis);
        InputPhone = findViewById(R.id.input_phone_regis);
        Inputemail = findViewById(R.id.input_email_regis);
        loadingbar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        backregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent1);
                CustomIntent.customType(registerActivity.this,"up-to-bottom");
            }
        });

        confirmb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccout();
            }

        });

    }




    private void CreateAccout(){

        String username = InputName.getText().toString();
        String password = Inputpassword.getText().toString();
        String confirmpass = Inputcpassword.getText().toString();
        String phonenumber = InputPhone.getText().toString();
        String email = Inputemail.getText().toString();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"กรุณากรอกชื่อของคุณ",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"กรุณากรอกรหัสผ่านของคุณ",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(confirmpass)){
            Toast.makeText(this,"กรุณายืนยันรหัสผ่าน",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phonenumber)){
            Toast.makeText(this,"กรุณากรอกเบอร์โทรศัพท์ของคุณ",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"กรุณาใส่อีเมลของคุณ",Toast.LENGTH_SHORT).show();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"ใส่อีเมลไม่ถูกต้อง",Toast.LENGTH_SHORT).show();
        }
        else if(!Patterns.PHONE.matcher(phonenumber).matches()||phonenumber.length()!=10){
            Toast.makeText(this,"ใส่เบอร์ไม่ถูกต้อง",Toast.LENGTH_SHORT).show();
        }
        else if(password.length()<8){
            Toast.makeText(this,"รหัสผ่านต้องมี8ตัวหรือมากกว่า",Toast.LENGTH_SHORT).show();
        }

        else if(username.length()>15){
            Toast.makeText(this,"มีชื่อได้ไม่เกิน15ตัว",Toast.LENGTH_SHORT).show();

        }
        else{
                if(password.equals(confirmpass)){


                    loadingbar.setTitle("กำลังสร้างบัญชี");
                    loadingbar.setMessage("กรุณารอ");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();

                    ValidatephoneNumber(username,phonenumber,password,confirmpass,email);
                }
                else{
                    Toast.makeText(this,"ใส่รหัสผ่านให้เหมือนกัน",Toast.LENGTH_SHORT).show();
                }
        }

    }

    private void ValidatephoneNumber(String username, String phonenumber,String password,String confirmpass,String email ) {
        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("Users").child(username).exists())){
                    HashMap<String,Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone",phonenumber);
                    userdataMap.put("name",username);
                    userdataMap.put("password",password);
                    userdataMap.put("email",email);
                    userdataMap.put("location","");
                    userdataMap.put("realname","");

                    mAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()){
                                        Toast.makeText(registerActivity.this, "อีเมลไม่ถูกต้องหรือมีอยู่แล้ว", Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();
                                    }
                                    else{
                                        Rootref.child("Users").child(username).updateChildren(userdataMap)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(registerActivity.this, "สร้างสำเร็จ", Toast.LENGTH_SHORT).show();
                                                            loadingbar.dismiss();

                                                            Intent intent = new Intent(registerActivity.this,MainActivity.class);
                                                            startActivity(intent);
                                                        }
                                                        else{
                                                            loadingbar.dismiss();
                                                            Toast.makeText(registerActivity.this, "ไม่สามารคเชื่อมต่อได้ลองใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });

                }
                else{
                    Toast.makeText(registerActivity.this, "ชื่อ"+username+"มีคนใช้แล้ว", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                    Toast.makeText(registerActivity.this, "กรุณาชื่อเอื่น", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}