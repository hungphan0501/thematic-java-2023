package net.javaguides.springboot.repository;

import com.paypal.api.payments.Order;
import net.javaguides.springboot.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> getAllById(int id);

    List<Orders> getAllByIdUser(int idUser);

    @Query("SELECT COUNT(o) FROM Orders o  WHERE o.status = 'Hoan tat' AND o.createAt BETWEEN ?1 AND ?2")
    int countOrdersCompleted(Date dateStart, Date dateEnd);

    @Query("SELECT COUNT(o) FROM Orders o  WHERE o.status = 'Chờ xác nhận'")
    int countOrdersWaitingCompleted();

    @Query("SELECT SUM(o.totalPrice) FROM Orders o")
    double getRevenue();

    @Query("SELECT SUM(od.quantity) FROM OrderDetail od JOIN Orders o ON od.idOrder=o.id WHERE o.createAt BETWEEN ?1 AND ?2")
    int countProductSold(Date dateStart, Date dateEnd);

    @Query("SELECT o FROM Orders o WHERE o.createAt BETWEEN ?1 AND ?2")
    List<Orders> getRevenue(Date dateStart, Date dateEnd);

    @Query("SELECT SUM(o.totalPrice) FROM Orders o WHERE o.createAt BETWEEN ?1 AND ?2")
    double getWeekRevenue(Date dateStart, Date dateEnd);



}
