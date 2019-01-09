package com.example.john.akelny.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Order {
    public ArrayList<String> FoodName;
    public ArrayList<Integer> Quantity;
    public String FinalPrice;

    public Order(){

    }

    public Order (ArrayList<String> FoodName,ArrayList<Integer> Quantity,String FinalPrice){

        this.FoodName=FoodName;
        this.Quantity=Quantity;
        this.FinalPrice=FinalPrice;

    }


}
