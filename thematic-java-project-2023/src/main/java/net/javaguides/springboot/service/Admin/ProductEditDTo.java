package net.javaguides.springboot.service.Admin;

import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.model.ProductDetail;

import java.util.List;

public class ProductEditDTo {
    private Product product;
    private List<ProductDetail> productDetails;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<ProductDetail> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<ProductDetail> productDetails) {
        this.productDetails = productDetails;
    }

    @Override
    public String toString() {
        return "ProductEditDTo{" +
                "product=" + product +
                ", productDetail=" + productDetails +
                '}';
    }
}
