package edu.fiu.ffqr.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="fooditem_recommendations")
public class FoodRecommendation implements Serializable {
	
	@JsonProperty("questionnaireId")
	private String questionnaireId; 
	@JsonProperty("patientName")
	String patientName;
	@JsonProperty("patientAge")
	int patientAgeInMonths;	
	@JsonProperty("foodCategoryRecList")
	List <FoodCategoryRecommendation> foodCategoryRecList;
	
	public FoodRecommendation() {
		foodCategoryRecList = new ArrayList<>();
	}

	public String getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(String questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public int getPatientAgeInMonths() {
		return patientAgeInMonths;
	}

	public void setPatientAgeInMonths(int patientAgeInMonths) {
		this.patientAgeInMonths = patientAgeInMonths;
	}

	public List<FoodCategoryRecommendation> getFoodCategoryRecList() {
		return foodCategoryRecList;
	}

	public void setFoodCategoryRecList(List<FoodCategoryRecommendation> foodCategoryRecList) {
		this.foodCategoryRecList = foodCategoryRecList;
	}
}
