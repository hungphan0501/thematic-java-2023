package net.javaguides.springboot.controller.Admin;

import net.javaguides.springboot.model.Brand;
import net.javaguides.springboot.model.Category;
import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.model.ProductDetail;
import net.javaguides.springboot.repository.BrandRepository;
import net.javaguides.springboot.repository.CategoryRepository;
import net.javaguides.springboot.repository.ProductDetailRepository;
import net.javaguides.springboot.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class ProductAddController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductDetailRepository productDetailRepository;

    @PostMapping("/product/addProduct")
    public String addProduct(Product product, Model model) {

        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
            String createAt = now.format(formatter);
            Product productSave = new Product(product.getBrand(), product.getName(), product.getCategory(), product.getPrice(), product.getSaleRate(), product.getTotalValue(), product.getSoleValue(), createAt, createAt, product.getDescription());
            productRepository.save(productSave);
            ProductDetail productDetail = new ProductDetail();
            productDetail.setIdProduct(productSave.getId());
            model.addAttribute("productDetail", productDetail);
            System.out.println("productSave: " + productSave.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin/views/dist/product-add-detail";
    }

    public List<Brand> getAllBrand() {
        return brandRepository.findAll();
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @PostMapping("/product/addProduct/details/")
    public String addDetailsProduct(ProductDetail productDetail) {
        System.out.println("productDetailFromForm: " + productDetail.toString());

        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
            String updateAt = now.format(formatter);
            ProductDetail productDetailSave = new ProductDetail(productDetail.getIdProduct(), productDetail.getSize(), productDetail.getQuantity(), productDetail.getCreateAt(), updateAt, 1, productDetail.getColor());
            productDetailRepository.save(productDetailSave);
            System.out.println("productDetail: " + productDetailSave.toString());
            return "redirect:/products-manage/id/" + productDetailSave.getIdProduct();
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/products-manage";
        }
    }

    @GetMapping("/products-manage/id/{idProduct}")
    public String getListProductManage(@PathVariable int idProduct, Model model) {

        try {
            List<Product> productList = productRepository.findAllById(Collections.singleton(idProduct));
            if (!productList.isEmpty()) {
                model.addAttribute("products", productList);
                return "admin/views/dist/products-list";
            }
            return "redirect:/products-manage";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/products-manage";
        }
    }

    @PostMapping("/products-manage")
    public String getListProductManageForm(@RequestParam("type") String type, @RequestParam("value") String value, @RequestParam("sort") String sort, Model model) {
        String error = "";
        List<Product> productList;
        try {
            switch (type) {
                case "id":
                    if (isNumber(value) == true) {
                        int id = Integer.parseInt(value);
                        productList = productRepository.findAllById(Collections.singleton(id));
                        if (productList.isEmpty()) {
                            error = "ID sản phẩm không đúng!";
                            model.addAttribute("error", error);
                        } else {
                            model.addAttribute("products", productList);
                        }
                    } else {
                        error = "ID không phải là số!";
                        model.addAttribute("error", error);

                    }
                    break;
                case "brand":
                    productList = productRepository.getAllByNameBrand(value);
                    sortProducts(sort,productList,"id");
                    if (productList.isEmpty()) {
                        error = "Tên brand không đúng hoặc không tồn tại sản phẩm nào theo yêu cầu!";
                        model.addAttribute("error", error);
                    } else {
                        model.addAttribute("products", productList);
                    }
                    break;

                case "name":
                    if (value.equals("")) {
                        error = "Tên Sản không được để trống!";
                        model.addAttribute("error", error);
                    } else {
                        productList = productRepository.getAllByName(value);
                        sortProducts(sort,productList,"name");
                        if (productList.isEmpty()) {
                            error = "Tên Sản không tồn tại!";
                            model.addAttribute("error", error);
                        } else {
                            model.addAttribute("products", productList);
                        }
                    }

                    break;

                case "price":
                    if (isNumber(value) == true) {
                        int price = Integer.parseInt(value);
                        productList = productRepository.getAllByPrice(price);
                        sortProducts(sort,productList,"id");
                        if (productList.isEmpty()) {
                            error = "Không có sản phầm nào tồn tại với giá tiền " + price + "!";
                            model.addAttribute("error", error);
                        } else {
                            model.addAttribute("products", productList);
                        }
                    } else {
                        error = "Giá tiền không đúng định dạng số!";
                        model.addAttribute("error", error);

                    }
                    break;

                default:
                    break;
            }
            return "admin/views/dist/products-list";

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String getNameBrandById(int idBrand) {
        return brandRepository.getNameById(idBrand);
    }

    public boolean isNumber(String str) {
        if (str == null || str.isEmpty() || str.equals("")) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public List<Product> sortProducts(String sort, List<Product> productList, String type) {
        int num = Integer.parseInt(sort);
        if (num == 0) {
            switch (type) {
                case "id":
                    Collections.sort(productList, Comparator.comparing(Product::getId));
                    break;
                case "name":
                    Collections.sort(productList, Comparator.comparing(Product::getName));
                    break;
                case "price":
                    Collections.sort(productList, Comparator.comparing(Product::getPrice));
                    break;
                default:
                    break;
            }

        } else if (num == 1) {
            switch (type) {
                case "id":
                    Collections.sort(productList, Comparator.comparing(Product::getId).reversed());
                    break;
                case "name":
                    Collections.sort(productList, Comparator.comparing(Product::getName).reversed());
                    break;
                case "price":
                    Collections.sort(productList, Comparator.comparing(Product::getPrice).reversed());
                    break;
                default:
                    break;
            }
        }
        return productList;
    }

}
