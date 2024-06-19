package com.Ketan.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ketan.Request.CreateOrderreq;
import com.Ketan.Service.OrderService;
import com.Ketan.Service.UserService;
import com.Ketan.model.User;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@RequestHeader("Authorization") String jwt,@RequestBody CreateOrderreq order){
        try {
            User user = userService.FindUserByJwt(jwt);
            return ResponseEntity.ok(orderService.CreateOrderItem(order,user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/getOrders")
    public ResponseEntity<?> getOrderHistory(@RequestHeader("Authorization") String jwt){
        try {
            User user = userService.FindUserByJwt(jwt);
            return ResponseEntity.ok(orderService.getOrdersByUser(user.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/getOrder/{orderId}")
    public ResponseEntity<?> getOrder(@RequestHeader("Authorization") String jwt,@PathVariable Long orderId){
        try {
            User user = userService.FindUserByJwt(jwt);
            return ResponseEntity.ok(orderService.getOrder(orderId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
