package com.pluartz.test.model;

public class Pet {
   String name, age, color, photo;
   Double vaccine_price;
   public Pet(){}

   public Pet(String name, String age, String color, Double vaccine_price, String photo) {
      this.name = name;
      this.age = age;
      this.color = color;
      this.vaccine_price = vaccine_price;
      this.photo = photo;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getAge() {
      return age;
   }

   public void setAge(String age) {
      this.age = age;
   }

   public String getColor() {
      return color;
   }

   public void setColor(String color) {
      this.color = color;
   }

   public Double getVaccine_price() {
      return vaccine_price;
   }

   public void setVaccine_price(Double vaccine_price) {
      this.vaccine_price = vaccine_price;
   }

   public String getPhoto() {
      return photo;
   }

   public void setPhoto(String photo) {
      this.photo = photo;
   }
}
