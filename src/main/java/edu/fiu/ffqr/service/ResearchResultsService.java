/*
 * Author: Dariana Gonzalez
 */
package edu.fiu.ffqr.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import edu.fiu.ffqr.models.ResearchResult;
import edu.fiu.ffqr.repositories.FFQResearchResultRepository;

@Service
@Component
public class ResearchResultsService {

	@Autowired
	private FFQResearchResultRepository responsesRepository;
	
	public List<ResearchResult> getAll() {
		return responsesRepository.findAll();
	}

	public List<ResearchResult> getResultsByUserId(String userId) {
		return responsesRepository.findByUserId(userId);
	}
	
	public ResearchResult create(ResearchResult results) {		
		return responsesRepository.save(results);
	}
	
    public ResearchResult getResultByQuestionnaireID(String questionnaireID) {
    	return responsesRepository.findByQuestionnaireId(questionnaireID);
    }

	public ResearchResult update(ResearchResult updatedItem) {
		return responsesRepository.save(updatedItem);
	}
}
