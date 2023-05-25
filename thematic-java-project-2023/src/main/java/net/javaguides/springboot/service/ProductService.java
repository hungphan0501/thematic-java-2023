package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public List<Product> finByBrand(int idBrand) {
        return productRepository.findByBrand(idBrand);
    }

    public List<Product> finByCategory(int idCategory) {
        return productRepository.findByCategory(idCategory);
    }


    public List<Product> finByPrices(int belowPrice, int abovePrice) {
        return productRepository.findByPrices(belowPrice, abovePrice);
    }

    public List<Product> filterProducts(List<Integer> sizes, List<Integer> brands, int minPrice,int maxPrice) {
        return productRepository.findBySizeInAndBrandAndPriceBetween(sizes, brands,minPrice, maxPrice);
    }

    public List<Product> filterProductsByBrandAndPrice( List<Integer> brands, int minPrice,int maxPrice) {
        return productRepository.findByBrandAndPriceBetween(brands,minPrice, maxPrice);
    }
    public List<Product> filterProductsExceptBrand( List<Integer> sizes, int minPrice,int maxPrice) {
        return productRepository.findBySizeInAndPriceBetween(sizes,minPrice, maxPrice);
    }
    public List<Product> filterProductsBySizesAndBrand( List<Integer> sizes, List<Integer> brands) {
        return productRepository.findBySizeInAndBrand(sizes,brands);
    }

    public List<Product> filterProductsBYPrice( int minPrice,int maxPrice) {
        return productRepository.findByPrice(minPrice, maxPrice);
    }

    public List<Product> filterProductsByBrand( List<Integer> brands) {
        return productRepository.findByBrand(brands);
    }

    public List<Product> filterProductsBySizes( List<Integer> sizes) {
        return productRepository.findBySizes(sizes);
    }


    public void removeProduct(int idProduct) {
        // Kiểm tra xem sản phẩm có tồn tại trong cơ sở dữ liệu hay không
        Product product = productRepository.findById(idProduct).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        // Xóa sản phẩm
        productRepository.delete(product);
    }

    public List<Product> getManage() {
        return productRepository.getManage();
    }


    public List<Product> getAllProductNew() {
        List<Product> products = productRepository.getAllProductNew();
        int count= 10;
        if (products.size() <= count) {
            List<Product> result = new ArrayList<>(products);
            return result;
        } else {
            List<Product>  result = products.subList(0, count);
            return result;
        }

    }

    public List<Product> getAllProductDiscount() {
        List<Product> products = productRepository.getAllProductDiscount();
        int count= 10;
        if (products.size() <= count) {
            List<Product> result = new ArrayList<>(products);
            return result;
        } else {
            List<Product>  result = products.subList(0, count);
            return result;
        }
    }

    public List<Product> getAllProductBestSeller() {
        List<Product> products = productRepository.getAllProductBestSeller();
        int count= 10;
        if (products.size() <= count) {
            List<Product> result = new ArrayList<>(products);
            return result;
        } else {
            List<Product>  result = products.subList(0, count);
            return result;
        }
    }

}
