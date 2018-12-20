package com.example.john.akelny.Model;

public class PriceSize {
    public String PriceSizeID;
    public Float Price;
    public String SizeID;
    public String FoodID;

    public PriceSize(){}

    public PriceSize(String PriceSizeID, Float Price, String SizeID, String FoodID){
        this.PriceSizeID=PriceSizeID;
        this.Price=Price;
        this.SizeID=SizeID;
        this.FoodID=FoodID;

    }
}
