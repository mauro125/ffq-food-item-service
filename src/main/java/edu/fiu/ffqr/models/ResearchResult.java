package edu.fiu.ffqr.models;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="research_results")
public class ResearchResult {
	
	@Id
	private ObjectId id;

	@JsonProperty("questionnaireId")
	private String questionnaireId; 
	
	@JsonProperty("participantUserId")
	private String participantUserId;

	@JsonProperty("participantName")
	private String participantName;	
	
	@JsonProperty("ageInMonths")
	private int ageInMonths;
	
	@JsonProperty("gender")
	private String gender;
        
        @JsonProperty("creationDate")
	private String creationDate;

	@JsonProperty("userChoices")
	ArrayList<FoodItemInput> userChoices;
	
	@JsonProperty("weeklyTotals")
	Map<String, Double> weeklyTotals = new HashMap<String, Double>();
	
	@JsonProperty("dailyAverages")
	Map<String, Double> dailyAverages = new HashMap<String, Double>();
        
        public ResearchResult(){};

	public ResearchResult(String questionnaireId, String participantUserId, int ageInMonths, ArrayList<FoodItemInput> userChoices, 
					Map<String, Double> weeklyTotals, Map<String, Double> dailyAverages, 
                                        String feedback, String gender, String creationDate){
		
		this.questionnaireId = questionnaireId;
		this.participantUserId = participantUserId;
		this.participantName = "pending";
		this.ageInMonths = ageInMonths;		
		this.userChoices = userChoices;
		this.weeklyTotals = weeklyTotals;
		this.dailyAverages = dailyAverages;		
		this.gender = gender;
                this.creationDate = creationDate;
	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(String questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String getUserId() {
		return participantUserId;
	}

	public void setUserId(String participantUserId) {
		this.participantUserId = participantUserId;
	}

	public String getPatientName() {
		return participantName;
	}

	public void setPatientName(String patientName) {
		this.participantName = patientName;
	}

	public int getAgeInMonths() {
		return ageInMonths;
	}

	public void setAgeInMonths(int ageInMonths) {
		this.ageInMonths = ageInMonths;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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
        
        public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreationDate() {
		return creationDate;
	}
}
