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

public class sendtomail extends AppCompatActivity {
    private Button comfirmpass;
    private EditText Inputemail;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sendtomail);
        mAuth = FirebaseAuth.getInstance();
        comfirmpass = findViewById(R.id.comfirm_reset);
        Inputemail = findViewById(R.id.input_email);

        comfirmpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = Inputemail.getText().toString();
                if(TextUtils.isEmpty(useremail)){
                    Toast.makeText(sendtomail.this, "ใส่อีเมงของคุณ", Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(sendtomail.this, "กรุณาตรวจสอบอีเมลของคุณ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(sendtomail.this,MainActivity.class));
                            }
                            else{
                                Toast.makeText(sendtomail.this, "Error", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

    }
}