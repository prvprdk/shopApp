package com.example.shop.domain;

public final class Views {

    public interface Id {}

    public  interface  IdName extends Id{}

    public  interface  FullComment extends IdName{}

    public  interface  FullProduct extends IdName{}

    public interface FullProfile extends IdName {
    }
}
