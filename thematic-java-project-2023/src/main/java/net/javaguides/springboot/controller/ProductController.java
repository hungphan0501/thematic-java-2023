package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;


    @GetMapping("/")
    public List<Product> getAllProduct() {
        List<Product> list = productService.getAllProduct();
        for (Product p : list) {
            System.out.println(p.toString());
        }
        return productService.getAllProduct();
    }

    @GetMapping("/brand/{idBrand}")
    public List<Product> findByBrand(@PathVariable("idBrand") int idBrand) {
        System.out.println("id brand đâu thèn ngu này" + idBrand);
        List<Product> list = productService.finByBrand(idBrand);
        for (Product p : list) {
            System.out.println(p.toString());
        }
        return list;
    }

    @GetMapping("/category/{idCategory}")
    public List<Product> findByCategory(@PathVariable("idCategory") int idCategory) {
        List<Product> list = productService.finByCategory(idCategory);
        for (Product p : list) {
            System.out.println(p.toString());
        }
        return list;
    }

    @GetMapping("/price/{belowPrice}/{abovePrice}")
    public List<Product> findByCategory(@PathVariable("belowPrice") int belowPrice, @PathVariable("abovePrice") int abovePrice) {
        List<Product> list = productService.finByPrices(belowPrice, abovePrice);
        System.out.println("Price 1: " + abovePrice + "\tPrice 2: " + belowPrice);
        for (Product p : list) {
            System.out.println(p.toString());
        }
        return list;
    }

    //lọc sản phẩm
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterProduct(@RequestParam(value = "size", required = false) List<Integer> sizes,
                                                       @RequestParam(value = "brand", required = false) List<Integer> brands,
                                                       @RequestParam(value = "min_price", required = false) Optional<Integer> minPrice,
                                                       @RequestParam(value = "max_price", required = false) Optional<Integer> maxPrice) {

        List<Product> list = null;
        if (sizes!=null && brands !=null && minPrice.isPresent()==true && maxPrice .isPresent()==true)
            //all
            list = productService.filterProducts(sizes, brands, minPrice.get(), maxPrice.get());
        if (sizes==null && brands!=null && minPrice.isPresent()==true && maxPrice.isPresent()==true)
            //brand and price
            list = productService.filterProductsByBrandAndPrice(brands, minPrice.get(), maxPrice.get());
        if (sizes!=null && brands==null && minPrice.isPresent()==true && maxPrice.isPresent()==true)
            //sizes and price
            list = productService.filterProductsExceptBrand(sizes, minPrice.get(), maxPrice.get());
        if (sizes!=null && brands !=null && minPrice.isPresent()==false && maxPrice.isPresent()==false)
            //sizes and brands
            list = productService.filterProductsBySizesAndBrand(sizes, brands);
        if (sizes==null && brands ==null && minPrice.isPresent()==true && maxPrice.isPresent()==true)
            //price
            list = productService.filterProductsBYPrice(minPrice.get(), maxPrice.get());
        if (sizes==null && brands !=null && minPrice.isPresent()==false && maxPrice.isPresent()==false)
            //brand
            list = productService.filterProductsByBrand(brands);
        if (sizes!=null && brands ==null && minPrice.isPresent()==false && maxPrice.isPresent()==false)
            //sizes
            list = productService.filterProductsBySizes(sizes);

        try {
            if (list.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
