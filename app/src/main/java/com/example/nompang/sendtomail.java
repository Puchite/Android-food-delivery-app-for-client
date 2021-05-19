package com.example.nompang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sendtomail extends AppCompatActivity {
    private Button comfirmpass;
    private EditText Inputemail;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sendtomail);
//        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        comfirmpass = findViewById(R.id.comfirm_reset);
        Inputemail = findViewById(R.id.input_email);
        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();
        comfirmpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = Inputemail.getText().toString();
                if(TextUtils.isEmpty(useremail)){
                    Toast.makeText(sendtomail.this, "ใส่อีเมลของคุณ", Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                Toast.makeText(sendtomail.this, "กรุณาตรวจสอบอีเมลของคุณ", Toast.LENGTH_SHORT).show();
                                finish();
//                                startActivity(new Intent(sendtomail.this,MainActivity.class));
                            }
                            else{
                                String message  = task.getException().getMessage();
                                Toast.makeText(sendtomail.this, "error "+message, Toast.LENGTH_SHORT).show();


                            }
                        }
                    });
                }
            }
        });

    }
}