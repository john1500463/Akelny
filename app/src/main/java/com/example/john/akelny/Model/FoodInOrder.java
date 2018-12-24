package com.example.john.akelny.Model;

public class FoodInOrder {

    public String FoodInOrderID;
    public String FoodID;
    public Integer Quantity;

    public FoodInOrder(){

    }
    public FoodInOrder(String FoodInOrderID,String FoodID,Integer Quantity ){
        this.FoodInOrderID=FoodInOrderID;
        this.FoodID=FoodID;
        this.Quantity=Quantity;

    }
}
