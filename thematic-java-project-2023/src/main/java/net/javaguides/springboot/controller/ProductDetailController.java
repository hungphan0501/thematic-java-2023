package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.ProductDetail;
import net.javaguides.springboot.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/productDetail")
public class ProductDetailController {
    @Autowired
    ProductDetailService productDetailService;

    @GetMapping("")
    public ResponseEntity<List<ProductDetail>> getAllProductDetailByIdProduct(@RequestParam("id") int idProduct) {
        try {
            List<ProductDetail> list = productDetailService.getAllProductDetailByIdProduct(idProduct);
            if (list.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<List<ProductDetail>>(list, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getQuantity/{idProduct}")
    public Map<Integer, Integer> getQuantityInStock(@PathVariable int idProduct) {
        Map<Integer, Integer> result = new HashMap<>();
        List<ProductDetail> list = productDetailService.getAllProductDetailByIdProduct(idProduct);
        if (!list.isEmpty()) {
            for (ProductDetail productDetail : list) {
                result.put(productDetail.getId(),productDetail.getTotalValue() - productDetail.getSoleValue());
            }
        }
        return result;
    }

    @GetMapping("/getQuantity/{idProduct}/{color}")
    public Map<Integer, Integer> getQuantityInStockOfOneColor(@PathVariable int idProduct, @PathVariable String color) {
        Map<Integer, Integer> result = new HashMap<>();
        List<ProductDetail> list = productDetailService.getAllProductDetailByIdProduct(idProduct);
        if (!list.isEmpty()) {
            for (ProductDetail productDetail : list) {
                if(productDetail.getColor().equals(color)) {
                    result.put(productDetail.getId(),productDetail.getTotalValue() - productDetail.getSoleValue());
                }
            }
        }
        return result;
    }

    @GetMapping("/getColorOfProduct/{idProduct}")
    public List<String> getAllColorOfProduct(@PathVariable int idProduct) {

        List<String> listColor = new ArrayList<>();
        List<ProductDetail> productDetailList = productDetailService.getAllProductDetailByIdProduct(idProduct);
        List<String> result = null;
        if (!productDetailList.isEmpty()) {
            for (ProductDetail productDetail : productDetailList) {
                listColor.add(productDetail.getColor());
            }

            Set<String> set = new LinkedHashSet<>(listColor);
            result = new ArrayList<>(set);
        }
        return result;
    }
}
