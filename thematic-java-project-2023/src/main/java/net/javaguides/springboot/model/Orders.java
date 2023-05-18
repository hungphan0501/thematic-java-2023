package net.javaguides.springboot.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "customer")
    private int idUser;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "id_address")
    private int idAddress;

    @Column(name = "status")
    private String status;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "status_payment")
    private String statusPayment;

    public Orders(int idUser, double totalPrice, Date createAt, int idAddress, String status, String paymentType, String statusPayment) {
        this.idUser = idUser;
        this.totalPrice = totalPrice;
        this.createAt = createAt;
        this.idAddress = idAddress;
        this.status = status;
        this.paymentType = paymentType;
        this.statusPayment = statusPayment;
    }

    public Orders() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(String address) {
        this.idAddress = idAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(String statusPayment) {
        this.statusPayment = statusPayment;
    }
}
