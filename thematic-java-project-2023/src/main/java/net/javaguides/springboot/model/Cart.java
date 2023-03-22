package net.javaguides.springboot.model;

import net.javaguides.springboot.repository.ProductDetailRepository;
import net.javaguides.springboot.repository.ProductRepository;

import javax.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "id_customer")
    private int idCustomer;
    @Column(name = "id_product_detail")
    private int idProductDetail;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "total_price")
    private double totalPrice;


    public Cart() {
    }

    public Cart(int idCustomer, int idProductDetail, int quantity, double totalPrice) {
        this.idCustomer = idCustomer;
        this.idProductDetail = idProductDetail;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Cart(int idCustomer, int idProductDetail, int quantity) {
        this.idCustomer = idCustomer;
        this.idProductDetail = idProductDetail;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getIdProductDetail() {
        return idProductDetail;
    }

    public void setIdProductDetail(int idProductDetail) {
        this.idProductDetail = idProductDetail;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void updatePrice(ProductDetailRepository productDetailRepository, ProductRepository productRepository) {
        ProductDetail productDetail = productDetailRepository.findById(idProductDetail).orElse(null);
        int idProduct = productDetail.getIdProduct();
        Product product = productRepository.findById(idProduct).orElse(null);
        double price = (double) product.getPrice();
        if (product != null) {
            totalPrice = quantity * price;
        }
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", idCustomer=" + idCustomer +
                ", idProductDetail=" + idProductDetail +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
