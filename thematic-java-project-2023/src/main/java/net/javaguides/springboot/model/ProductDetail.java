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
    @Column(name = "total_value")
    private int totalValue;
    @Column(name = "sole_value")
    private int soleValue;
    @Column(name = "create_at")
    private String createAt;
    @Column(name = "update_at")
    private String updateAt;
    @Column(name = "active")
    private int active;
    @Column(name = "color")
    private String color;

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
                ", totalValue=" + totalValue +
                ", soleValue=" + soleValue +
                ", createAt='" + createAt + '\'' +
                ", updateAt='" + updateAt + '\'' +
                ", active=" + active +
                ", color='" + color + '\'' +
                '}'+"\n";
    }
}
