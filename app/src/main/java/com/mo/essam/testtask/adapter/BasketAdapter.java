package com.mo.essam.testtask.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mo.essam.testtask.R;
import com.mo.essam.testtask.object.Product;

import java.util.ArrayList;

/**
 * Created by mohamedEssam on 10/10/2018.
 */

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ItemListHolder> {


    private Context context;
    private ArrayList<Product> data;
    private DeleteFromCartClick deleteFromCartClick;

    public BasketAdapter(Context context, ArrayList<Product> data, DeleteFromCartClick deleteFromCartClick) {
        this.context = context;
        this.data = data;
        this.deleteFromCartClick=deleteFromCartClick;
    }

    @NonNull
    @Override
    public ItemListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_basket, parent, false);

        ItemListHolder holder = new ItemListHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemListHolder holder, final int position) {
        final Product product = data.get(position);

        holder.productNameText.setText(product.getName());
        holder.productImage.setImageResource(product.getImageRes());
        holder.productAmountText.setText(product.getAmount()+"");

        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFromCartClick.onClick(v,position,product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ItemListHolder extends RecyclerView.ViewHolder{

        ImageView productImage, deleteImage;
        TextView productNameText,productAmountText;


        public ItemListHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.cart_productImage);
            productNameText = itemView.findViewById(R.id.cart_productNameText);
            deleteImage = itemView.findViewById(R.id.cart_deleteItemImage);
            productAmountText = itemView.findViewById(R.id.cart_amountText);
        }
    }

    public interface DeleteFromCartClick{
     void onClick(View v, int position, Product product);
    }

}
