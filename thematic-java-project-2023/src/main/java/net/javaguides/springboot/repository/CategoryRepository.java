package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category getCategoryById(int id);
}
