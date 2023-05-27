package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    //    @Query("SELECT p.id, p.brand, p.name, p.category, p.price, p.sale_rate,p.star_rate, p.description,p.total_value, p.sole_value, p.Active, p.create_at, FROM product p INNER JOIN linkimg l ON p.id=l.idProduct WHERE l.level=0 AND p.id=?1")
    @Query("SELECT p FROM Product p WHERE p.brand=?1")
    public List<Product> findByBrand(int idBrand);

    @Query("SELECT p FROM Product p WHERE p.category=?1")
    public List<Product> findByCategory(int idCategory);

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN ?1 AND ?2 ORDER BY p.price ASC")
    public List<Product> findByPrices(int belowPrice, int abovePrice);

    //all
    @Query("SELECT p FROM Product p JOIN ProductDetail pd ON p.id=pd.idProduct WHERE pd.size IN :sizes AND p.brand IN :brands ORDER BY p.price DESC ")
    List<Product> findBySizeInAndBrandAndPriceBetweenDesc(@Param("sizes") List<Integer> sizes, @Param("brands") List<Integer> brands);

    //price and brands
    @Query("SELECT p FROM Product p JOIN ProductDetail pd ON p.id=pd.idProduct WHERE p.brand IN :brands ORDER BY p.price DESC")
    List<Product> findByBrandAndPriceBetween(@Param("brands") List<Integer> brands);

    //sizes and price
    @Query("SELECT p FROM Product p JOIN ProductDetail pd ON p.id=pd.idProduct WHERE pd.size IN :sizes  ORDER BY p.price DESC")
    List<Product> findBySizeInAndPriceBetween(@Param("sizes") List<Integer> sizes );

    //sizes and brand
    @Query("SELECT p FROM Product p JOIN ProductDetail pd ON p.id=pd.idProduct WHERE pd.size IN :sizes AND p.brand IN :brands")
    List<Product> findBySizeInAndBrand(@Param("sizes") List<Integer> sizes, @Param("brands") List<Integer> brands);

    //price
    @Query("SELECT p FROM Product p JOIN ProductDetail pd ON p.id=pd.idProduct  order by p.price DESC")
    List<Product> findByPrice();


    //brand
    @Query("SELECT p FROM Product p JOIN ProductDetail pd ON p.id=pd.idProduct WHERE p.brand IN :brands")
    List<Product> findByBrand(@Param("brands") List<Integer> brands);


    //sizes
    @Query("SELECT p FROM Product p JOIN ProductDetail pd ON p.id=pd.idProduct WHERE pd.size IN :sizes")
    List<Product> findBySizes(@Param("sizes") List<Integer> sizes);

    @Query("SELECT COUNT(p) FROM Product p ")
    int countAllProduct();

    @Query("SELECT SUM(p.price*p.saleRate/100) FROM Product p WHERE p.saleRate> 0")
    double getRevenueBySale();

    @Query("SELECT SUM(p.price*(100 - p.saleRate)/100) FROM Product p WHERE p.saleRate> 0")
    double getSpending();

    @Query("SELECT p FROM Product p JOIN Brand b ON p.brand = b.id WHERE b.name LIKE CONCAT('%', :name, '%')")
    List<Product> getAllByNameBrand(String name);

    @Query("SELECT p FROM Product p JOIN Category b ON p.brand = b.id WHERE b.name LIKE CONCAT('%', :name, '%')")
    List<Product> getAllByNameCategory(String name);

    List<Product> getAllByPrice(int price);

    @Query("SELECT p FROM Product p WHERE p.name LIKE CONCAT('%', :name, '%')")
    List<Product> getAllByName(String name);


    @Query(value = "SELECT * FROM Product ORDER BY id ASC LIMIT 20", nativeQuery = true)
    List<Product> getManage();

    Product getProductById(int id);

    @Query("SELECT p FROM Product p WHERE p.saleRate=?1 ORDER BY p.create_at ASC")
    List<Product> getAllProductNew(int sale);

    @Query("SELECT p FROM Product p ORDER BY p.saleRate DESC")
    List<Product> getAllProductDiscount();

    @Query("SELECT p FROM Product p" )
    List<Product> getAllProductBestSeller();
}
