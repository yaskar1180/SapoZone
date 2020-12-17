package com.example.sapozone.data.shop;

import android.widget.ImageView;

import java.util.HashMap;
import com.example.sapozone.data.items.Product;

public class Shop {


    private String id;
    private String userId;
    private String name;

    private int postalCode;

    private int logo;

    private HashMap<String, Product> productToSell = new HashMap<String, Product>();


    public String getName(){
        return name;
    }


    public String getId(){
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
