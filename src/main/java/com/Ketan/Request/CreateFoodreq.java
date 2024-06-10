package com.Ketan.Request;

import java.util.List;

import com.Ketan.model.Category;
import com.Ketan.model.Ingredientsitem;

import lombok.Data;

@Data
public class CreateFoodreq {
    private String name;
    private String description;
    private Long price;
    private Category foodcategory;
    private boolean available;
    private boolean isVeg;
    private boolean isSeasonal;
    private Long restaurantId;
    private List<String> images;
    private List<Ingredientsitem> ingredients;
}
