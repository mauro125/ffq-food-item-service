package edu.fiu.ffqr.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.fiu.ffqr.models.NutrientRecommendation;
import edu.fiu.ffqr.models.Recommendation;
import edu.fiu.ffqr.models.Result;
import edu.fiu.ffqr.models.SysNutrientRecommendation;
import edu.fiu.ffqr.service.ResultsService;
import edu.fiu.ffqr.service.SysNutRecomService;

/*
 * Author: Dariana Gonzalez
 * Created: 10/2019
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/nutrientsrecommendations")
public class SysNutRecomController {

	@Autowired
	private SysNutRecomService SysNutRecomService;
	
	@Autowired
	private ResultsService resultsService;
	
	public SysNutRecomController() {}
	
	// return all the system nutrient recommendations
	@GetMapping("/all")
	public List<SysNutrientRecommendation> getAllResults() throws JsonProcessingException {
		List<SysNutrientRecommendation> SysNutrientsRecommendations = SysNutRecomService.getAll();
		return SysNutrientsRecommendations;		
	}
	
	@GetMapping("/calculateNutrientsRecommendations/{questionnaireID}")
	  public NutrientRecommendation calculateNutrientsRecommendations(@PathVariable("questionnaireID") String questionnaireID) throws Exception {
		
		String ageRange = "";
		String status = "";
		
		Result result = resultsService.getResultByQuestionnaireID(questionnaireID);
		int patientAge = result.getAgeInMonths();
		String patientName = result.getPatientName();
		Map<String, Double> map = result.getDailyAverages();
		
		// get list of nutrients recommendations by age
		List<SysNutrientRecommendation> SysNutrientsRecommendations = SysNutRecomService.getAll();
		
		if(patientAge >= 0 && patientAge <= 6)
		{
			ageRange = "0-6";
		}
		else if(patientAge >= 7 && patientAge <= 12)
		{
			ageRange = "7-12";
		}
		else if(patientAge >= 13 && patientAge <= 24)
		{
			ageRange = "13-24";
		}
		else
			throw new Exception("There are no recommendations available for infants of age over 24 months");	
		
		NutrientRecommendation nRecommendation = new NutrientRecommendation();		
		nRecommendation.setPatientAgeInMonths(patientAge);
		nRecommendation.setPatientName(patientName);
		nRecommendation.setQuestionnaireId(questionnaireID);

		for (SysNutrientRecommendation n: SysNutrientsRecommendations) {
			
			double calculatedValue = map.get(n.getNutrientName());
			double recommendedValue = n.getEstimatedAverageByAge().get(ageRange);
			
			if(calculatedValue < recommendedValue) {
				status = "Below";
			}
			else if(calculatedValue > recommendedValue) {
				status = "Above";
			}
			else
				status = "Normal";
			
			Recommendation recommedation = new Recommendation();
			recommedation.setCalculatedAmount(calculatedValue);
			recommedation.setRecommendedAmount(recommendedValue);
			recommedation.setNutrientName(n.getNutrientName());
			recommedation.setStatus(status);
			
			nRecommendation.getRecommendationsList().add(recommedation);
		}
		
		return nRecommendation;
	}
	
	@PostMapping("/create")
	public SysNutrientRecommendation create(@RequestBody SysNutrientRecommendation nr) throws JsonProcessingException {
		
		if(SysNutRecomService.getSysNutrientRecommendationByNutrientName(nr.getNutrientName()) != null)
		{
			throw new IllegalArgumentException("System Nutrient Recommendation for nutrient " + nr.getNutrientName() + " already exists.");			
		}
		else
			return SysNutRecomService.create(nr);
	}
}
