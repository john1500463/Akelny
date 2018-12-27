package com.example.john.akelny.Model;

public class Resturant {

    public String ResuturantName;
    public Float DeliveryTime;
    public Float DeliveryFees;
//    public String Longitude;
//    public String Latitude;
    public String Logo;
    public Float StartTime;
    public Float EndTime;

    public Resturant(){

    }

    public Resturant(String ResturantName,Float DeleviryTime,Float DeliveryFees,String Logo,Float StartTime,Float EndTime){

        this.ResuturantName=ResturantName;
        this.DeliveryTime=DeleviryTime;
        this.DeliveryFees=DeliveryFees;
//        this.Longitude=Longitude;
//        this.Latitude=Latitude;
        this.Logo=Logo;
        this.StartTime=StartTime;
        this.EndTime=EndTime;

    }

 }
