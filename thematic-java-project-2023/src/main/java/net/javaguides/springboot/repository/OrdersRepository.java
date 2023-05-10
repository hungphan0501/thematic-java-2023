package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> getAllById(int id);

    List<Orders> getAllByIdUser(int idUser);

    @Query("SELECT COUNT(o) FROM Orders o  WHERE o.status = 'Hoan tat'")
    int countOrdersCompleted();

    @Query("SELECT COUNT(o) FROM Orders o  WHERE o.status = 'Chờ xác nhận'")
    int countOrdersWaitingCompleted();

    @Query("SELECT SUM(o.totalPrice) FROM Orders o")
    double getRevenue();

    @Query("SELECT SUM(o.quantity) FROM OrderDetail o")
    int countProductSold();


}
