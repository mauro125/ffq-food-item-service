package edu.fiu.ffqr.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.fiu.ffqr.models.FoodItem;
import edu.fiu.ffqr.models.SysNutrientRecommendation;

@Repository
public interface FFQFSysNutRecomRepository extends MongoRepository<SysNutrientRecommendation, String> {

	SysNutrientRecommendation findBynutrientName(String name);
}
