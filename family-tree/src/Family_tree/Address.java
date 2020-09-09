/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Family_tree;

import java.io.Serializable;

/**
 * title: Address class
 * purpose: Class that contains address details of a member
 */
public class Address implements Serializable{
    private int streetNum;
    private String streetName;
    private String suburb;
    private int postCode;   

    @Override
    public String toString() {
        return "streetNum=" + streetNum +
                "\nstreetName=" + streetName + 
                "\nsuburb=" + suburb + 
                "\npostCode=" + postCode;
    }
    
    public Address(){
        
    }
    
    public Address(int streetNum, String streetName, String suburb, int postCode) {
        this.streetNum = streetNum;
        this.streetName = streetName;
        this.suburb = suburb;
        this.postCode = postCode;
    }

    public int getStreetNum() {
        return streetNum;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getSuburb() {
        return suburb;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setStreetNum(int streetNum) {
        this.streetNum = streetNum;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }
}