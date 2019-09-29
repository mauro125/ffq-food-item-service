package edu.fiu.ffqr.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodNutrients implements Serializable {
	
	@JsonProperty("foodItem")
	private FoodItem foodItem;
	@JsonProperty("nutrientList")
	private NutrientList nutrientList;
	
	public FoodNutrients(){}
	
	public FoodNutrients(FoodItem foodItem, NutrientList nutrientList){
		this.setFoodItem(foodItem);
		this.setNutrientList(nutrientList);
	}

	public FoodItem getFoodItem() {
		return foodItem;
	}

	public void setFoodItem(FoodItem foodItem) {
		this.foodItem = foodItem;
	}

	public NutrientList getNutrientList() {
		return nutrientList;
	}

	public void setNutrientList(NutrientList nutrientList) {
		this.nutrientList = nutrientList;
	}
}
