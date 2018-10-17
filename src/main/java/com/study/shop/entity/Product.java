package com.study.shop.entity;

import java.time.LocalDateTime;

public class Product {
    private int id;
    private String name;
    private double price;
    private LocalDateTime addDate;
    private String picturePath;

    public Product() {
        super();
    }

    public Product(int id, String name, double price, LocalDateTime addDate, String picturePath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.addDate = addDate;
        this.picturePath = picturePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return id == product.id && name != null && name.equals(product.name) && price == product.price &&
                addDate.equals(product.addDate) && picturePath != null && picturePath.equals(product.picturePath);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0) + (int) price + addDate.hashCode() + (picturePath != null ? picturePath.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + "\'" +
                ", price=" + price +
                ", addDate=" + addDate +
                ", picturePath=" + picturePath +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

}
