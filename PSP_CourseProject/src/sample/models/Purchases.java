package sample.models;

import com.mysql.cj.conf.StringProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class Purchases extends Auto implements Serializable {
    private static final long serialVersionUID  = 1L;

    public int id;
    public SimpleStringProperty username;
    public String brand;
    public String model;
    public String generation;
    public int price;
    public String transmission;
    public String fuel;
    public int year;
    public float volumeE;

    public Purchases(int id, String username, String brand, String model, String generation, int price, String transmission, String fuel, int year, float volumeE){
        super(id, brand, model, generation, price, transmission, fuel, year, volumeE);
        this.username = new SimpleStringProperty(username);
    }

    public SimpleStringProperty getUsername() {
        return username;
    }
/*public Purchases(int id, String username, String brand, String model, String generation, int price, String transmission, String fuel, int year, float volumeE){
        this.id = id;
        this.username = username;
        this.brand = brand;
        this.model = model;
        this.generation = generation;
        this.price = price;
        this.transmission = transmission;
        this.fuel = fuel;
        this.year = year;
        this.volumeE = volumeE;
    }*/
}
