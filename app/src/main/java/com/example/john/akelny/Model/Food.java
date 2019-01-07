package com.example.john.akelny.Model;

public class Food {
    public String FoodID;
    public String FoodName;
    public String FoodDescription;
    public String CategoryID;
    public String ResturantID;
    public String FoodImage;


    public Food(){

    }
    public Food(String FoodName,String FoodDescription,String CategoryID, String ResturantID,String FoodImage){

        this.FoodName=FoodName;
        this.FoodDescription=FoodDescription;
        this.CategoryID=CategoryID;
        this.ResturantID=ResturantID;
        this.FoodImage=FoodImage;


    }

}
