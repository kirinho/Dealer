package com.Dealer.entities;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city; // місце знаходження

    private int year; // рік випуску

    private int mileage; // пробіг

    private String type; // тип кузову

    private String color; // колір

    private String typeEngine; // тип двигуну

    private double sizeEngine; // скільки літрів

    private int powerEngine; // кількість коней

    private String box; // коробка

    private String typeDrive; // привод

    @Lob
    private byte[] photos; // фото авто

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public Long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public int getYear() {
        return year;
    }

    public int getMileage() {
        return mileage;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public String getTypeEngine() {
        return typeEngine;
    }

    public double getSizeEngine() {
        return sizeEngine;
    }

    public int getPowerEngine() {
        return powerEngine;
    }

    public String getBox() {
        return box;
    }

    public String getTypeDrive() {
        return typeDrive;
    }

    public byte[] getPhotos() {
        return photos;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setTypeEngine(String typeEngine) {
        this.typeEngine = typeEngine;
    }

    public void setSizeEngine(double sizeEngine) {
        this.sizeEngine = sizeEngine;
    }

    public void setPowerEngine(int powerEngine) {
        this.powerEngine = powerEngine;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public void setTypeDrive(String typeDrive) {
        this.typeDrive = typeDrive;
    }

    public void setPhotos(byte[] photos) {
        this.photos = photos;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
