package com.mo.essam.testtask.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mo.essam.testtask.R;
import com.mo.essam.testtask.adapter.BasketAdapter;
import com.mo.essam.testtask.database.TaskTable;
import com.mo.essam.testtask.object.Product;

import java.util.ArrayList;

public class BasketActivity extends AppCompatActivity {
    ImageView backImage;
    TextView titleText, noDataText;
    RecyclerView basketList;
    BasketAdapter basketAdapter;

    TaskTable taskTable;
    ArrayList<Product> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        initializeViews();
        taskTable=new TaskTable(this);

        data=taskTable.getSelectedProducts();

        if (data!=null){
            basketAdapter = new BasketAdapter(this,data,deleteFromCartClick);
            basketList.setAdapter(basketAdapter);
            basketList.setLayoutManager(new GridLayoutManager(this,1));
        }
        else {
            noDataText.setVisibility(View.VISIBLE);
        }
    }

    private BasketAdapter.DeleteFromCartClick deleteFromCartClick = new BasketAdapter.DeleteFromCartClick() {
        @Override
        public void onClick(View v, int position, Product product) {
            taskTable.deleteItem(product.getId());
            data.remove(position);
            basketAdapter.notifyDataSetChanged();

            if (data.size()==0){
                noDataText.setVisibility(View.VISIBLE);
            }
        }
    };

    public void clearBasket(View view) {
        if (data==null || data.size()==0){
            Toast.makeText(this, getString(R.string.empty_basket), Toast.LENGTH_SHORT).show();
        }
        else {
            showClearWarningDialog();
        }
    }

    private void showClearWarningDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.clear_warning));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                data.clear();
                basketAdapter.notifyDataSetChanged();
                taskTable.clearDB();
                noDataText.setVisibility(View.VISIBLE);
            }
        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void initializeViews(){
        backImage = findViewById(R.id.backImage);
        titleText = findViewById(R.id.titleText);
        noDataText = findViewById(R.id.noDataText);
        basketList = findViewById(R.id.basketList);

        titleText.setText(getString(R.string.my_basket));

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
