package sample.models;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Auto implements ObservableList<Auto>, Serializable {
    private static final long serialVersionUID  = 1L;
    public int id;

    public String username;

    public String brand;
    public String model;
    public String generation;

    public int price;
    public String transmission;
    public String fuel;
    public int year;
    public float volumeE;

    public Auto(int id, String brand, String model, String generation, int price, String transmission, String fuel, int year, float volumeE) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.generation = generation;
        this.price = price;
        this.transmission = transmission;
        this.fuel = fuel;
        this.year = year;
        this.volumeE = volumeE;
    }

    public Auto(int id, String username, String brand, String model, String generation, int price, String transmission, String fuel, int year, float volumeE) {
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
    }

    public Auto(String brand, String model, String generation, int price, String transmission, String fuel, int year, float volumeE) {
        this.brand = brand;
        this.model = model;
        this.generation = generation;
        this.price = price;
        this.transmission = transmission;
        this.fuel = fuel;
        this.year = year;
        this.volumeE = volumeE;
    }

    public Auto() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getVolumeE() {
        return volumeE;
    }

    public void setVolumeE(float volumeE) {
        this.volumeE = volumeE;
    }

    @Override
    public void addListener(ListChangeListener<? super Auto> listChangeListener) {

    }

    @Override
    public void removeListener(ListChangeListener<? super Auto> listChangeListener) {

    }

    @Override
    public boolean addAll(Auto... autos) {
        return false;
    }

    @Override
    public boolean setAll(Auto... autos) {
        return false;
    }

    @Override
    public boolean setAll(Collection<? extends Auto> collection) {
        return false;
    }

    @Override
    public boolean removeAll(Auto... autos) {
        return false;
    }

    @Override
    public boolean retainAll(Auto... autos) {
        return false;
    }

    @Override
    public void remove(int i, int i1) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<Auto> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }


    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(Auto auto) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Auto> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Auto> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Auto get(int index) {
        return null;
    }

    @Override
    public Auto set(int index, Auto element) {
        return null;
    }

    @Override
    public void add(int index, Auto element) {

    }

    @Override
    public Auto remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<Auto> listIterator() {
        return null;
    }

    @Override
    public ListIterator<Auto> listIterator(int index) {
        return null;
    }

    @Override
    public List<Auto> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {

    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {

    }
}
