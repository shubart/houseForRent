package com.example.dell.myapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 10/23/2016.
 */
public class Constants {

    public static final String FIRST_COLUMN="First";
    public static final String SECOND_COLUMN="Second";
    public static final String THIRD_COLUMN="Third";
    public static final String FOURTH_COLUMN="Fourth";
    public static final String FIRTH_COLUMN="firth";
    public static final String SIX_COLUMN="six";
    public static final String SEVEN_COLUMN="seven";
    public static final String EIGHT_COLUMN="eight";
    public static final String NINE_COLUMN="nine";
    public static final String TEN_COLUMN="ten";
    public static final String ELEVEN_COLUMN="eleven";
    public static final String TWELVE_COLUMN="twelve";

    public  static List<String> hforlist = new ArrayList<String>();
    public  static List<String> provincelist = new ArrayList<String>();
    public  static  List<String> pricelist = new ArrayList<String>();

public static void addArrays() {
    provincelist.clear();
    pricelist.clear();
    hforlist.clear();

    pricelist.add("K(100 - 500)");
    pricelist.add("K(500 - 1000)");
    pricelist.add("K(1000 - 1500)");
    pricelist.add("K(1500 - 2000)");
    pricelist.add("K(2000 - 3000)");
    pricelist.add("K(3000 - 10,000)");
    pricelist.add("K(10,000 - 30,000)");
    pricelist.add("K(30,000 - Above)");

    hforlist.add("Rent");
    hforlist.add("Sale");
    hforlist.add("Hire");
    hforlist.add("Boarding House");
    hforlist.add("Room Mate");
    hforlist.add("Office Space");

    provincelist.add("Lusaka");
    provincelist.add("Copperbelt");
    provincelist.add("Muchinga");
    provincelist.add("Central");
    provincelist.add("Northern");
    provincelist.add("Western");
    provincelist.add("Eastern");
    provincelist.add("Southern");
    provincelist.add("NorthWestern");
    provincelist.add("Luapula");


}




}
