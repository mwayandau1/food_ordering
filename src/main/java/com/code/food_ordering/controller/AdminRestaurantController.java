package com.code.food_ordering.controller;


import com.code.food_ordering.dto.RestaurantRequestDto;
import com.code.food_ordering.entity.Restaurant;
import com.code.food_ordering.service.RestaurantService;
import com.code.food_ordering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    ResponseEntity<Restaurant> createRestaurant(@RequestBody RestaurantRequestDto req, @RequestHeader("Authorization") String jwt){
        return null;
    }


    @GetMapping("/restaurants")
    ResponseEntity<Restaurant> getAllRestaurants(){
        return null;
    }
}
