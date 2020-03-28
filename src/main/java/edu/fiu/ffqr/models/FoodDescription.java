package edu.fiu.ffqr.models;
import java.io.Serializable;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="food_description")
public class FoodDescription implements Serializable {

    @Id
    private ObjectId _id;
    @JsonProperty("imageUrl")
    private String imageUrl;
    @Field("foodItemGroupName")
    private String foodItemGroupName;
    @Field("dailyFoodIntake")
    private String dailyFoodIntake;
    @Field("description")
    private String description;


    // Constructors
    public FoodDescription() {
    }

    public FoodDescription(String imageUrl, String foodItemGroupName, String dailyFoodIntake, String description) {
        this._id = new ObjectId();
        this.imageUrl = imageUrl;
        this.foodItemGroupName = foodItemGroupName;
        this.dailyFoodIntake = dailyFoodIntake;
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFoodItemGroupName() {
        return foodItemGroupName;
    }

    public void setFoodItemGroupName(String foodItemGroupName) {
        this.foodItemGroupName = foodItemGroupName;
    }

    public String getDailyFoodIntake() {
        return dailyFoodIntake;
    }

    public void setDailyFoodIntake(String dailyFoodIntake) {
        this.dailyFoodIntake = dailyFoodIntake;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String findByDailyFoodIntake() {
	    return this.dailyFoodIntake;
	  }
}