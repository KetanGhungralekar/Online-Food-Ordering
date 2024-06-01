package com.Ketan.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ketan.Repository.AddressRepo;
import com.Ketan.Repository.RestaurantRepository;
import com.Ketan.Repository.UserRepository;
import com.Ketan.Request.CreateRestaurantreq;
import com.Ketan.dto.Restaurant_dti;
import com.Ketan.model.Address;
import com.Ketan.model.Restaurant;
import com.Ketan.model.User;

@Service
public class RestServiceImp implements RestaurantService{
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private UserRepository userrepo;

    @Override
    public Restaurant CreateRestaurant(CreateRestaurantreq req, User user) {
        Address address = addressRepo.save(req.getAddress());
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setName(req.getName());
        restaurant.setOpening_hours(req.getOpeningTime());
        restaurant.setImages(req.getImages());
        restaurant.setRegistrationdate(LocalDateTime.now());
        restaurant.setOwner(user);
        restaurant.setOpen(true);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantid, CreateRestaurantreq req) throws Exception {
        Restaurant restaurant = restaurantRepository.findById(restaurantid).orElseThrow(() -> new Exception("Restaurant not found"));
        if (req.getAddress() != null){
            Address address = addressRepo.save(req.getAddress());
            restaurant.setAddress(address);
        }
        if (req.getContactInformation() != null){
            restaurant.setContactInformation(req.getContactInformation());
        }
        if (req.getCuisineType() != null){
            restaurant.setCuisineType(req.getCuisineType());
        }
        if (req.getDescription() != null){
            restaurant.setDescription(req.getDescription());
        }
        if (req.getName() != null){
            restaurant.setName(req.getName());
        }
        if (req.getOpeningTime() != null){
            restaurant.setOpening_hours(req.getOpeningTime());
        }
        if (req.getImages() != null){
            restaurant.setImages(req.getImages());
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant getRestaurant(Long restaurantid) throws Exception {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantid);
        if (!restaurant.isPresent()){
            throw new Exception("Restaurant not found");
        }
        return restaurant.get(); 
    }  

    @Override
    public void deleteRestaurant(Long restaurantid) throws Exception {
        Restaurant restaurant = getRestaurant(restaurantid);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    // @Override
    // public List<Restaurant> searchRestaurants(String keyword) {
    //     return restaurantRepository.findBySearchQuery(keyword);
    // }

    @Override
    public List<Restaurant> searchRestaurantsByCuisine(String cuisine) throws Exception {
        return restaurantRepository.findByCuisineType(cuisine);
    }

    @Override
    public Restaurant getRestaurantByUserid(Long userid) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userid);
        if (restaurant == null){
            throw new Exception("Restaurant not found");
        }
        return restaurant;
    }

    @Override
    public Restaurant_dti add_to_favourites(Long restaurantid, User user) throws Exception {
        Restaurant restaurant = getRestaurant(restaurantid);
        Restaurant_dti restaurant_dti = new Restaurant_dti();
        restaurant_dti.setDescription(restaurant.getDescription());
        restaurant_dti.setId(restaurantid);
        restaurant_dti.setTitle(restaurant.getName());
        if (user.getFavourites().contains(restaurant_dti)){
            user.getFavourites().remove(restaurant_dti);
        }
        else{
            user.getFavourites().add(restaurant_dti);
        }
        userrepo.save(user);
        return restaurant_dti;
    }

    @Override
    public Restaurant update_restaurant_status(Long restaurantid) throws Exception {
        Restaurant restaurant = getRestaurant(restaurantid);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}
