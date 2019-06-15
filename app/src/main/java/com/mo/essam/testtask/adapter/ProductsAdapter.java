package com.mo.essam.testtask.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mo.essam.testtask.R;
import com.mo.essam.testtask.database.TaskTable;
import com.mo.essam.testtask.object.Product;

import java.util.ArrayList;

/**
 * Created by mohamedEssam on 10/10/2018.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ItemListHolder> {


    private Context context;
    private ArrayList<Product> data;
    private AddToCartClick addToCartClick;

    public ProductsAdapter(Context context, ArrayList<Product> data, AddToCartClick addToCartClick) {
        this.context = context;
        this.data = data;
        this.addToCartClick=addToCartClick;
    }

    @NonNull
    @Override
    public ItemListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);

        ItemListHolder holder = new ItemListHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemListHolder holder, final int position) {
        final Product product = data.get(position);

        holder.productNameText.setText(product.getName());
        holder.productImage.setImageResource(product.getImageRes());

        holder.productPlusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.productAmountText.setText(String.valueOf(Integer.parseInt((holder.productAmountText.getText().toString()))+1));
                product.setAmount(Integer.parseInt(holder.productAmountText.getText().toString()));
            }
        });

        holder.productMinusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt((holder.productAmountText.getText().toString()))>0) {
                    holder.productAmountText.setText(String.valueOf(Integer.parseInt((holder.productAmountText.getText().toString()))-1));
                    product.setAmount(Integer.parseInt(holder.productAmountText.getText().toString()));
                }
            }
        });

        holder.addToCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCartClick.onClick(v,position,product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ItemListHolder extends RecyclerView.ViewHolder{

        ImageView productImage,productPlusImage,productMinusImage;
        TextView productNameText,productAmountText;
        LinearLayout addToCartLayout;


        public ItemListHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productNameText = itemView.findViewById(R.id.productNameText);
            productPlusImage = itemView.findViewById(R.id.productPlusImage);
            productMinusImage = itemView.findViewById(R.id.productMinusImage);
            productAmountText = itemView.findViewById(R.id.productAmountText);
            addToCartLayout = itemView.findViewById(R.id.addToCartLayout);
        }
    }

    public interface AddToCartClick{
     void onClick(View v, int position, Product product);
    }

}
