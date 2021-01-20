package com.example.nompang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.nompang.models.Users;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nompang.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class profileActivity extends AppCompatActivity {


    private FloatingActionButton mainButton, editProfileButton, homeButton,logOutbutton,informationButton;
    private Animation openFloatAni,closeFloatAni;
    private boolean isOpen;
    private ProgressDialog loadingbar;
    private Button confirmbutton;
    private CircleImageView picture;
    private TextView username,changepic;
    private EditText name,phone,pass,confirmpass,location;
    private Uri imageuri;
    private String myuri ="";
    private StorageTask uploadtask;
    private StorageReference storageProfilepicture;
    private String checker = "";
    private static final int imgrequest = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        storageProfilepicture = FirebaseStorage.getInstance().getReference().child("Profile picture");
        mainButton = findViewById(R.id.main);
        editProfileButton = findViewById(R.id.editProfile);
        homeButton = findViewById(R.id.home);
        logOutbutton = findViewById(R.id.logOut);
        informationButton = findViewById(R.id.information);
        Paper.init(this);
        isOpen = false;
        confirmbutton = findViewById(R.id.comfirm);
        changepic = findViewById(R.id.change_pic_pro);
        username = findViewById(R.id.input_username_prof);
        name = findViewById(R.id.input_name_pro);
        phone = findViewById(R.id.input_phone_pro);

        location = findViewById(R.id.input_loca_pro);
        username.setText(Prevalent.currentonlineUsers.getName());
        phone.setText(Prevalent.currentonlineUsers.getPhone());
        name.setText(Prevalent.currentonlineUsers.getRealname());
        location.setText(Prevalent.currentonlineUsers.getLocation());

        picture = findViewById(R.id.cirpic);

        System.out.println(Prevalent.currentonlineUsers.getName());
        System.out.println(Prevalent.currentonlineUsers.getName());
        System.out.println(Prevalent.currentonlineUsers.getName());
        System.out.println(Prevalent.currentonlineUsers.getName());
        System.out.println(Prevalent.currentonlineUsers.getName());
        System.out.println(Prevalent.currentonlineUsers.getName());

        userprofile(username,name,phone,location,picture);

        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checker.equals("clicked")){
                    userinfosaved();
                }
                else{
                    updateonlyuserinfo();

                }

            }
        });
        changepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = "clicked";

                CropImage.activity(imageuri).setAspectRatio(1,1)
                        .start(profileActivity.this);
            }
        });

        openFloatAni = AnimationUtils.loadAnimation(profileActivity.this,R.anim.floating_open);
        closeFloatAni = AnimationUtils.loadAnimation(profileActivity.this,R.anim.floating_close);

        editProfileButton.setVisibility(View.INVISIBLE);
        homeButton.setVisibility(View.INVISIBLE);
        logOutbutton.setVisibility(View.INVISIBLE);
        informationButton.setVisibility(View.INVISIBLE);

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen)
                {
                    editProfileButton.startAnimation(closeFloatAni);
                    homeButton.startAnimation(closeFloatAni);
                    logOutbutton.startAnimation(closeFloatAni);
                    informationButton.startAnimation(closeFloatAni);

                    isOpen = false;
                }
                else
                {
                    editProfileButton.startAnimation(openFloatAni);
                    homeButton.startAnimation(openFloatAni);
                    logOutbutton.startAnimation(openFloatAni);
                    informationButton.startAnimation(openFloatAni);

                    isOpen = true;
                }
            }
        });

        logOutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().destroy();
                Intent logout = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(logout);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE&&resultCode==RESULT_OK&&data!=null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageuri = result.getUri();
            picture.setImageURI(imageuri);

        }
        else{
            Toast.makeText(this, "error try again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(profileActivity.this,profileActivity.class));
            finish();
        }
    }
    private  void updateonlyuserinfo(){
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference().child("Users");
        HashMap<String,Object> userdataMap = new HashMap<>();
        userdataMap.put("realname",name.getText().toString());
        userdataMap.put("location",location.getText().toString());
        userdataMap.put("phone",phone.getText().toString());
        ref.child(Prevalent.currentonlineUsers.getName()).updateChildren(userdataMap);

        Prevalent.currentonlineUsers.setRealname(name.getText().toString());
        Prevalent.currentonlineUsers.setLocation(location.getText().toString());
        Prevalent.currentonlineUsers.setPhone(phone.getText().toString());



        startActivity(new Intent(profileActivity.this,home_activity.class));
        Toast.makeText(profileActivity.this, "Profile info update is success ", Toast.LENGTH_SHORT).show();
        finish();
    }
    private void userinfosaved() {
        if(TextUtils.isEmpty(name.getText().toString())){
            Toast.makeText(this, "กรุณาใส่ชื่อจริงของคุณ", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(location.getText().toString())){
            Toast.makeText(this, "กรุณาใส่ที่อยู่ที่ถูกต้องของคุณ", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(phone.getText().toString())){
            Toast.makeText(this, "กรุณาใส่เบอร์ที่ถูกต้องของคุณ", Toast.LENGTH_SHORT).show();

        }
        else if(checker.equals("clicked")){
            uploadImage();

        }

    }
    private void uploadImage(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("update profile");
        progressDialog.setMessage("please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if(imageuri != null){

            final StorageReference fileRef = storageProfilepicture
                    .child(Prevalent.currentonlineUsers.getName() +".jpg");

            uploadtask = fileRef.putFile(imageuri);
            uploadtask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();

                    }
                    return  fileRef.getDownloadUrl();

                }
            })
            .addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    Toast.makeText(profileActivity.this, imageuri.toString(), Toast.LENGTH_SHORT).show();
                    if(task.isSuccessful()){
                        Uri downloaduri = task.getResult();
                        myuri = downloaduri.toString();
                        DatabaseReference ref = FirebaseDatabase.getInstance()
                                .getReference().child("Users");
                        HashMap<String,Object> userdataMap = new HashMap<>();
                        userdataMap.put("realname",name.getText().toString());
                        userdataMap.put("location",location.getText().toString());
                        userdataMap.put("phone",phone.getText().toString());
                        userdataMap.put("image",myuri);
                        ref.child(Prevalent.currentonlineUsers.getName()).updateChildren(userdataMap);




                        progressDialog.dismiss();

                        startActivity(new Intent(profileActivity.this,home_activity.class));
                        Toast.makeText(profileActivity.this, "Profile info update is success ", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{

                        progressDialog.dismiss();
                        Toast.makeText(profileActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
        else{
            Toast.makeText(this, "image is not select", Toast.LENGTH_SHORT).show();
        }
    }

    private void userprofile(TextView username, EditText name, EditText phone, EditText location,CircleImageView picture) {
        DatabaseReference  UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentonlineUsers.getName());
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("image").exists()){
                        String image = snapshot.child("image").getValue().toString();
                        String realname = snapshot.child("realname").getValue().toString();

                        String locationt = snapshot.child("location").getValue().toString();

                        Picasso.get().load(image).into(picture);
                        name.setText(realname);
                        location.setText(locationt);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}