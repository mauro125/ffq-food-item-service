package edu.fiu.ffqr.dataloader;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.fiu.ffqr.controller.FoodItemController;
import edu.fiu.ffqr.controller.SysNutRecomController;
import edu.fiu.ffqr.models.FoodItem;
import edu.fiu.ffqr.models.SysNutrientRecommendation;
import edu.fiu.ffqr.repositories.FFQFSysNutRecomRepository;
import edu.fiu.ffqr.repositories.FFQFoodEntryRepository;
import edu.fiu.ffqr.repositories.NutrientListRepository;
import edu.fiu.ffqr.service.NutrientListService;


@Component
public class DataLoader {
	
	private FFQFoodEntryRepository foodRepository;
	
	private NutrientListRepository nutrientRepository;
	
	private FFQFSysNutRecomRepository sysNutRecomRepository;
	
	private NutrientListService nutrientService;
	
	private FoodItemController foodController;
	
	private SysNutRecomController sysNutRecomController;
	
	public DataLoader(FFQFoodEntryRepository foodRepository, 
			NutrientListRepository nutrientRepository, NutrientListService nutrientService, FoodItemController foodController, 
			SysNutRecomController sysNutRecomController, FFQFSysNutRecomRepository sysNutRecomRepository) {
		this.foodRepository = foodRepository;
		this.nutrientRepository = nutrientRepository;
		this.nutrientService = nutrientService;
		this.foodController = foodController;
		this.sysNutRecomController = sysNutRecomController;
		this.sysNutRecomRepository = sysNutRecomRepository;
	}
	
	// Added by Dariana Gonzalez 10/2019
	public void loadSysNutrientsRecommendations() {
		
		System.out.println("<------- Loading System Nutrients Recommendations... (only main 19) ------->");
		
		this.sysNutRecomRepository.deleteAll();
		
		try {
		
			String resourceName = "SysNutrientsRecommendationsPayload.json";		
		
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream(resourceName);
			JSONParser jsonParser = new JSONParser();		
			JSONArray jsonArray = (JSONArray) jsonParser
				.parse(new InputStreamReader(inputStream));
			ObjectMapper mapper = new ObjectMapper();
			List<SysNutrientRecommendation> sysNutRecomList = new ArrayList<>();
		
			for (Object object : jsonArray) {
				JSONObject jsonObject = (JSONObject) object;
				SysNutrientRecommendation item = mapper.readValue(jsonObject.toString(), SysNutrientRecommendation.class);
				sysNutRecomList.add(item);
			}
			for(SysNutrientRecommendation item : sysNutRecomList) {
				System.out.println(item.getNutrientName() + "---- Loaded!");
				this.sysNutRecomController.create(item);
			}
		} catch (Exception e) {
			System.err.println("An error occurred while loading System Nutrients Recommendations: ");
			e.printStackTrace();
		}		
	}
	
	public void load() {
		System.out.println("Loading fooditems...");
		try {
			String resourceName = "FoodItemPayload.json";
			
			this.foodRepository.deleteAll();
			this.nutrientRepository.deleteAll();
			ExcelDataLoad loader = new ExcelDataLoad(this.nutrientService);
			loader.dataLoad();
			
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream(resourceName);
			JSONParser jsonParser = new JSONParser();		
			JSONArray jsonArray = (JSONArray) jsonParser
					.parse(new InputStreamReader(inputStream));
			ObjectMapper mapper = new ObjectMapper();
			List<FoodItem> foodList = new ArrayList<>();
			
			for (Object object : jsonArray) {
				JSONObject jsonObject = (JSONObject) object;
				FoodItem item = mapper.readValue(jsonObject.toString(), FoodItem.class);
				foodList.add(item);
			}
			for(FoodItem item : foodList) {
				this.foodController.create(item);
			}			
			System.out.println("A total of " + foodList.size() + " food items were added to food_items collection");
			
		} catch (Exception e) {
			System.err.println("An error occurred while loading food items: ");
			e.printStackTrace();
		}
			
		System.out.println("...Loading complete!");	
	}
	
	
}
