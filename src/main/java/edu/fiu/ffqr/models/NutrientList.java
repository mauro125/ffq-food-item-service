package edu.fiu.ffqr.models;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "nutrient_lists")
public class NutrientList {
	
	@Id
    private ObjectId id;
	@JsonProperty("nutrientListID")	
	private String nutrientListID;
	@JsonProperty("nutrientMap")
	Map <String,Double> nutrientMap = new HashMap<String,Double>();	
	
	public NutrientList() {}
	
	public NutrientList(String nutrientListID, Map<String, Double> nutrientMap) {
		//check that all nutrients are valid
		for (String nutrient: nutrientMap.keySet()) {
			if (!Arrays.stream(ValidNutrientList.validNutrients).anyMatch(nutrient::equals)) {
				throw new IllegalArgumentException("The nutrient " + nutrient + " is not valid");
			}
		}
		
		this.id = new ObjectId();
		this.nutrientListID = nutrientListID;
		this.nutrientMap = nutrientMap;
	}

	public String getId() {
		return id.toHexString();
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getNutrientListID() {
		return nutrientListID;
	}

	public void setNutrientListID(String nutrientListID) {
		this.nutrientListID = nutrientListID;
	}

	public Map<String, Double> getNutrientMap() {
		return nutrientMap;
	}

	public void setNutrientMap(Map<String, Double> nutrientMap) {
		//check that all nutrients are valid
		for (String nutrient: nutrientMap.keySet()) {
			if (!Arrays.stream(ValidNutrientList.validNutrients).anyMatch(nutrient::equals)) {
				throw new IllegalArgumentException("The nutrient " + nutrient + " is not valid");
			}
	    }
		
		this.nutrientMap = nutrientMap;
	}
	
	public double getNutrient(String nutrient) {
		return nutrientMap.get(nutrient);
	}
	
}
