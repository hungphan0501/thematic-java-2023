package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    List<OrderDetail> getAllByIdOrder(int idOrder);
}
