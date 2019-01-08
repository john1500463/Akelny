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
    public String Photo;
    public String UserType;


    public User(){

    }
    public User(String Email,String Password, String PhoneNumber,String FirstName,String LastName,String usertype){

        this.Email=Email;
        this.Password =Password;
        this.PhoneNumber=PhoneNumber;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.UserType = usertype;

    }

//    public User(String Email,String Password, String PhoneNumber,String FirstName,String LastName,String Usertype, String Photo)
//    {
//        this.Email=Email;
//        this.Password =Password;
//        this.PhoneNumber=PhoneNumber;
//        this.FirstName = FirstName;
//        this.LastName = LastName;
//        this.Photo=Photo;
//    }
}
