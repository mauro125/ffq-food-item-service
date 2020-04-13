package edu.fiu.ffqr.models;
import java.util.ArrayList;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="tracker")
public class TrackerResult {
	
	@Id
	private ObjectId id;
	
	@JsonProperty("userId")
	private String userId;

	@JsonProperty("age")
	private int age;
	
	@JsonProperty("date")
	private String date;
	
	@JsonProperty("responses")
	ArrayList<TrackerItem> responses;

	public TrackerResult(String userId, int age, String date, ArrayList<TrackerItem> responses){
		this.userId = userId;
		this.age = age;
		this.date = date;
		this.responses = responses;
	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public ArrayList<TrackerItem> getResponses() {
		return responses;
	}

	public void setResponses(ArrayList<TrackerItem> responses) {
		this.responses = responses;
	}
}
