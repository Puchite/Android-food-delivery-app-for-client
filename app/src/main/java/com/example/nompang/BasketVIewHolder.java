package com.example.nompang;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nompang.save.ItemClickListner;

public class BasketVIewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView ProductName,ProductPrice,ProductAmount;
    public ImageView ProductImage;
    public ItemClickListner listner;

    public BasketVIewHolder(View itemView) {
            super(itemView);

            ProductImage = itemView.findViewById(R.id.basket_product_image);
            ProductName = itemView.findViewById(R.id.basket_product_name);
            ProductAmount = itemView.findViewById(R.id.productAmountBasket);

    }
    public void setItemClickListner(ItemClickListner listener){
        this.listner = listener;
    }

    @Override
    public void onClick(View v) {
            listner.onClick(v,getAdapterPosition(),false);
    }
}
