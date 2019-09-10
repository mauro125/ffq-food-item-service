package edu.fiu.ffqr.calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.fiu.ffqr.models.FFQResult;
import edu.fiu.ffqr.models.FoodItemInput;
import edu.fiu.ffqr.models.NutrientList;
import edu.fiu.ffqr.models.ValidNutrientList;
import edu.fiu.ffqr.service.NutrientListService;

public class FFQCalculator {
	
	public static FFQResult calculateTotals(ArrayList<FoodItemInput> userChoices, NutrientListService nlService) {
		//get list of valid nutrients
		String[] nutrients = ValidNutrientList.validNutrients;

		Map<String, Double> weeklyTotals = new HashMap<String, Double>();
		Map<String, Double> dailyAverages = new HashMap<String, Double>();

		if (userChoices.size() == 0) {
			for (int i = 0; i < nutrients.length; i++) {
				weeklyTotals.put(nutrients[i], 0.0);
				dailyAverages.put(nutrients[i], 0.0);
			}
			return new FFQResult(weeklyTotals, dailyAverages);
		}
		
		NutrientList tbspSugar = nlService.getWithNutrientListID("suga");

		//for each food item that the user selected 
		for (FoodItemInput foodItem: userChoices) {
			
			//find record with that nutrientListID
			NutrientList selectedFoodType = nlService.getWithNutrientListID(foodItem.getNutrientListID());

			//get amount of servings for item
			double amountOfServings;
			if(foodItem.getServing() == null)
				amountOfServings = 1;
			else
				amountOfServings = Double.parseDouble(foodItem.getServing().split(" ")[0]);
			
			//if user selected daily frequency
			if (foodItem.getFrequencyType().equalsIgnoreCase("day")) {

				//if item has additional sugar, add corresponding values for sugar 1 tbsp.
				if (foodItem.getAdditionalSugar() != 0) {
					for (int i = 0; i < nutrients.length; i++) {
						double nutrientValuePerServing = tbspSugar.getNutrient(nutrients[i]); 
						dailyAverages.put(nutrients[i], dailyAverages.getOrDefault(nutrients[i], 0.0) + nutrientValuePerServing * foodItem.getAdditionalSugar() * foodItem.getFrequency());
						weeklyTotals.put(nutrients[i], weeklyTotals.getOrDefault(nutrients[i], 0.0) + 7 * nutrientValuePerServing * foodItem.getAdditionalSugar() * foodItem.getFrequency());
					}
				}

				//iterate nutrients and update daily average
				for (int i = 0; i < nutrients.length; i++) {
					double additionalIntake = 0.0;
					//additional intake = amount of servings * value of nutrient per serving
					double nutrientValuePerServing = selectedFoodType.getNutrient(nutrients[i]);
					
					if (selectedFoodType.getNutrientListID().equalsIgnoreCase("brea")) 
						additionalIntake = amountOfServings * foodItem.getFrequency() * 5 * nutrientValuePerServing;
					else
						additionalIntake = amountOfServings * foodItem.getFrequency() * nutrientValuePerServing;
						
					dailyAverages.put(nutrients[i], dailyAverages.getOrDefault(nutrients[i], 0.0) + additionalIntake);
				}

				//iterate nutrients and update weekly intake
				for (int i = 0; i < nutrients.length; i++) {
					double additionalIntake = 0.0;
					
					//additional intake = amount of servings * value of nutrient per serving
					double nutrientValuePerServing = selectedFoodType.getNutrient(nutrients[i]);
					
					if (selectedFoodType.getNutrientListID().equalsIgnoreCase("brea")) 
						additionalIntake = amountOfServings * foodItem.getFrequency() * 5 * nutrientValuePerServing;
					else
						additionalIntake = amountOfServings * foodItem.getFrequency() * nutrientValuePerServing;

					weeklyTotals.put(nutrients[i], weeklyTotals.getOrDefault(nutrients[i], 0.0) + additionalIntake * 7);
				}

			}

			//if user selected weekly frequency
			else if (foodItem.getFrequencyType().equalsIgnoreCase("week")) {

				//if item has additional sugar, add corresponding values for sugar 1 tbsp.
				if (foodItem.getAdditionalSugar() != 0) {
					for (int i = 0; i < nutrients.length; i++) {
						double nutrientValuePerServing = tbspSugar.getNutrient(nutrients[i]);
						weeklyTotals.put(nutrients[i], weeklyTotals.getOrDefault(nutrients[i], 0.0) + nutrientValuePerServing * foodItem.getAdditionalSugar() * foodItem.getFrequency());
						dailyAverages.put(nutrients[i], dailyAverages.getOrDefault(nutrients[i], 0.0) + (nutrientValuePerServing * foodItem.getAdditionalSugar() * foodItem.getFrequency()) / 7.0);
					}
				}
				
				//iterate nutrients and update weekly intake
				for (int i = 0; i < nutrients.length; i++) {
					double additionalIntake = 0.0;
					//additional intake = amount of servings * value of nutrient per serving
					double nutrientValuePerServing = selectedFoodType.getNutrient(nutrients[i]);
					
					if (selectedFoodType.getNutrientListID().equalsIgnoreCase("brea")) 
						additionalIntake = amountOfServings * foodItem.getFrequency() * 5 * nutrientValuePerServing;
					else
						additionalIntake = amountOfServings * foodItem.getFrequency() * nutrientValuePerServing;

					weeklyTotals.put(nutrients[i], weeklyTotals.getOrDefault(nutrients[i], 0.0) + additionalIntake);
				}

				//iterate nutrients and update daily average
				for (int i = 0; i < nutrients.length; i++) {
					double additionalIntake = 0.0;
					//additional intake = amount of servings * value of nutrient per serving
					double nutrientValuePerServing = selectedFoodType.getNutrient(nutrients[i]);
					
					if (selectedFoodType.getNutrientListID().equalsIgnoreCase("brea")) 
						additionalIntake = amountOfServings * foodItem.getFrequency() * 5 * nutrientValuePerServing;
					else
						additionalIntake = amountOfServings * foodItem.getFrequency() * nutrientValuePerServing;

					dailyAverages.put(nutrients[i], dailyAverages.getOrDefault(nutrients[i], 0.0) + additionalIntake / 7);
				}	
			
			}
			
			else {
				throw new IllegalArgumentException("Frequency type must be day or week");
			}
		}

		//calculate % calories from protein, fat, and carbs
		
		//% calories from fat
		double caloriesFromFatWeekly = weeklyTotals.get("Total Fat (g)") * 9;
		double percentageCalFromFatWeekly = caloriesFromFatWeekly * 100 / weeklyTotals.get("Energy (kcal)");
		weeklyTotals.put("% Calories from Fat", percentageCalFromFatWeekly);
		dailyAverages.put("% Calories from Fat", weeklyTotals.get("% Calories from Fat"));
		
		//% calories from protein
		double caloriesFromProteinWeekly = weeklyTotals.get("Total Protein (g)") * 4;
		double percentageCalFromProteinWeekly = caloriesFromProteinWeekly * 100 / weeklyTotals.get("Energy (kcal)");
		weeklyTotals.put("% Calories from Protein", percentageCalFromProteinWeekly);
		dailyAverages.put("% Calories from Protein", weeklyTotals.get("% Calories from Protein"));
				
		//% calories from carbs
		double caloriesFromCarbsWeekly = weeklyTotals.get("Total Carbohydrate (g)") * 4;
		double percentageCalFromCarbsWeekly = caloriesFromCarbsWeekly * 100 / weeklyTotals.get("Energy (kcal)");
		weeklyTotals.put("% Calories from Carbs", percentageCalFromCarbsWeekly);
		dailyAverages.put("% Calories from Carbs", weeklyTotals.get("% Calories from Carbs"));
				
		FFQResult results = new FFQResult(weeklyTotals, dailyAverages);
		return results;
		
	}
			
}
