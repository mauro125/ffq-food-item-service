package edu.fiu.ffqr.models;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;



public class QuestionnaireResultNutrientsList {
	final static int validNutrientsSize = 200;
	//global String[] validNutrients = {};
	//public static String[] validNutrients = new String[validNutrientsSize];
    
    public static String[] nutrientsList = {
        "Energy (kcal)",
        "Total Protein (g)",
        "CHO (g)",
        "Total Fat (g)",
        "% Calories from Protein",
        "% Calories from Carbs",
        "% Calories from Fat",
        "Animal Protein (g)",
        "Vegetable Protein (g)",
        "Total Dietary Fiber (g)",
        "Total Sugars (g)",
        "Added Sugars (by Total Sugars) (g)",
        "Fructose (g)",
        "Lactose (g)",
        "Starch (g)",
        "Total Grains (ounce equivalents)",
        "Whole Grains (ounce equivalents)",
        "Refined Grains (ounce equivalents)",
        "Total Saturated Fatty Acids (SFA) (g)",
        "Total Monounsaturated Fatty Acids (MUFA) (g)",
        "Total Polyunsaturated Fatty Acids (PUFA) (g)",
        "Omega-3 Fatty Acids (g)",
        "Water (g)",
        "Caffeine (mg)",
        "Thiamin (vitamin B1) (mg)",
        "Riboflavin (vitamin B2) (mg)",
        "Niacin (vitamin B3) (mg)",
        "Pantothenic Acid (vitamin B5) (mg)",
        "Vitamin B-6 (pyridoxine, pyridoxyl, & pyridoxamine) (mg)",
        "Total Folate (vitamin B9) (mcg)",
        "Vitamin B-12 (cobalamin) (mcg)",
        "Vitamin C (ascorbic acid) (mg)",
        "Total Vitamin A Activity (Retinol Activity Equivalents) (mcg)",
        "Vitamin D (calciferol) (mcg)",
        "Vitamin E (Total Alpha-Tocopherol) (mg)",
        "Vitamin K (phylloquinone) (mcg)",
        "Calcium (mg)",
        "Copper (mg)",
        "Iron (mg)",
        "Manganese (mg)",
        "Magnesium (mg)",
        "Phosphorus (mg)",
        "Potassium (mg)",
        "Selenium (mcg)",
        "Sodium (mg)",
        "Zinc (mg)"

    };

    public static ArrayList<String> getNutrientList(){
        ArrayList<String> nutrientArray = new ArrayList<String>();
    
        for(String nutrient: nutrientsList) {
           nutrientArray.add(nutrient);
        }

        return nutrientArray;
    }
   
    


		
}
