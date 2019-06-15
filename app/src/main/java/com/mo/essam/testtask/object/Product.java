package com.mo.essam.testtask.object;

public class Product {

    private int id;
    private int imageRes;
    private String name;
    private int amount;

    public Product(int id, int imageRes, String name, int amount) {
        this.id = id;
        this.imageRes = imageRes;
        this.name = name;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
