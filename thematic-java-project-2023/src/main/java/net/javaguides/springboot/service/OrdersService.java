package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Orders;
import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.repository.OrdersRepository;
import net.javaguides.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

@Service
public class OrdersService {
    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    UserRepository userRepository;

    public double getRevenue(int date) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String createAt = now.format(formatter);
        Date dateEnd = Date.valueOf(createAt);

        //dateStart
        LocalDate subtractedDate = LocalDate.from(now.minusDays(date));
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String createAt2 = subtractedDate.format(formatter2);
        Date dateStart = Date.valueOf(createAt2);
        List<Orders> ordersList = ordersRepository.getRevenue(dateStart, dateEnd);
        double result = 0.0;
        if (ordersList ==null) {
            return 0.0;
        }
        else {
            for (Orders o : ordersList) {
               result+= o.getTotalPrice() *24000;
            }
        }
        return result;
    }

    public double getWeekRevenue() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String createAt = now.format(formatter);
        Date dateEnd = Date.valueOf(createAt);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateEnd);
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date dateStart = new Date(calendar.getTimeInMillis());
        System.out.println("dateStart : " +dateStart + "\tdateEnd: " +dateEnd);
        double result = ordersRepository.getWeekRevenue(dateStart,dateEnd);
        if (result > 0) {
            return result * 24000;
        }
        return 0.0;
    }

    public int countUserWithRoleUser(int date) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String createAt = now.format(formatter);
        Date dateEnd = Date.valueOf(createAt);

        //dateStart
        LocalDate subtractedDate = LocalDate.from(now.minusDays(date));
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String createAt2 = subtractedDate.format(formatter2);
        Date dateStart = Date.valueOf(createAt2);
        int result = userRepository.countUserWithRoleUser(dateStart,dateEnd);
        if (result > 0) {
            return result;
        }
        return 0;
    }
    public int countOrders(int date) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String createAt = now.format(formatter);
        Date dateEnd = Date.valueOf(createAt);

        //dateStart
        LocalDate subtractedDate = LocalDate.from(now.minusDays(date));
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String createAt2 = subtractedDate.format(formatter2);
        Date dateStart = Date.valueOf(createAt2);
        int result = ordersRepository.countOrdersCompleted(dateStart,dateEnd);
        if (result > 0) {
            return result;
        }
        return 0;
    }

    public int countProductSold(int date) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String createAt = now.format(formatter);
        Date dateEnd = Date.valueOf(createAt);

        //dateStart
        LocalDate subtractedDate = LocalDate.from(now.minusDays(date));
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String createAt2 = subtractedDate.format(formatter2);
        Date dateStart = Date.valueOf(createAt2);
        int result = ordersRepository.countProductSold(dateStart,dateEnd);
        if (result > 0) {
            return result;
        }
        return 0;
    }

    public void removeOrder(int orderId) {
        // Kiểm tra xem sản phẩm có tồn tại trong cơ sở dữ liệu hay không
        Orders order = ordersRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));

        // Xóa sản phẩm
        ordersRepository.delete(order);
    }
}
