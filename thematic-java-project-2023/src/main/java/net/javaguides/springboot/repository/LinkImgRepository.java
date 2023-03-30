package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.LinkImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkImgRepository extends JpaRepository<LinkImg,Integer> {
    List<LinkImg> getAllByIdProduct(int idProduct);
    LinkImg getLinkImgByIdProductAndLevel(int idProduct, int active);
}
