/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Family_tree;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.control.Alert;

/**
 * title: Member class
 * purpose: Class that contains personal details, address and immediate relatives of a family member
 * 
 */
public class Member implements Serializable {
    private String firstName;
    private String surname;
    private String maidenName;
    private String gender;
    private Address address;
    private String description;
    private Member mother;
    private Member father;
    private Member spouse;
    
    private ArrayList<Member> parents = new ArrayList<>();
    private ArrayList<Member> children= new ArrayList<>();
    
    public Member(){
        
    }

    public Member(String firstName, String surname) {
        this.firstName = firstName;
        this.surname = surname;
    }
    
    @Override
    public String toString() {
        return firstName + " " + surname;
    }
    
    //getters
    public ArrayList<Member> getParents() {
        return parents;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getMaidenName() {
        return maidenName;
    }

    public String getGender() {
        return gender;
    }

    public Address getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public Member getMother() {
        return mother;
    }

    public Member getFather() {
        return father;
    }

    public Member getSpouse() {
        return spouse;
    }

    public ArrayList<Member> getMemberChildren() {
        return children;
    }

    //setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
    }

   
    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMother(Member mother) {
        this.mother = mother;
    }

    public void setFather(Member father) {
        this.father = father;
    }

    public void setSpouse(Member spouse) {
        this.spouse = spouse;
    }

    public void setMemberChildren(ArrayList<Member> children) {
        this.children = children;
    }
    
    public void setParents(ArrayList<Member> parents) {
        this.parents = parents;
    }
    
   /**
    * @param newChild 
    * description :adds a child member to the array list, and also to the parent array list
    */
    public void addChildren(Member newChild){
        children.add(newChild); 
        newChild.parents.add(this);   
    }
    
    /**
     * @param m 
     * description :adds a parent member to the array list if parents are less than 2 
     */
    public void addParents(Member m){
        if(this.parents.size() >= 2)
        {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setContentText("Cannot add more than two parents");
            warning.show();
            //System.out.println("Cannot add more than two parents");
        }
        else
            parents.add(m);
    }
    
    /**
     * @param m
     * @param memberType 
     * description- takes in a member object and the type of relative to be added as a string
     * sets the member according to its type
     */
    public void addMember(Member m, String memberType){
        switch (memberType) {
            case "Child":
                this.addChildren(m);
                break;
            case "Spouse":
                this.setSpouse(m);
                break;
            case "Mother":
                this.setMother(m);
                this.addParents(m);
                break;
            case "Father":
                this.setFather(m);
                this.addParents(m);
                break;
        }
    }
    //function that provides an initial family
    public void popMember(){
        this.setGender("Male");
        this.addChildren(new Member("Jane", "Doe"));
        this.addChildren(new Member("Lucy", "Doe"));
        this.setAddress(new Address(1321,"St Happy", "Suburb", 233));

        this.setMother(new Member("Jane","Doe"));
        this.setFather(new Member("Joseph",""));
        this.setSpouse(new Member("Sara",""));
        
        Member ss = new Member("Rebecca", "Doe");
        ss.addChildren(new Member("Ron", "Doe"));
        ss.addChildren(new Member("William", "Doe"));
        ss.setMaidenName("Dane");
        ss.setMother(new Member("Reb", "doe"));
        ss.setFather(this);
        ss.setAddress(new Address(4141,"St Street", "Avenue", 452));
        this.addChildren(ss);       
    }
}
