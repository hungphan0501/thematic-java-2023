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

    //lấy size của 1 màu của 1 sản phẩm
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

    //cách 2 là lấy danh sách màu và size tương ứng của màu
    @GetMapping("/getAllSizeOfColor/{idProduct}")
    public Map<String, List<Integer>> getAllSizeOfColor (@PathVariable int idProduct) {
        Map<String, List<Integer>> result = new HashMap<>();
        List<ProductDetail> productDetailList = productDetailService.getAllProductDetailByIdProduct(idProduct);

        List<String> listColor = getAllColorOfProduct(idProduct);
        if (!productDetailList.isEmpty()) {
            for (String color : listColor) {
                List<Integer> listSize = new ArrayList<>(); // Tạo một List mới cho mỗi màu sắc
                for (ProductDetail productDetail : productDetailList) {
                    if (productDetail.getColor().equals(color)) {
                        listSize.add(productDetail.getSize());
                    }
                }
                result.put(color, listSize); // Thêm đối tượng List mới vào Map
            }
        }
        return result;
    }

    /**
     * XỬ LÝ CỦA FRONTEND
     * Trong page cart có danh sách các sản phẩm trong giỏ hàng
     * người dùng muốn mua 1 số sản phẩm trong danh sách đó thì cần phải chọn vào check box
     * FrontEnd xử dụng cách dưới để gửi danh sách được chọn xuống để BackEnd xử lý và tiếp tục thanh toán
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
     *     // Xử lý kết quả trả về từ server (nếu có)
     *   },
     *   error: function(error) {
     *     console.error(error);
     *     // Xử lý lỗi (nếu có)
     *   }
     * });
     * **/

}
