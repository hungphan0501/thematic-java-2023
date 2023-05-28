package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.*;
import net.javaguides.springboot.repository.*;
import net.javaguides.springboot.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
@RequestMapping("/product/detail")
public class ProductDetailController {
    @Autowired
    ProductDetailService productDetailService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    LinkImgRepository linkImgRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;
    @GetMapping("/{idProduct}")
    public String getProductDetailPage(@PathVariable("idProduct") int idProduct, Model model) {
        Product product = productRepository.getProductById(idProduct);
        List<ProductDetail> list = productDetailService.getAllProductDetailByIdProduct(idProduct);
        List<LinkImg> linkImgs = getAllImgByIdProduct(idProduct);
        List<Comment> comments = commentRepository.getAllByIdProduct(idProduct);
        List<Product> getRelatedProducts = getRelatedProducts(idProduct);
        double totalPrice = 0;
        List<Cart> carts = getCartsOfUser();
        if(carts!= null){
            for (Cart cart : carts) {
                totalPrice += cart.getTotalPrice();
            }
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("carts", carts);
        model.addAttribute("comments", comments);
        model.addAttribute("productsRelate", getRelatedProducts);
        model.addAttribute("product", product);
        model.addAttribute("details", list);
        model.addAttribute("linkImgs", linkImgs);

        return "user/product";
    }
    public List<Cart> getCartsOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            if(user != null){
                return cartRepository.getAllProductInCartOfCustomer(user.getId());
            }
            return null;

        } else return null;

    }
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
                result.put(productDetail.getId(), productDetail.getQuantity());
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
                if (productDetail.getColor().equals(color)) {
                    result.put(productDetail.getId(), productDetail.getQuantity());
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
    public List<Integer> getSizeOfColor(@PathVariable int idProduct, @PathVariable String color) {
        List<Integer> listSize = new ArrayList<>();
        List<ProductDetail> productDetailList = productDetailService.getAllProductDetailByIdProduct(idProduct);
        if (!productDetailList.isEmpty()) {
            for (ProductDetail productDetail : productDetailList) {
                if (productDetail.getColor().equals(color)) {
                    listSize.add(productDetail.getSize());
                }
            }
        }
        return listSize;
    }

    //cách 2 là lấy danh sách màu và size tương ứng của màu
    @GetMapping("/getAllSizeOfColor/{idProduct}")
    public Map<String, List<Integer>> getAllSizeOfColor(@PathVariable int idProduct) {
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

    public List<LinkImg> getAllImgByIdProduct(int idProduct) {
        try {
            List<LinkImg> list = linkImgRepository.getAllByIdProduct(idProduct);
            if (!list.isEmpty()) {
                return list;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Product> getRelatedProducts(int idProduct) {
        List<Product> result = new ArrayList<>();
        Product product = productRepository.getProductById(idProduct);
        List<Product> list1 = productRepository.findByBrand(product.getBrand());
        List<Product> list2 = productRepository.findByCategory(product.getCategory());
        if (list1.size() > 4) {
            result = list1.subList(0, 4);
        }
        if (list2.size() > 4) {
            for (int i = 0 ; i<list2.size();i++){
                if(i <4){
                    result.add(list2.get(i));
                }
            }
        }
        Collections.shuffle(result);
        return result;
    }


}
