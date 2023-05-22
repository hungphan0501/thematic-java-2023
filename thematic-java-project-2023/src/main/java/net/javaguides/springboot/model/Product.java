package net.javaguides.springboot.model;
import javax.persistence.*;

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
    @Column(name = "img")
    private String img;


    public Product() {
    }

    public Product(int brand, String name, int category, int price, int saleRate, int totalValue, int soleValue, String create_at, String update_at, String description) {
        this.brand = brand;
        this.name = name;
        this.category = category;
        this.price = price;
        this.saleRate = saleRate;
        this.totalValue = totalValue;
        this.soleValue = soleValue;
        this.create_at = create_at;
        this.update_at = update_at;
        this.description = description;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
                ", totalValue=" + totalValue +
                ", soleValue=" + soleValue +
                ", create_at='" + create_at + '\'' +
                ", update_at='" + update_at + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}