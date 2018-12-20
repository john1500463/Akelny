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
    public User(String UserID,String FirstName,String LastName,String Email,String Password,String Birthdate,String PhoneNumber,String Gender,String RoleID){

        this.UserID = UserID;
        this.FirstName=FirstName;
        this.LastName=LastName;
        this.Email=Email;
        this.Password =Password;
        this.Birthdate=Birthdate;
        this.PhoneNumber=PhoneNumber;
        this.Gender=Gender;
        this.RoleID=RoleID;

    }
}
