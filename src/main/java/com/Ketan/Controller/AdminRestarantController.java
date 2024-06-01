package com.Ketan.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.Ketan.Request.CreateRestaurantreq;
import com.Ketan.Service.RestaurantService;
import com.Ketan.Service.UserService;
import com.Ketan.model.Restaurant;
import com.Ketan.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestarantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")    
    public ResponseEntity<Restaurant> CreateRestaurant(@RequestBody CreateRestaurantreq req, @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.FindUserByJwt(jwt);
        Restaurant restaurant = restaurantService.CreateRestaurant(req, user);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }
}
