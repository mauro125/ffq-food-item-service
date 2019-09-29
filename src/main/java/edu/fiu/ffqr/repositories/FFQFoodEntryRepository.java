package edu.fiu.ffqr.repositories;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import edu.fiu.ffqr.models.FoodItem;

public interface FFQFoodEntryRepository extends MongoRepository<FoodItem, String> {
	
  FoodItem findByName(String name);
  
  @Query(value = "{ 'foodTypes.nutrientListID' : ?0 }", fields = "{ 'foodTypes.nutrientListID' : 0 }")
  FoodItem findByNutrientListID(String nutrientListID);
  
}