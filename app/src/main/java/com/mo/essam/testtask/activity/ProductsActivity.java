package com.mo.essam.testtask.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mo.essam.testtask.R;
import com.mo.essam.testtask.adapter.ProductsAdapter;
import com.mo.essam.testtask.database.TaskTable;
import com.mo.essam.testtask.object.Product;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {
    ImageView backImage, basketImage;
    RecyclerView productsList;
    TextView titleText;

    ArrayList<Product> data = new ArrayList<>();
    TaskTable taskTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        initializeViews();
        fillData();
        taskTable = new TaskTable(this);

        ProductsAdapter productsAdapter = new ProductsAdapter(this,data,addToCartClick);
        productsList.setAdapter(productsAdapter);
        productsList.setLayoutManager(new GridLayoutManager(this,1));

    }

    private void fillData(){
        data.add(new Product(0,R.drawable.apple,"Apple",0));
        data.add(new Product(1,R.drawable.banana,"Banana",0));
        data.add(new Product(2,R.drawable.orange,"Orange",0));
        data.add(new Product(3,R.drawable.strawberry,"Strawberry",0));
        data.add(new Product(4,R.drawable.watermelon,"Watermelon",0));
    }

    private ProductsAdapter.AddToCartClick addToCartClick = new ProductsAdapter.AddToCartClick() {
        @Override
        public void onClick(View v, int position, Product product) {
            int amount = product.getAmount();
                if (amount>0){
                    if (!taskTable.checkIfProductInCart(product.getId())) {
                        taskTable.insertToTable(product);
                        Log.e("new amount",product.getAmount()+"");
                        Log.e("amount to be saved",amount+"");
                    }
                    else {
                        int quantityOfProduct = taskTable.getQuantityOfProduct(product.getId());
                        Log.e("amount saved",quantityOfProduct+"");
                        Log.e("amount to be saved",amount+"");

                        taskTable.updateProductQuantity(product.getId(),
                                (quantityOfProduct+amount));
                    }
                    Toast.makeText(ProductsActivity.this, getString(R.string.added), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ProductsActivity.this, getString(R.string.add_basket_error), Toast.LENGTH_SHORT).show();
                }
        }
    };

    public void goToBasket(View view) {
        Intent intent = new Intent(this,BasketActivity.class);
        startActivity(intent);
    }

    private void initializeViews(){
        backImage = findViewById(R.id.backImage);
        basketImage = findViewById(R.id.basketImage);
        titleText = findViewById(R.id.titleText);
        productsList = findViewById(R.id.productsList);

        basketImage.setVisibility(View.VISIBLE);
        titleText.setText(getString(R.string.my_products));
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        basketImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductsActivity.this,BasketActivity.class);
                startActivity(intent);
            }
        });

    }
}
