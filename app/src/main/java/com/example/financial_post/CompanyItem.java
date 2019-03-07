package com.example.financial_post;

public class CompanyItem {
    private String ID;
    private String name;
    private String fullName;
    private String city;
    private String link;


    public CompanyItem() {
        super();
        ID = "";
        link = "";
        name = "";
        fullName = "";
        city = "";
    }
    public CompanyItem(String ID,String name,String fullName,String city, String link) {
        super();
        this.ID = ID;
        this.name = name;
        this.fullName = fullName;
        this.city = city;
        this.link = link;

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
