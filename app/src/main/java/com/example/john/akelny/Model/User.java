package com.example.john.akelny.Model;

public class User {
    public String UserID;
    public String FirstName;
    public String LastName;
    public String Email;
    public String Password;
    public String Birthdate;
    public String PhoneNumber;
    public String Gender;
    public String RoleID;


    public User(){

    }
    public User(String Email,String Password, String PhoneNumber,String FirstName,String LastName){

        this.Email=Email;
        this.Password =Password;
        this.PhoneNumber=PhoneNumber;
        this.FirstName = FirstName;
        this.LastName = LastName;

    }
}
