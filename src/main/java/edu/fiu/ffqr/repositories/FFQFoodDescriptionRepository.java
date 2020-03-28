package edu.fiu.ffqr.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import edu.fiu.ffqr.models.FoodDescription;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.data.mongodb.core.MongoTemplate;

@Repository
public interface FFQFoodDescriptionRepository extends MongoRepository<FoodDescription, String> {

  //@Query(value = "{ 'dailyFoodIntake' : ?0 }", fields = "{ 'dailyFoodIntake' : 0 }")
  FoodDescription getCollectionByFoodItemGroupName(String foodItemGroupName);

  FoodDescription findByDailyFoodIntake(String dailyFoodIntake);

}