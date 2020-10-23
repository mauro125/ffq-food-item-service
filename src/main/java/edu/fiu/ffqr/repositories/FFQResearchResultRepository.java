package edu.fiu.ffqr.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import edu.fiu.ffqr.models.ResearchResult;

@Repository
public interface FFQResearchResultRepository extends MongoRepository<ResearchResult, String> {

	ResearchResult findByQuestionnaireId(String id);

	List<ResearchResult> findByUserId(String userId);
 
}
