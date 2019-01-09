package com.example.john.akelny.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Order {
    public String  UserMail;
    public ArrayList<String> FoodName;
    public ArrayList<Integer> Quantity;
    public String FinalPrice;
    public String ResturantName;

    public Order(){

    }

    public Order (String UserMail,ArrayList<String> FoodName,ArrayList<Integer> Quantity,String FinalPrice,String ResturantName){
        this.UserMail = UserMail;
        this.FoodName=FoodName;
        this.Quantity=Quantity;
        this.FinalPrice=FinalPrice;
        this.ResturantName=ResturantName;

    }




}
