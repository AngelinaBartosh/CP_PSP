package sample.models;

import java.io.Serializable;

public class Cart extends Auto implements Serializable {
    private static final long serialVersionUID  = 1L;
    private String username;
    public Cart(String brand, String model, String generation, int price, String transmission, String fuel, int year, float volumeE) {
        super(brand, model, generation, price, transmission, fuel, year, volumeE);
    }

    public Cart(int id, String brand, String model, String generation, int price, String transmission, String fuel, int year, float volumeE) {
        super(id, brand, model, generation, price, transmission, fuel, year, volumeE);
    }

    public Cart(int id, String username, String brand, String model, String generation, int price, String transmission, String fuel, int year, float volumeE) {
        super(id, brand, model, generation, price, transmission, fuel, year, volumeE);
        this.username = username;
    }

    public Integer getTotalPrice(){
        return getPrice();
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }
}
