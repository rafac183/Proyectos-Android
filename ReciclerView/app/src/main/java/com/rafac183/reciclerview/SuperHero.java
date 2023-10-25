package com.rafac183.reciclerview;

public class SuperHero {

    private String name;
    private String publisher;
    private String poder;
    private String image;

    public SuperHero(String name, String publisher, String poder, String image) {
        this.name = name;
        this.publisher = publisher;
        this.poder = poder;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPoder() {
        return poder;
    }

    public void setPoder(String poder) {
        this.poder = poder;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
