package edu.fiu.ffqr.calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.fiu.ffqr.models.Result;
import edu.fiu.ffqr.models.FoodItemInput;
import edu.fiu.ffqr.models.NutrientList;
import edu.fiu.ffqr.models.ValidNutrientList;
import edu.fiu.ffqr.service.NutrientListService;

public class FFQCalculator {
	
	public static Result calculateTotals(String questionnaireId, int ageInMonths, ArrayList<FoodItemInput> userChoices, NutrientListService nlService) {
		
		//get list of valid nutrients
		String[] nutrients = ValidNutrientList.validNutrients;

		Map<String, Double> weeklyTotals = new HashMap<String, Double>();
		Map<String, Double> dailyAverages = new HashMap<String, Double>();

		if (userChoices.size() == 0) {
			for (int i = 0; i < nutrients.length; i++) {
				weeklyTotals.put(nutrients[i], 0.0);
				dailyAverages.put(nutrients[i], 0.0);
			}
			return new Result(questionnaireId, ageInMonths, userChoices, weeklyTotals, dailyAverages);
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
					
					double finalDailyValue = dailyAverages.getOrDefault(nutrients[i], 0.0) + additionalIntake;
					double finalWeeklyValue = weeklyTotals.getOrDefault(nutrients[i], 0.0) + additionalIntake * 7;

					if(selectedFoodType.getNutrientListID().equalsIgnoreCase("brea"))
					{
						if(ageInMonths == 1)
						{
							finalDailyValue = finalDailyValue * 699;
							finalWeeklyValue = finalWeeklyValue * 699;
						}
						else if(ageInMonths == 2)
						{
							finalDailyValue = finalDailyValue * 731;
							finalWeeklyValue = finalWeeklyValue * 731;
						}
						else if(ageInMonths == 3)
						{
							finalDailyValue = finalDailyValue * 751;
							finalWeeklyValue = finalWeeklyValue * 751;
						}
						else if(ageInMonths == 4)
						{
							finalDailyValue = finalDailyValue * 780;
							finalWeeklyValue = finalWeeklyValue * 780;
						}
						else if(ageInMonths == 5)
						{
							finalDailyValue = finalDailyValue * 796;
							finalWeeklyValue = finalWeeklyValue * 796;
						}
						else if(ageInMonths == 6)
						{
							finalDailyValue = finalDailyValue * 854;
							finalWeeklyValue = finalWeeklyValue * 854;
						}
						else if(ageInMonths == 7)
						{
							finalDailyValue = finalDailyValue * 867;
							finalWeeklyValue = finalWeeklyValue * 867;
						}
						else if(ageInMonths == 8)
						{
							finalDailyValue = finalDailyValue * 815;
							finalWeeklyValue = finalWeeklyValue * 815;
						}
						else if(ageInMonths == 9)
						{
							finalDailyValue = finalDailyValue * 890;
							finalWeeklyValue = finalWeeklyValue * 890;
						}
						else if(ageInMonths == 10)
						{
							finalDailyValue = finalDailyValue * 900;
							finalWeeklyValue = finalWeeklyValue * 900;
						}
						else if(ageInMonths == 11)
						{
							finalDailyValue = finalDailyValue * 910;
							finalWeeklyValue = finalWeeklyValue * 910;
						}
						else if(ageInMonths == 12)
						{
							finalDailyValue = finalDailyValue * 900;
							finalWeeklyValue = finalWeeklyValue * 900;
						}
						else if(ageInMonths >= 13 && ageInMonths <= 24)
						{
							finalDailyValue = finalDailyValue * 500;
							finalWeeklyValue = finalWeeklyValue * 500;
						}						 
					}
					dailyAverages.put(nutrients[i], finalDailyValue);
					weeklyTotals.put(nutrients[i], finalWeeklyValue);
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

					double finalWeeklyValue = weeklyTotals.getOrDefault(nutrients[i], 0.0) + additionalIntake;
					double finalDailyValue = dailyAverages.getOrDefault(nutrients[i], 0.0) + additionalIntake / 7;

					if(selectedFoodType.getNutrientListID().equalsIgnoreCase("brea"))
					{
						if(ageInMonths == 1)
						{
							finalDailyValue = finalDailyValue * 699;
							finalWeeklyValue = finalWeeklyValue * 699;
						}
						else if(ageInMonths == 2)
						{
							finalDailyValue = finalDailyValue * 731;
							finalWeeklyValue = finalWeeklyValue * 731;
						}
						else if(ageInMonths == 3)
						{
							finalDailyValue = finalDailyValue * 751;
							finalWeeklyValue = finalWeeklyValue * 751;
						}
						else if(ageInMonths == 4)
						{
							finalDailyValue = finalDailyValue * 780;
							finalWeeklyValue = finalWeeklyValue * 780;
						}
						else if(ageInMonths == 5)
						{
							finalDailyValue = finalDailyValue * 796;
							finalWeeklyValue = finalWeeklyValue * 796;
						}
						else if(ageInMonths == 6)
						{
							finalDailyValue = finalDailyValue * 854;
							finalWeeklyValue = finalWeeklyValue * 854;
						}
						else if(ageInMonths == 7)
						{
							finalDailyValue = finalDailyValue * 867;
							finalWeeklyValue = finalWeeklyValue * 867;
						}
						else if(ageInMonths == 8)
						{
							finalDailyValue = finalDailyValue * 815;
							finalWeeklyValue = finalWeeklyValue * 815;
						}
						else if(ageInMonths == 9)
						{
							finalDailyValue = finalDailyValue * 890;
							finalWeeklyValue = finalWeeklyValue * 890;
						}
						else if(ageInMonths == 10)
						{
							finalDailyValue = finalDailyValue * 900;
							finalWeeklyValue = finalWeeklyValue * 900;
						}
						else if(ageInMonths == 11)
						{
							finalDailyValue = finalDailyValue * 910;
							finalWeeklyValue = finalWeeklyValue * 910;
						}
						else if(ageInMonths == 12)
						{
							finalDailyValue = finalDailyValue * 900;
							finalWeeklyValue = finalWeeklyValue * 900;
						}
						else if(ageInMonths >= 13 && ageInMonths <= 24)
						{
							finalDailyValue = finalDailyValue * 500;
							finalWeeklyValue = finalWeeklyValue * 500;
						}
					}
					dailyAverages.put(nutrients[i], finalDailyValue);
					weeklyTotals.put(nutrients[i], finalWeeklyValue);
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
				
		Result results = new Result(questionnaireId, ageInMonths, userChoices, weeklyTotals, dailyAverages);
		return results;
		
	}
			
}
