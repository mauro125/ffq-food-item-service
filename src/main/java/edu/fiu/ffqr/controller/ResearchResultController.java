package edu.fiu.ffqr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.fiu.ffqr.models.ResearchResult;
import edu.fiu.ffqr.service.ResearchResultsService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/research_results")
public class ResearchResultController {
	
	@Autowired
	private ResearchResultsService resultsService;
	
	public ResearchResultController() {}

	@GetMapping("/all")
	public List<ResearchResult> getAllResults() throws JsonProcessingException {
		List<ResearchResult> results = resultsService.getAll();
		return results;
	}

	@GetMapping("/participant/{userID}")
	public List<ResearchResult> getResultsByUserId(@PathVariable("userID") String userId) throws JsonProcessingException {
		List<ResearchResult> results = resultsService.getResultsByUserId(userId);
		return results;
	}

	@PutMapping("/update")
	public ResearchResult update(@RequestBody ResearchResult data) {

		System.out.println(data.toString());

		String id = data.getQuestionnaireId();

		if (null == id) {
			throw new IllegalArgumentException("Missing questionnaireID");
		}

		ResearchResult questionnaireResult = resultsService.getResultByQuestionnaireID(id);
		if (null == questionnaireResult) {
			throw new IllegalArgumentException("Invalid questionnaireID");
		}
	

		resultsService.update(questionnaireResult);

		return questionnaireResult;
	}
        
       @PutMapping("/create")
	public ResearchResult create(@RequestBody ResearchResult data) {

            if(data != null)
            {
                System.out.println(data.toString());

		String id = data.getQuestionnaireId();

		if (null == id) {
			throw new IllegalArgumentException("Missing questionnaireID");
		}
		

		resultsService.create(data);

		
            }
            else
            {
                throw new IllegalArgumentException("Cannot create a null research result");
            }
		
            return data;
	}
}
