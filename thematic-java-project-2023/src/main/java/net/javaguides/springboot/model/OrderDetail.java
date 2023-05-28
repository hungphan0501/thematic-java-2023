package net.javaguides.springboot.model;

import javax.persistence.*;

@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_order")
    private int idOrder;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "id_product_detail")
    private int idProductDetail;

    public OrderDetail() {
    }
    public OrderDetail(int idOrder, int quantity, int idProductDetail) {
        this.idOrder = idOrder;
        this.quantity = quantity;
        this.idProductDetail = idProductDetail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIdProductDetail() {
        return idProductDetail;
    }

    public void setIdProductDetail(int idProductDetail) {
        this.idProductDetail = idProductDetail;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", idOrder=" + idOrder +
                ", quantity=" + quantity +
                ", idProductDetail=" + idProductDetail +
                '}';
    }
}
