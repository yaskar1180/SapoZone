package com.example.sapozone.data.users;

import java.util.ArrayList;
import java.util.HashMap;
import com.example.sapozone.data.communication.Chat;
import com.example.sapozone.data.shop.Quote;
import com.example.sapozone.data.shop.Command;


public class  Account {


    // --------------------------------   informations that the user has to give

    private String username;
    private String password;
    private String email;
    private String phonenumber;
    private String firstname;
    private String lastname;
    private String streetname;
    private String streetnumber;
    private String postal_code;
    private String city;
    private String bio;
    private String id;
    private String pp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
//--------------------------------   Social

    /**
     * Stock the rate gets by the others users
     */
    private ArrayList<Integer> rating = new ArrayList<Integer>();

    /**
     * Stock the email of the users already rated
     */
    private ArrayList<String> AccountsAlreadyRated = new ArrayList<String>();

    /**
     * Stock all conversations
     */
    private HashMap<String, Chat> chats = new HashMap<String, Chat>();



    //--------------------------------   Shop


    /**
     * Commands completed
     */
    private HashMap<String, Command> historic = new HashMap<String, Command>();


    /**
     * Commands in progress
     */
    private HashMap<String, Command> commands = new HashMap<String, Command>();



    /**
     * Quote received
     */
    private HashMap<String, Quote> quotes = new HashMap<String, Quote>();


    /**
     *
     * @param username
     * @param password
     * @param email
     */
    public Account(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }


    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getStreetnumber() {
        return streetnumber;
    }

    public void setStreetnumber(String streetnumber) {
        this.streetnumber = streetnumber;
    }

    public String getStreetname() {
        return streetname;
    }

    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getCity() {
        return city;
    }

    /**
     *
     * @param username
     * @param password
     * @param email
     * @param phonenumber
     * @param firstname
     * @param lastname
     * @param streetname
     * @param streetnumber
     * @param postal_code
     * @param city
     * @param bio
     */
    public Account(String username,String id, String password, String email, String phonenumber,  String firstname, String lastname, String streetname,String streetnumber, String postal_code, String city, String bio,String pp ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phonenumber = phonenumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.streetname = streetname;
        this.streetnumber = streetnumber;
        this.postal_code = postal_code;
        this.city= city;
        this.bio= bio;
        this.id=id;
        this.pp=pp;


    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return phonenumber;
    }

    public void setNumber(String number) {
        this.phonenumber = number;
    }



    public ArrayList<Integer> getRating() {
        return rating;
    }

    public void setRating(ArrayList<Integer> rating) {
        this.rating = rating;
    }

    public ArrayList<String> getAccountsAlreadyRated() {
        return AccountsAlreadyRated;
    }

    public void setAccountsAlreadyRated(ArrayList<String> accountsAlreadyRated) {
        AccountsAlreadyRated = accountsAlreadyRated;
    }

    public HashMap<String, Chat> getChats() {
        return chats;
    }

    public void setChats(HashMap<String, Chat> chats) {
        this.chats = chats;
    }

    public HashMap<String, Command> getHistoric() {
        return historic;
    }

    public void setHistoric(HashMap<String, Command> historic) {
        this.historic = historic;
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }

    public void setCommands(HashMap<String, Command> commands) {
        this.commands = commands;
    }

    public HashMap<String, Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(HashMap<String, Quote> quotes) {
        this.quotes = quotes;
    }
}
