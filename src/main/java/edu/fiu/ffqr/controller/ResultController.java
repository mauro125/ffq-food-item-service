package edu.fiu.ffqr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.fiu.ffqr.models.Result;
import edu.fiu.ffqr.service.ResultsService;

/*
 * Author: Dariana Gonzalez
 * Created: 09/2019
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/results")
public class ResultController {
	
	@Autowired
	private ResultsService resultsService;
	
	public ResultController() {}

	@GetMapping("/all")
	public List<Result> getAllResults() throws JsonProcessingException {
		List<Result> results = resultsService.getAll();
		return results;
	}

	@GetMapping("/parent/{userID}")
	public List<Result> getResultsByParents(@PathVariable("userID") String userId) {
		List<Result> results = resultsService.getResultsByParentId(userId);
		return results;
	}
}
