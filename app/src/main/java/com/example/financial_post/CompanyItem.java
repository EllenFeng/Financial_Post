package com.example.financial_post;

public class CompanyItem {
    private String ID;
    private String name;
    private String fullName;
    private String city;
    private String link;
    private String add;

    public CompanyItem() {
        super();
        ID = "";
        link = "";
        name = "";
        fullName = "";
        city = "";
        add = "";
    }
    public CompanyItem(String ID,String name,String fullName,String city, String link, String add) {
        super();
        this.ID = ID;
        this.name = name;
        this.fullName = fullName;
        this.city = city;
        this.link = link;
        this.add = add;

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
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
