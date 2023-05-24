package net.javaguides.springboot.model;

import javax.persistence.*;

@Entity
@Table(name = "product_detail")
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "id_product")
    private int idProduct;
    @Column(name = "size")
    private int size;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "create_at")
    private String createAt;
    @Column(name = "update_at")
    private String updateAt;
    @Column(name = "active")
    private int active;
    @Column(name = "color")
    private String color;

    public ProductDetail(int idProduct, int size, int quantity, String createAt, String updateAt, int active, String color) {
        this.idProduct = idProduct;
        this.size = size;
        this.quantity = quantity;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.active = active;
        this.color = color;
    }

    public ProductDetail(int size, int quantity, String color) {
        this.size = size;
        this.quantity = quantity;
        this.color = color;
    }

    public ProductDetail() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "id=" + id +
                ", idProduct=" + idProduct +
                ", size=" + size +
                ", quantity=" + quantity +
                ", createAt='" + createAt + '\'' +
                ", updateAt='" + updateAt + '\'' +
                ", active=" + active +
                ", color='" + color + '\'' +
                '}'+"\n";
    }
}
