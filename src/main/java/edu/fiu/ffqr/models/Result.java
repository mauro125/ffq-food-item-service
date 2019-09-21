package edu.fiu.ffqr.models;
import java.util.HashMap;
import java.util.Map;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="results")
public class Result {
	
	@Id
	private ObjectId id;

	@JsonProperty("questionnaireId")
	private String questionnaireId; 
	
	@JsonProperty("weeklyTotals")
	Map<String, Double> weeklyTotals = new HashMap<String, Double>();
	
	@JsonProperty("dailyAverages")
	Map<String, Double> dailyAverages = new HashMap<String, Double>();
	
	public Result(String questionnaireId, Map<String, Double> weeklyTotals, Map<String, Double> dailyAverages){
		
		this.questionnaireId = questionnaireId;
		this.weeklyTotals = weeklyTotals;
		this.dailyAverages = dailyAverages;
	}
	
	public void setquestionnaireId(String questionnaireId){
		this.questionnaireId = questionnaireId;
	}
	
	public String getQuestionnaireId(){
		return this.questionnaireId;
	}

	public Map<String, Double> getWeeklyTotals(){
		return weeklyTotals;
	}

	public void setWeeklyTotals(Map<String, Double> weeklyTotals){
		this.weeklyTotals = weeklyTotals;
	}

	public Map<String, Double> getDailyAverages(){
		return dailyAverages;
	}

	public void setDailyAverages(Map<String, Double> dailyAverages){
		this.dailyAverages = dailyAverages;
	}
	
}
