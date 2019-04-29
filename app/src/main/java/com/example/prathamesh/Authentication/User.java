package com.example.prathamesh.Authentication;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String name;
    public int code;

    public User(){

    }

    public User(String name, int code)
    {
        this.name = name;
        this.code =  code;
    }
}
