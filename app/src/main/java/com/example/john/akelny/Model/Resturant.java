package com.example.john.akelny.Model;

public class Resturant {

    public String ResuturantName;
    public String DeliveryTime;
    public String DeliveryFees;
    public String Longitude;
    public String Latitude;
    public String Logo;
    public String StartTime;
    public String EndTime;

    public Resturant(){

    }

    public Resturant(String ResturantName,String DeleviryTime,String DeliveryFees,String Logo,String StartTime,String EndTime,String Longitude, String Latitude){

        this.ResuturantName=ResturantName;
        this.DeliveryTime=DeleviryTime;
        this.DeliveryFees=DeliveryFees;
        this.Longitude=Longitude;
        this.Latitude=Latitude;
        this.Logo=Logo;
        this.StartTime=StartTime;
        this.EndTime=EndTime;

    }

 }
