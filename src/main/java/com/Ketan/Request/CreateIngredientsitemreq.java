package com.Ketan.Request;

import lombok.Data;

@Data
public class CreateIngredientsitemreq {
    private String ingredientsname;
    private Long ingredientCategoryId;
    private Long restaurantid;
}
