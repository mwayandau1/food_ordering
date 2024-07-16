package com.code.food_ordering.service;

import com.code.food_ordering.dto.RestaurantDto;
import com.code.food_ordering.dto.RestaurantRequestDto;
import com.code.food_ordering.entity.Restaurant;
import com.code.food_ordering.entity.User;

import java.util.List;

public interface RestaurantService {


    public Restaurant createRestaurant(RestaurantRequestDto req, User user) throws Exception;

    public Restaurant updateRestaurant(Long restaurantId, RestaurantRequestDto req) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurants() ;

    public Restaurant getARestaurantById(Long restaurantId) throws Exception;

    public List<Restaurant> searchRestaurant(String query);

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception;



}
