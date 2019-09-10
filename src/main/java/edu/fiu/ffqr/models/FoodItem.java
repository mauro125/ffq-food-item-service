package edu.fiu.ffqr.models;

import java.io.Serializable;
import java.util.ArrayList;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="food_items")
public class FoodItem implements Serializable {
	
  @Id
  @JsonProperty("id")
  private ObjectId id;
  @JsonProperty("name")
  private String name;
  @Field("servings")
  private ArrayList<ServingOptions> servingsList;
  @Field("foodTypes")
  private ArrayList<FoodType> foodTypes;
  @JsonProperty("sugar")
  private SugarSetting additionalSugar;
  @JsonProperty("primary")
  private boolean primary;
  
  // Constructors
  public FoodItem() {}
  
  public FoodItem(String name, ArrayList<ServingOptions> servings, ArrayList<FoodType> foodTypes) {
    this.id = new ObjectId();
    this.name = name;
    this.servingsList = servings;
    this.foodTypes = foodTypes;
    this.primary = false;
  } 
  
  public FoodItem(String name, ArrayList<ServingOptions> servings, ArrayList<FoodType> foodTypes, boolean primary) {
	this.id = new ObjectId();
    this.name = name;
    this.servingsList = servings;
	this.foodTypes = foodTypes;
	this.primary = true;
  }

  public FoodItem(String name, ArrayList<ServingOptions> servings, ArrayList<FoodType> foodTypes, SugarSetting addSugar) {
	this.id = new ObjectId();
	this.name = name;
	this.servingsList = servings;
	this.foodTypes = foodTypes;
	this.additionalSugar = addSugar;
	this.primary = false;
  }
  
  public FoodItem(String name, ArrayList<ServingOptions> servings, ArrayList<FoodType> foodTypes, SugarSetting addSugar, boolean primary) {
    this.id = new ObjectId();
    this.name = name;
	this.servingsList = servings;
	this.foodTypes = foodTypes;
	this.additionalSugar = addSugar;
	this.primary = true;
  }

  public FoodItem(String name, ArrayList<FoodType> foodTypes, SugarSetting addSugar) {
	this.id = new ObjectId();
	this.name = name;
	this.foodTypes = foodTypes;
	this.additionalSugar = addSugar;
	this.primary = false;
	
	//default serving
	ArrayList<ServingOptions> servingList = new ArrayList<ServingOptions>(); 
	servingList.add(new ServingOptions("1 OZ/unit"));
 	this.servingsList = servingList;
  } 
  
  public FoodItem(String name, ArrayList<FoodType> foodTypes, SugarSetting addSugar, boolean primary) {
	this.id = new ObjectId();
	this.name = name;
	this.foodTypes = foodTypes;
	this.additionalSugar = addSugar;
	this.primary = true;
		
	//default serving
	ArrayList<ServingOptions> servingList = new ArrayList<ServingOptions>(); 
	servingList.add(new ServingOptions("1 OZ/unit"));
	this.servingsList = servingList;
  }

  public FoodItem(String name, ArrayList<FoodType> foodTypes) {
	this.id = new ObjectId();
	this.name = name;
	this.foodTypes = foodTypes;
	this.primary = false;	
	
	//default serving
	ArrayList<ServingOptions> servingList = new ArrayList<ServingOptions>(); 
	servingList.add(new ServingOptions("1 OZ/unit"));
	this.servingsList = servingList;
  }
  
  public FoodItem(String name, ArrayList<FoodType> foodTypes, boolean primary) {
	this.id = new ObjectId();
	this.name = name;
	this.foodTypes = foodTypes;
	this.primary = true;	
		
	//default serving
	ArrayList<ServingOptions> servingList = new ArrayList<ServingOptions>(); 
	servingList.add(new ServingOptions("1 OZ/unit"));
	this.servingsList = servingList;
  }

  public String getName() { 
    return this.name; 
  }

  public ObjectId getId() { 
	return id;
  }
  
  public void setId(ObjectId id) {
	this.id = id;
  }
  
  public void setName(String name) { 
	this.name = name; 
  }

  public ArrayList<ServingOptions> getServingsList() {
	return servingsList;
  }

  public void setServingsList(ArrayList<ServingOptions> servingsList) {
    this.servingsList = servingsList;
  }
  
  public ArrayList<FoodType> getFoodTypes() {
    return this.foodTypes;
  }
  
  public void setFoodTypes(ArrayList<FoodType> foodTypes) {
    this.foodTypes = foodTypes;
  }
  
  public SugarSetting getAdditionalSugar() {
	return additionalSugar;
  }

  public void setAdditionalSugar(SugarSetting additionalSugar) {
	this.additionalSugar = additionalSugar;
  }

  public boolean isPrimary() {
	return primary;
  }

  public void setPrimary(boolean primary) {
	this.primary = primary;
  }

  
}