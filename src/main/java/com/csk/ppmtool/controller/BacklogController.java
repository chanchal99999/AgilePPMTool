package com.csk.ppmtool.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csk.ppmtool.domain.ProjectTask;
import com.csk.ppmtool.service.MapValidationErrorsService;
import com.csk.ppmtool.service.ProjectTaskService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

	@Autowired
	private ProjectTaskService projectTaskService;

	@Autowired
	MapValidationErrorsService mapValidationErrorsService;
	
	@PostMapping("/{backlogId}")
	public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,
			BindingResult result,@PathVariable String backlogId) {
		ResponseEntity<?> errorMap=mapValidationErrorsService.MapValidationErrorsService(result);
		if(errorMap!=null)return errorMap;
		ProjectTask projectTask1=projectTaskService.addProjectTask(backlogId, projectTask);
 		return new ResponseEntity<ProjectTask>(projectTask1,HttpStatus.CREATED);
	}
}
