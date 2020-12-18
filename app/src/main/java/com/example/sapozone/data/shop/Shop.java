package com.example.sapozone.data.shop;

import android.graphics.Color;
import android.widget.ImageView;

import java.util.HashMap;
import com.example.sapozone.data.items.Product;

public class Shop {


    private int id;
    private String userId;
    private String name;

    private int postalCode;

    private int logo = Color.GRAY;

    private HashMap<String, Product> productToSell = new HashMap<String, Product>();


    public Shop(int id, String name, int postalCode){
        this.id = id;
        this.name = name;
        this.postalCode = postalCode;
    }

    public String getName(){
        return name;
    }


    public int getId(){
        return id;
    }

    public String getUserId(){
        return userId;
    }


    public int getPostalCode(){
        return postalCode;
    }


    public int getLogo() {
        return logo;
    }
}
