package edu.fiu.ffqr.calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

import edu.fiu.ffqr.models.Result;
import edu.fiu.ffqr.models.FoodItemInput;
import edu.fiu.ffqr.models.NutrientList;
import edu.fiu.ffqr.models.QuestionnaireResultNutrientsList;
import edu.fiu.ffqr.models.ValidNutrientList;
import edu.fiu.ffqr.service.NutrientListService;

public class FFQCalculator {

	// Recommended Volume of milk for infants of differing age in months
	// (exclusively in ml or grams)
	static double oneMonthInfantBreastMilkVolume = 699.0;
	static double twoMonthInfantBreastMilkVolume = 731.0;
	static double threeMonthInfantBreastMilkVolume = 751.0;
	static double fourMonthInfantBreastMilkVolume = 780.0;
	static double fiveMonthInfantBreastMilkVolume = 796.0;
	static double sixMonthInfantBreastMilkVolume = 854.0;
	static double sevenMonthInfantBreastMilkVolume = 867.0;
	static double eightMonthInfantBreastMilkVolume = 815.0;
	static double nineMonthInfantBreastMilkVolume = 890.0;
	static double tenMonthInfantBreastMilkVolume = 900.0;
	static double elevenMonthInfantBreastMilkVolume = 910.0;
	static double twelveMonthInfantBreastMilkVolume = 900.0;
	static double thirteenThroughTwentyFourMonthInfantBreastMilkVolume = 500.0;
	static final double ouncesToMilliliter = 29.5735;

