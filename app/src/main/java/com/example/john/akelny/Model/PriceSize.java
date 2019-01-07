package com.example.john.akelny.Model;

public class PriceSize {
    public String PriceSizeID;
    public Float Price;
    public String Size;
    public String FoodID;

    public PriceSize(){}

    public PriceSize(String PriceSizeID, Float Price, String SizeID, String FoodID){
        this.PriceSizeID=PriceSizeID;
        this.Price=Price;
        this.Size=Size;
        this.FoodID=FoodID;

    }
}
