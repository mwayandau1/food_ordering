package com.code.food_ordering.service;


import com.code.food_ordering.dto.RestaurantDto;
import com.code.food_ordering.dto.RestaurantRequestDto;
import com.code.food_ordering.entity.Address;
import com.code.food_ordering.entity.Restaurant;
import com.code.food_ordering.entity.User;
import com.code.food_ordering.repository.AddressRepository;
import com.code.food_ordering.repository.RestaurantRepository;
import com.code.food_ordering.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Restaurant createRestaurant(RestaurantRequestDto req, User user) throws Exception {
        Address address = addressRepository.save(req.getAddress());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setName(req.getName());
        restaurant.setDescription(req.getDescription());
        restaurant.setContactInfo(restaurant.getContactInfo());
        restaurant.setImages(req.getImages());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setOwner(user);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, RestaurantRequestDto req) throws Exception {
        Restaurant restaurant = getARestaurantById(restaurantId);


        if(req.getName() != null){
            restaurant.setName(req.getName());
        }
        if(req.getContactInfo() != null){
            restaurant.setContactInfo(req.getContactInfo());
        }if(req.getDescription() != null){
            restaurant.setDescription(req.getDescription());
        }if(req.getCuisineType() != null){
            restaurant.setCuisineType(req.getCuisineType());
        }if(req.getOpeningHours() != null){
            restaurant.setOpeningHours(req.getOpeningHours());
        }if(req.getImages() != null){
            restaurant.setImages(req.getImages());
        }if(req.getAddress() != null) {
            restaurant.setAddress(req.getAddress());
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = getRestaurantByUserId(restaurantId);

        restaurantRepository.delete(restaurant);

    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return  restaurantRepository.findAll();
    }

    @Override
    public Restaurant getARestaurantById(Long restaurantId) throws Exception {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if(optionalRestaurant.isEmpty()){
            throw new Exception("No restaurant found with this Id "+restaurantId);
        }
        return optionalRestaurant.get();
    }

    @Override
    public List<Restaurant> searchRestaurant(String query) {
        return restaurantRepository.findBySearchQuery(query);
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if(restaurant == null){
            throw new Exception("No restaurant found with user Id "+userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = getARestaurantById(restaurantId);

        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setImages(restaurant.getImages());
        restaurantDto.setTitle(restaurant.getName());
        restaurantDto.setId(restaurantId);

        if(user.getFavorites().contains(restaurantDto)){
            user.getFavorites().remove(restaurantDto);
        }
        else{
            user.getFavorites().add(restaurantDto);

        }
        userRepository.save(user);
        return restaurantDto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception {
        Restaurant restaurant = getARestaurantById(restaurantId);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}