	//Khalid Alamoudi - Added total calories to the parameters and return
	public static Result calculateTotals(String questionnaireId, int ageInMonths, ArrayList<FoodItemInput> userChoices,
			NutrientListService nlService) {

		// get list of valid nutrients
		
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

		double amountOfServings = 0.0;
		double servingsInMilliliters = 0.0;
		double remainingMilliters = 0.0;

		boolean isFormulaAndBreastMilkConsumed = false;
		boolean isBreastMilkChosen = false;
		boolean isFormulaChosen = false;

		//Check for consumption of breastmilk and formula together
		for (FoodItemInput foodItem: userChoices)
		{
			if(foodItem.getNutrientListID() == "form")
			{
				isFormulaChosen = true;
			}
			if(foodItem.getNutrientListID() == "brea")
			{
				isBreastMilkChosen = true;
			}
		}

		if(isFormulaChosen && isBreastMilkChosen)
		{
			isFormulaAndBreastMilkConsumed = true;
		}
		

		//for each food item that the user selected 
		for (FoodItemInput foodItem: userChoices) {
			
			//find record with that nutrientListID
			NutrientList selectedFoodType = nlService.getWithNutrientListID(foodItem.getNutrientListID());

			//get amount of servings for item
			if(foodItem.getServing() == null || foodItem.getServing().isEmpty())
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
					{
						additionalIntake = nutrientValuePerServing;
					}
					else
					{
						additionalIntake = amountOfServings * foodItem.getFrequency() * nutrientValuePerServing;
					}
					
					double finalDailyValue = dailyAverages.getOrDefault(nutrients[i], 0.0) + additionalIntake;

					if(selectedFoodType.getNutrientListID().equalsIgnoreCase("brea") && !isFormulaAndBreastMilkConsumed)
					{
						finalDailyValue = calculateBreastMilk(ageInMonths, finalDailyValue);
					}
					else if(selectedFoodType.getNutrientListID().equalsIgnoreCase("brea") && isFormulaAndBreastMilkConsumed)
					{
						finalDailyValue = calculateFormulaAndBreastMilk(ageInMonths, amountOfServings, servingsInMilliliters, remainingMilliters);
					}
					dailyAverages.put(nutrients[i], finalDailyValue);
					weeklyTotals.put(nutrients[i], finalDailyValue * 7.00);
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
					{
						additionalIntake = nutrientValuePerServing;
					}
					else
					{
						additionalIntake = amountOfServings * foodItem.getFrequency() * nutrientValuePerServing;
					}

					double finalDailyValue = dailyAverages.getOrDefault(nutrients[i], 0.0) + additionalIntake / 7.00;

					if(selectedFoodType.getNutrientListID().equalsIgnoreCase("brea"))
					{
						if(foodItem.getFrequency() > 6)
						{
							if(!isFormulaAndBreastMilkConsumed)
							{
								finalDailyValue = calculateBreastMilk(ageInMonths, finalDailyValue);
							}
							else if(isFormulaAndBreastMilkConsumed)
							{
								finalDailyValue = calculateFormulaAndBreastMilk(ageInMonths, amountOfServings, servingsInMilliliters, remainingMilliters);
							}
						}
						else
						{
							finalDailyValue = 0.0;
						}
					}
					dailyAverages.put(nutrients[i], finalDailyValue);
					weeklyTotals.put(nutrients[i], finalDailyValue * 7.00);		
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
			
		//Khalid Alamoudi - Change below to return the modified versions of dailyAverages and weeklyTotals
		//===============================================================
		//--Calculating total calories
		double totalCalories = weeklyTotals.get("% Calories from Fat") + weeklyTotals.get("% Calories from Protein") +
		weeklyTotals.get("% Calories from Carbs");
		weeklyTotals.put("Total Calories (g)", totalCalories);
		dailyAverages.put("Total Calories (g)", weeklyTotals.get("Total Calories (g)"));

		//---- modifying weeklyTotals and dailyAverages
		Map<String, Double> modifiedDailyAverages = modifiedMap(dailyAverages);
		Map<String, Double> modifiedWeeklyTotals = modifiedMap(weeklyTotals);
		Map<String, Double> modDailyAverages = new HashMap<String, Double>();
		Map<String, Double> modWeeklyTotals = new HashMap<String, Double>();

		for (String key: modifiedWeeklyTotals.keySet()) {
			if(QuestionnaireResultNutrientsList.getNutrientList().contains(key)){
				modDailyAverages.put(key, modifiedDailyAverages.get(key));
				modWeeklyTotals.put(key, modifiedWeeklyTotals.get(key));
			}
		}

		Result results = new Result(questionnaireId, ageInMonths, userChoices, modWeeklyTotals, modDailyAverages);
		//End of added code
		//===============================================================
		return results;
	}
		
	//Khalid Alamoudi - Added functions to create modified version of any map into the required 3 digit max criteria
	// set by the PO
	//================================================================================
	public static Map<String, Double> modifiedMap(Map<String, Double> mapInput){

		Map<String, Double> newMap = new HashMap<>();
		for(String key: mapInput.keySet()){
			double value = mapInput.get(key);
			newMap.put(key, threeDigitView(value));
		}
		return newMap;
	}

	public static double threeDigitView(double value){
		double newValue = value;
		if(value >= 100){
			newValue = (double)((int)value);
		}  
		else if((value >= 10)&&(value < 100)){
			newValue = ((double)((int)(value*10)))/10.0;
		}
		else{
			newValue = ((double)((int)(value*100)))/100.0;
		}
		return newValue;
	}
	//End of added code
	//===================================================================================

	private static double calculateBreastMilk(int ageInMonths, double finalDailyValue)
	{
		if(ageInMonths == 1)
		{
			finalDailyValue *= oneMonthInfantBreastMilkVolume;
		}
		else if(ageInMonths == 2)
		{
			finalDailyValue *= twoMonthInfantBreastMilkVolume;
		}
		else if(ageInMonths == 3)
		{
			finalDailyValue *= threeMonthInfantBreastMilkVolume;
		}
		else if(ageInMonths == 4)
		{
			finalDailyValue *= fourMonthInfantBreastMilkVolume;
		}
		else if(ageInMonths == 5)
		{
			finalDailyValue *= fiveMonthInfantBreastMilkVolume;
		}
		else if(ageInMonths == 6)
		{
			finalDailyValue *= sixMonthInfantBreastMilkVolume;
		}
		else if(ageInMonths == 7)
		{
			finalDailyValue *= sevenMonthInfantBreastMilkVolume;
		}
		else if(ageInMonths == 8)
		{
			finalDailyValue *= eightMonthInfantBreastMilkVolume;
		}
		else if(ageInMonths == 9)
		{
			finalDailyValue *= nineMonthInfantBreastMilkVolume;
		}
		else if(ageInMonths == 10)
		{
			finalDailyValue *= tenMonthInfantBreastMilkVolume;
		}
		else if(ageInMonths == 11)
		{
			finalDailyValue *= elevenMonthInfantBreastMilkVolume;
		}
		else if(ageInMonths == 12)
		{
			finalDailyValue *= twelveMonthInfantBreastMilkVolume;
		}
		else if(ageInMonths >= 13 && ageInMonths <= 24)
		{
			finalDailyValue *= thirteenThroughTwentyFourMonthInfantBreastMilkVolume;
		}

		return finalDailyValue;
	}

	private static double calculateFormulaAndBreastMilk(int ageInMonths, double amountOfServings,
	double servingsInMilliliters, double remainingMilliters)
	{
		servingsInMilliliters = amountOfServings * ouncesToMilliliter;

		if(ageInMonths == 1)
		{
			remainingMilliters = oneMonthInfantBreastMilkVolume - servingsInMilliliters;
		}
		else if(ageInMonths == 2)
		{
			remainingMilliters = twoMonthInfantBreastMilkVolume - servingsInMilliliters;
		}
		else if(ageInMonths == 3)
		{
			remainingMilliters = threeMonthInfantBreastMilkVolume - servingsInMilliliters;
		}
		else if(ageInMonths == 4)
		{
			remainingMilliters = fourMonthInfantBreastMilkVolume - servingsInMilliliters;
		}
		else if(ageInMonths == 5)
		{
			remainingMilliters = fiveMonthInfantBreastMilkVolume - servingsInMilliliters;
		}
		else if(ageInMonths == 6)
		{
			remainingMilliters = sixMonthInfantBreastMilkVolume - servingsInMilliliters;
		}
		else if(ageInMonths == 7)
		{
			remainingMilliters = sevenMonthInfantBreastMilkVolume - servingsInMilliliters;
		}
		else if(ageInMonths == 8)
		{
			remainingMilliters = eightMonthInfantBreastMilkVolume - servingsInMilliliters;
		}
		else if(ageInMonths == 9)
		{
			remainingMilliters = nineMonthInfantBreastMilkVolume - servingsInMilliliters;
		}
		else if(ageInMonths == 10)
		{
			remainingMilliters = tenMonthInfantBreastMilkVolume - servingsInMilliliters;
		}
		else if(ageInMonths == 11)
		{
			remainingMilliters = elevenMonthInfantBreastMilkVolume - servingsInMilliliters;
		}
		else if(ageInMonths == 12)
		{
			remainingMilliters = twelveMonthInfantBreastMilkVolume - servingsInMilliliters;
		}
		else if(ageInMonths >= 13 && ageInMonths <= 24)
		{
			remainingMilliters = thirteenThroughTwentyFourMonthInfantBreastMilkVolume - servingsInMilliliters;
		}

		if(remainingMilliters > 0)
		{
			return remainingMilliters;
		}

		return 0.0;
	}
}