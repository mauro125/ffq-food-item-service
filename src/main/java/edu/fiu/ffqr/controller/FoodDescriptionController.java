package edu.fiu.ffqr.controller;

import java.util.List;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.fiu.ffqr.models.FoodDescription;

import edu.fiu.ffqr.service.ResultsService;
import edu.fiu.ffqr.models.Result;
import edu.fiu.ffqr.service.FFQFoodDescriptionService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/fooddescription")
public class FoodDescriptionController {

    @Autowired
    private FFQFoodDescriptionService foodDescriptionService;

    @Autowired
    private ResultsService resultsService;

    public FoodDescriptionController() {}

    @GetMapping("/all")
    public List<FoodDescription> getAllFoodDescriptions() throws JsonProcessingException
    {
        return foodDescriptionService.getAll();
    }

    @GetMapping("/{questionnaireID}")
    public FoodDescription foodGroupDescription(@PathVariable("questionnaireID") String questionnaireID) throws Exception
     {
         int infantAge = 0;

         FoodDescription foodItemDescription = new FoodDescription();


         // get results for given questionnaire
         Result result = resultsService.getResultByQuestionnaireID(questionnaireID);

         infantAge = result.getAgeInMonths();

         foodItemDescription.getImageUrl();

         if(infantAge < 6)
         {
             foodDescriptionService.findByDailyFoodIntake(foodItemDescription.getDailyFoodIntake());
         }
         foodItemDescription.getFoodItemGroupName();
         foodItemDescription.getDailyFoodIntake();
         foodItemDescription.getDescription();

         return foodItemDescription;
    }

    @GetMapping("/{foodItemGroupName}")
    public FoodDescription collectionByFoodItemGroupName(@PathVariable("foodItemGroupName") String foodItemGroupName)
    {
        return foodDescriptionService.getCollectionByFoodItemGroupName(foodItemGroupName);
    }

    @PostMapping("/create")
	public FoodDescription createFoodDescription(@RequestBody FoodDescription foodDescription) throws JsonProcessingException {
		
		if(foodDescriptionService.findByDailyFoodIntake(foodDescription.getFoodItemGroupName()) != null)
		{
			throw new IllegalArgumentException("Food Item Description " + foodDescription.getFoodItemGroupName() + " already exists.");			
		}
		else
			return foodDescriptionService.create(foodDescription);
    }

}