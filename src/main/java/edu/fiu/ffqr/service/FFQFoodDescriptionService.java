package edu.fiu.ffqr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import edu.fiu.ffqr.models.FoodDescription;
import edu.fiu.ffqr.models.FoodItem;
import edu.fiu.ffqr.models.FoodType;
import edu.fiu.ffqr.models.ServingOptions;
import edu.fiu.ffqr.models.SugarSetting;
import edu.fiu.ffqr.repositories.FFQFoodDescriptionRepository;
import edu.fiu.ffqr.repositories.FFQFoodEntryRepository;;

@Service
@Component
public class FFQFoodDescriptionService {

    @Autowired
    private FFQFoodDescriptionRepository foodDescriptionRepository;

    public List<FoodDescription> getAll()	{
        return foodDescriptionRepository.findAll();
    }

    public FoodDescription create(FoodDescription foodDescription) {
        return foodDescriptionRepository.save(foodDescription);
    }

    public FoodDescription findByFoodItemGroupName(String foodItemGroupName) {
		    return foodDescriptionRepository.findByFoodItemGroupName(foodItemGroupName);
    }
}