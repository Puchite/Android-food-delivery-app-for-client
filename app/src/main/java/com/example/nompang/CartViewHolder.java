package com.example.nompang;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nompang.save.ItemClickListner;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView  productname,ProductAmount;
    public ImageView productimager;
    public ItemClickListner itemClickListener;
    public Button deletebut;
    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        productname = itemView.findViewById(R.id.basket_product_name);
        productimager = itemView.findViewById(R.id.basket_product_image);
        deletebut  = itemView.findViewById(R.id.deletebtn);
        ProductAmount = itemView.findViewById(R.id.productAmountBasket);


    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
    public void setItemClickListener(ItemClickListner itemClickListener){
        this.itemClickListener = itemClickListener;
    }
}