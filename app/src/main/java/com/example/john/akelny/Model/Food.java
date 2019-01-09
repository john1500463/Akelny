package com.example.john.akelny.Model;

import java.io.Serializable;

public class Food implements Serializable{

    public String FoodName;
    public String FoodDescription;
    public String CategoryID;
    public String ResturantID;
    public String FoodImage;
    public String Price;


    public Food(){

    }
    public Food(String FoodName,String FoodDescription,String CategoryID, String ResturantID,String FoodImage,String Price){

        this.FoodName=FoodName;
        this.FoodDescription=FoodDescription;
        this.CategoryID=CategoryID;
        this.ResturantID=ResturantID;
        this.FoodImage=FoodImage;
        this.Price=Price;


    }

}
