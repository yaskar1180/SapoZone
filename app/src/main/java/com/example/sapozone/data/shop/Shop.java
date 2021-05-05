package com.example.sapozone.data.shop;

import android.graphics.Color;
import android.widget.ImageView;

import java.util.HashMap;
import com.example.sapozone.data.items.Product;

public class Shop {


    private int id;
    private String userId;
    private String name;
    private String phoneNumber;
    private String bio;
    private String picture;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    private String postalCode;

    private int logo = Color.GRAY;

    private HashMap<String, Product> productToSell = new HashMap<String, Product>();


    public Shop(int id, String name, String postalCode){
        this.id = id;
        this.name = name;
        this.postalCode = postalCode;
    }


    public Shop(int id, String name, String phoneNumber, String postalCode, String bio,String pp) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.postalCode = postalCode;
        this.bio = bio;
        this.picture = pp;
    }

    public String getName(){
        return name;
    }

    public String getBio() {
        return bio;
    }

    public int getId(){
        return id;
    }

    public String getUserId(){
        return userId;
    }


    public String getPostalCode(){
        return postalCode;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public HashMap<String, Product> getProductToSell() {
        return productToSell;
    }

    public void setProductToSell(HashMap<String, Product> productToSell) {
        this.productToSell = productToSell;
    }

    public int getLogo() {
        return logo;
    }
}
