package com.lastyear.semester2.thematicjava2023.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "brand")
    private int brand;
    @Column(name = "name")
    private String name;
    @Column(name = "category")
    private int category;
    @Column(name = "price" )
    private int price;
    @Column(name = "sale_rate")
    private int saleRate;
    @Column(name = "star_rate")
    private int starRate;
    @Column(name = "total_value")
    private int totalValue;
    @Column(name = "sole_value")
    private int soleValue;
    @Column(name = "create_at")
    private String create_at;
    @Column(name = "update_at")
    private String update_at;
    @Column(name = "description")
    private String description;
    @Column(name = "idvoucher")
    private String idVoucher;
    @Column(name = "active")
    private String active;


    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBrand() {
        return brand;
    }

    public void setBrand(int brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSaleRate() {
        return saleRate;
    }

    public void setSaleRate(int saleRate) {
        this.saleRate = saleRate;
    }

    public int getStarRate() {
        return starRate;
    }

    public void setStarRate(int starRate) {
        this.starRate = starRate;
    }

    public int getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }

    public int getSoleValue() {
        return soleValue;
    }

    public void setSoleValue(int soleValue) {
        this.soleValue = soleValue;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdVoucher() {
        return idVoucher;
    }

    public void setIsVoucher(String isVoucher) {
        this.idVoucher = isVoucher;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", brand=" + brand +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", saleRate=" + saleRate +
                ", starRate=" + starRate +
                ", totalValue=" + totalValue +
                ", soleValue=" + soleValue +
                ", create_at='" + create_at + '\'' +
                ", update_at='" + update_at + '\'' +
                ", description='" + description + '\'' +
                ", idVoucher='" + idVoucher + '\'' +
                ", active=" + active +
                '}';
    }
}
