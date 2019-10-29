package edu.fiu.ffqr.models;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.bson.types.ObjectId;
import org.junit.Ignore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="results")
public class Result {
	
	@Id
	private ObjectId id;
	
	@JsonProperty("patientName")
	private String patientName;
	
	@JsonProperty("ageInMonths")
	private int ageInMonths;

	@JsonProperty("questionnaireId")
	private String questionnaireId; 
	
	@JsonProperty("userChoices")
	ArrayList<FoodItemInput> userChoices;
	
	@JsonProperty("weeklyTotals")
	Map<String, Double> weeklyTotals = new HashMap<String, Double>();
	
	@JsonProperty("dailyAverages")
	Map<String, Double> dailyAverages = new HashMap<String, Double>();
	
	public Result(String questionnaireId, int ageInMonths, ArrayList<FoodItemInput> userChoices, Map<String, Double> weeklyTotals, Map<String, Double> dailyAverages){
		
		this.patientName = "pending";
		this.ageInMonths = ageInMonths;
		this.questionnaireId = questionnaireId;
		this.userChoices = userChoices;
		this.weeklyTotals = weeklyTotals;
		this.dailyAverages = dailyAverages;
	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public int getAgeInMonths() {
		return ageInMonths;
	}

	public void setAgeInMonths(int ageInMonths) {
		this.ageInMonths = ageInMonths;
	}

	public String getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(String questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public ArrayList<FoodItemInput> getUserChoices() {
		return userChoices;
	}

	public void setUserChoices(ArrayList<FoodItemInput> userChoices) {
		this.userChoices = userChoices;
	}

	public Map<String, Double> getWeeklyTotals() {
		return weeklyTotals;
	}

	public void setWeeklyTotals(Map<String, Double> weeklyTotals) {
		this.weeklyTotals = weeklyTotals;
	}

	public Map<String, Double> getDailyAverages() {
		return dailyAverages;
	}

	public void setDailyAverages(Map<String, Double> dailyAverages) {
		this.dailyAverages = dailyAverages;
	}
}
