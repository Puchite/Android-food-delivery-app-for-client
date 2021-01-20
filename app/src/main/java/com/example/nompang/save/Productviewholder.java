package com.example.nompang.save;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nompang.R;

public class Productviewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public  TextView ProductName,Productstatus,Productdescription,ProductPrice;
    public ImageButton ProductImage;
    public ItemClickListner listner;
    public Productviewholder(@NonNull View itemView) {
        super(itemView);
        ProductImage = itemView.findViewById(R.id.product_image);
        ProductName = itemView.findViewById(R.id.product_name);
        Productstatus = itemView.findViewById(R.id.product_status);
        Productdescription = itemView.findViewById(R.id.product_description);
        ProductPrice = itemView.findViewById(R.id.product_price);

    }
    public void setItemClickListener(ItemClickListner listener){
        this.listner = listener;
    }

    @Override
    public void onClick(View v) {
        listner.onClick(v,getAdapterPosition(),false);
    }
}
