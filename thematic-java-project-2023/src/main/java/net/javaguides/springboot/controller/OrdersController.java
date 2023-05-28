package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.OrderDetail;
import net.javaguides.springboot.model.Orders;
import net.javaguides.springboot.repository.OrderDetailRepository;
import net.javaguides.springboot.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;



    @GetMapping("/{idUser}")
    public ResponseEntity<List<Orders>> getAllOrderByIdUser(@PathVariable("idUser") int idUser) {
        try {
            List<Orders> list= ordersRepository.getAllByIdUser(idUser);
            if(list.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Orders>>(list ,HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/detail/{idOrder}")
    public ResponseEntity<List<OrderDetail>> getAllOrderDetailByIdOrder(@PathVariable("idOrder") int idOrder) {
        try {
            List<OrderDetail> list= orderDetailRepository.getAllByIdOrder(idOrder);
            if(list.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<OrderDetail>>(list ,HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
