package com.example.john.akelny.Model;

public class UserAddress {
    public String UserAddressID;

    public String City;
    public String Area;
    public String Street;
    public String Floor;
    public String Appartment;
    public String UserID;

    public UserAddress(){}

    public UserAddress(String UserAddressID,String City,String Area, String Street,String Floor,String Appartment,String UserID){
        this.Appartment=Appartment;
        this.Area=Area;
        this.UserID=UserID;
        this.Floor=Floor;
        this.Street=Street;
        this.City=City;

    }

}
