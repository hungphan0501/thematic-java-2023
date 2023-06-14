package net.javaguides.springboot.controller.Admin;

import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.model.ProductDetail;
import net.javaguides.springboot.repository.ProductDetailRepository;
import net.javaguides.springboot.repository.ProductRepository;
import net.javaguides.springboot.service.Admin.ProductEditDTo;
import net.javaguides.springboot.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductEditController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductDetailService productDetailService;

    @Autowired
    ProductDetailRepository productDetailRepository;

    @GetMapping("/products-manage/edit/id/{idProduct}")
    public String getProductEdit(@PathVariable("idProduct") int idProduct, Model model) {
        Optional<Product> optProduct = productRepository.findById(idProduct);
        if (optProduct.isPresent()) {
            Product product = optProduct.get();
            List<ProductDetail> productDetailList = productDetailService.getAllProductDetailByIdProduct(idProduct);
            model.addAttribute("product", product);
            ProductEditDTo productEditDTo = new ProductEditDTo();
            productEditDTo.setProduct(product);
            productEditDTo.setProductDetails(productDetailList);
            model.addAttribute("productEditDTo", productEditDTo);
        }

        return "admin/views/dist/product-edit";
    }

    @PostMapping("/products-manage/edit/id/{idProduct}")
    public String updateProduct(@ModelAttribute("productEditDTo") ProductEditDTo productEditDTo, Model model) {
        System.out.println("ProductEditDTo: " + productEditDTo.toString());
        Product product = productEditDTo.getProduct();
        List<ProductDetail> productDetailList = productEditDTo.getProductDetails();
        productRepository.save(product);
        for (ProductDetail productDetail : productDetailList) {
            Optional<ProductDetail> currentProductDetail = productDetailRepository.findById(productDetail.getId());
            if (currentProductDetail.isPresent()) {
                ProductDetail saveProductDetail = currentProductDetail.get();
                saveProductDetail.setSize(productDetail.getSize());
                saveProductDetail.setQuantity(productDetail.getQuantity());
                saveProductDetail.setColor(productDetail.getColor());
                productDetailRepository.save(saveProductDetail);
            }
        }
        model.addAttribute("success", "Cập nhật sản phẩm thành công");
        return "admin/views/dist/product-edit";
    }

}
