package com.mattrubacky.monet2;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mattr on 9/15/2017.
 */

public class Annie {
    public Annie(){}

    @SerializedName("ordered_info")
    Ordered ordered;
    @SerializedName("merchandises")
    ArrayList<Product> merch;
}
class Ordered{
    public Ordered(){}

    @SerializedName("gear")
    Gear gear;
    @SerializedName("price")
    int price;
    @SerializedName("skill")
    Skill skill;

}
class Product{
    public Product(){}
    @SerializedName("gear")
    Gear gear;
    @SerializedName("price")
    int price;
    @SerializedName("id")
    int id;
    @SerializedName("skill")
    Skill skill;
    @SerializedName("end_time")
    Long endTime;

}
class Gear{
    public Gear(){}

    @SerializedName("name")
    String name;
    @SerializedName("brand")
    Brand brand;
    @SerializedName("image")
    String url;
    @SerializedName("rarity")
    int rarity;
    @SerializedName("id")
    int id;
    @SerializedName("kind")
    String kind;
}
class Brand{
    public Brand(){}

    @SerializedName("name")
    String name;
    @SerializedName("id")
    int id;
    @SerializedName("image")
    String url;
    @SerializedName("frequent_skill")
    Skill skill;
}
class Skill{
    public Skill(){}

    @SerializedName("name")
    String name;
    @SerializedName("image")
    String url;
    @SerializedName("id")
    int id;
}
