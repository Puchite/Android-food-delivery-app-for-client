import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nompang.Basket;
import com.example.nompang.Prevalent.Prevalent;
import com.example.nompang.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class CustomizeDrink extends AppCompatActivity implements AdapterView.OnItemSelectedListener,NumberPicker.OnValueChangeListener {

    private Spinner Sweetness;
    private NumberPicker productAmount;
    private String Type;
    public String sweet;
    private ImageView productImage;
    private TextView productName , productPrice;
    private Spinner productSpinner;
    private RadioGroup productRadiogroup ;
    private RadioButton productRadioButton1, productRadioButton2 ;
    private EditText productDescription ;
    private Button productConfirmed;
    private int amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_drink);

        productImage = findViewById(R.id.product_image_details);

        productName = findViewById(R.id.product_name_details);

        productSpinner = findViewById(R.id.product_spinner);

        productRadiogroup = findViewById(R.id.product_radioGroup);

        productDescription = findViewById(R.id.product_description_details);

        productConfirmed = findViewById(R.id.product_Confirm);

        productPrice = findViewById(R.id.product_price_details);

        Sweetness = findViewById(R.id.product_spinner);

        productAmount = findViewById(R.id.numberPicker);

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
                System.out.println(Type);
            }
        });

        RadioButton Hot = findViewById(R.id.product_radioButton2);
        Hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hot = productRadiogroup.getCheckedRadioButtonId();
                productRadioButton2 = findViewById(hot);
                Type = productRadioButton2.getText().toString();
                System.out.println(Type);
            }
        });

        productConfirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IT = new Intent(getApplicationContext(), Basket.class);
                startActivity(IT);
                UpData();
                Prevalent.currentProduct.setName(productName.getText().toString());
                System.out.println(Prevalent.currentProduct.getImageuri());
            }
        });

        Picasso.get().load(Prevalent.currentProduct.getImageuri()).into(productImage);
        productName.setText(Prevalent.currentProduct.getName());
        productPrice.setText("Price : "+ Prevalent.currentProduct.getPrice()+" Baht");

        productAmount.setMinValue(1);
        productAmount.setMaxValue(10);
        productAmount.setOnValueChangedListener(this);
    }
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        amount = newVal;
    }

    private void UpData(){
        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child("Users").child(Prevalent.currentonlineUsers.getName()).child("product").child("Drinks").exists())
                        || snapshot.child("Users").child(Prevalent.currentonlineUsers.getName()).child("product").child("Drinks").exists()) {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("name", productName.getText().toString());
                    userdataMap.put("sweet", sweet);
                    userdataMap.put("type", Type);
                    userdataMap.put("description", productDescription.getText().toString());
                    userdataMap.put("price", productPrice.getText().toString());
                    userdataMap.put("imageuri", Prevalent.currentProduct.getImageuri());
                    userdataMap.put("amount", "");
                    Rootref.child("Users").child(Prevalent.currentonlineUsers.getName()).child("product").child("Drinks")
                            .child(productName.getText().toString()).updateChildren(userdataMap);
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
}