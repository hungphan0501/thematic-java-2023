package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Integer> {

    @Query("SELECT b.name FROM Brand b WHERE b.id=?1")
    String getNameById(int id);

    Brand getBrandById(int id);
}
