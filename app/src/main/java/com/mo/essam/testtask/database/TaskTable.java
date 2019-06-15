package com.mo.essam.testtask.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mo.essam.testtask.object.Product;

import java.util.ArrayList;

public class TaskTable {

    private Engine engine;
    private SQLiteDatabase database;

    public TaskTable(Context context) {
        engine=new Engine(context);
        database=engine.getWritableDatabase();
    }

    public void insertToTable(Product product){
        ContentValues values = new ContentValues();

        values.put("_id",product.getId());
        values.put("image",String.valueOf(product.getImageRes()));
        values.put("name",product.getName());
        values.put("amount",product.getAmount());


        database.insert("TaskTable",null,values);
    }

    public void deleteItem(int id){

        String query = "Delete from TaskTable where _id = "+id;
        database.execSQL(query);

    }

    public void clearDB(){

        String query = "Delete from TaskTable";
        database.execSQL(query);

    }

    public boolean checkIfProductInCart(int productId){

        String countQuery="Select count(_id) from TaskTable WHERE _id="+productId;
        Cursor countCursor = database.rawQuery(countQuery,null);

        countCursor.moveToNext();
        int count = countCursor.getInt(0);
        countCursor.close();

        if (count>0){
            return true;
        }
        else {
            return false;
        }

    }

    public int getQuantityOfProduct(int productId){
        String countQuery="Select amount from TaskTable where _id="+productId;
        Cursor countCursor = database.rawQuery(countQuery,null);
        countCursor.moveToNext();
        int amount = countCursor.getInt(0);
        countCursor.close();

        return amount;
    }

    public void updateProductQuantity(int productId, int amount){
        String updateQuery ="Update TaskTable SET amount = "+amount+" WHERE _id = "+productId;
        Cursor cursor = database.rawQuery(updateQuery,null);
        cursor.moveToNext();
        cursor.close();
    }

    public ArrayList<Product> getSelectedProducts(){

        ArrayList<Product> selectedProducts=new ArrayList<>();

        String countQuery="Select count(_id) from TaskTable";
        Cursor countCursor = database.rawQuery(countQuery,null);
        countCursor.moveToNext();
        int count = countCursor.getInt(0);

        if (count>0) {

            countCursor.close();

            String query = "Select * from TaskTable";
            Cursor cursor = database.rawQuery(query,null);

            cursor.moveToNext();

            while (!cursor.isAfterLast()){

                Log.e("amount in basket",cursor.getInt(cursor.getColumnIndex("amount"))+"");

                Product product = new Product(
                        cursor.getInt(cursor.getColumnIndex("_id")),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex("image"))),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getInt(cursor.getColumnIndex("amount")));

                selectedProducts.add(product);

                cursor.moveToNext();

            }
            cursor.close();
            return selectedProducts;
        }
        else {
            countCursor.close();
            return null;
        }
    }

}
