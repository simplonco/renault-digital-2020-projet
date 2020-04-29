package com.renault.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Entité principale de l'application ...
 */
@Entity(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * La marque (non-vide) d'une voiture.
     */
    @Column(nullable = false)
    @NotNull
    private String brand;

    @Column
    @NotNull
    private String model;

    /**
     * La consommation en "miles per gallon"
     */
    @Column
    @NotNull
    private double mpg;

    public Car() {
        // java bean
    }

    public Car(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public double getMpg() {
        return mpg;
    }

    public void setMpg(double mpg) {
        this.mpg = mpg;
    }

    /**
     * Retourne la consommation en litres par 100 km, le calcul est ...
     *
     * @return la consommation en litres par 100 km
     * @see #mpg
     */
    public double getLitersPer100Kilometers() {
        return mpg * 2;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                '}';
    }

}
