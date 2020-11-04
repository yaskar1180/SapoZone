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
    private String number;
    private Location location;

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


    /**
     *
     * @param username
     * @param password
     * @param email
     * @param number
     * @param location
     */
    public Account(String username, String password, String email, String number, Location location) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.number = number;
        this.location = location;
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
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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
