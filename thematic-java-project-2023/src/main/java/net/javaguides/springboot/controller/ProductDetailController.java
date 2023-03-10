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

    //l???y size c???a 1 m??u c???a 1 s???n ph???m
    @GetMapping("/getSizeOfColor/{idProduct}/{color}")
    public List<Integer> getSizeOfColor(@PathVariable int idProduct,@PathVariable String color) {
        List<Integer> listSize = new ArrayList<>();
        List<ProductDetail> productDetailList = productDetailService.getAllProductDetailByIdProduct(idProduct);
        if (!productDetailList.isEmpty()) {
            for (ProductDetail productDetail : productDetailList) {
                if(productDetail.getColor().equals(color)){
                    listSize.add(productDetail.getSize());
                }
            }
        }
        return listSize;
    }

    //c??ch 2 l?? l???y danh s??ch m??u v?? size t????ng ???ng c???a m??u
    @GetMapping("/getAllSizeOfColor/{idProduct}")
    public Map<String, List<Integer>> getAllSizeOfColor (@PathVariable int idProduct) {
        Map<String, List<Integer>> result = new HashMap<>();
        List<ProductDetail> productDetailList = productDetailService.getAllProductDetailByIdProduct(idProduct);

        List<String> listColor = getAllColorOfProduct(idProduct);
        if (!productDetailList.isEmpty()) {
            for (String color : listColor) {
                List<Integer> listSize = new ArrayList<>(); // T???o m???t List m???i cho m???i m??u s???c
                for (ProductDetail productDetail : productDetailList) {
                    if (productDetail.getColor().equals(color)) {
                        listSize.add(productDetail.getSize());
                    }
                }
                result.put(color, listSize); // Th??m ?????i t?????ng List m???i v??o Map
            }
        }
        return result;
    }

    /**
     * X??? L?? C???A FRONTEND
     * Trong page cart c?? danh s??ch c??c s???n ph???m trong gi??? h??ng
     * ng?????i d??ng mu???n mua 1 s??? s???n ph???m trong danh s??ch ???? th?? c???n ph???i ch???n v??o check box
     * FrontEnd x??? d???ng c??ch d?????i ????? g???i danh s??ch ???????c ch???n xu???ng ????? BackEnd x??? l?? v?? ti???p t???c thanh to??n
     *
     * const selectedCarts = [
     *   {
     *     id: 1,
     *     idProduct: 1,
     *     size: 'M',
     *     color: 'Black',
     *     quantity: 2,
     *     totalPrice: 200000
     *   },
     *   {
     *     id: 2,
     *     idProduct: 2,
     *     size: 'L',
     *     color: 'White',
     *     quantity: 1,
     *     totalPrice: 100000
     *   }
     * ];
     *
     * $.ajax({
     *   url: '/carts/checkout',
     *   type: 'POST',
     *   data: JSON.stringify(selectedCarts),
     *   contentType: 'application/json',
     *   success: function(response) {
     *     console.log(response);
     *     // X??? l?? k???t qu??? tr??? v??? t??? server (n???u c??)
     *   },
     *   error: function(error) {
     *     console.error(error);
     *     // X??? l?? l???i (n???u c??)
     *   }
     * });
     * **/

}
