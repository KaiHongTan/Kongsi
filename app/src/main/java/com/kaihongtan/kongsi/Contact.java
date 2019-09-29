package com.kaihongtan.kongsi;



public class Contact {
    private int id;
    private String name;
    private String Location;
    private String image;

    public Contact(int id, String name, String Location, String image) {
        this.id = id;
        this.name = name;
        this.Location = Location;
        this.image = image;

    }
    public int getid() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getLocation() {
        return Location;
    }
    public String getImage() {
        return image;
    }

    private static int lastContactId = 0;


}
