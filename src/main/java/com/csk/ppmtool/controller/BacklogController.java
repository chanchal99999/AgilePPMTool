package com.csk.ppmtool.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
	@GetMapping("/{backlogId}")
	public Iterable<ProjectTask> getBacklog(@PathVariable String backlogId){
		
		return projectTaskService.findBacklogById(backlogId);
	}
	@GetMapping("/{backlogId}/{ptid}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlogId,@PathVariable String ptid){
		ProjectTask projectTask=projectTaskService.findPTByProjectSequence(backlogId,ptid);
		return new ResponseEntity<ProjectTask>(projectTask,HttpStatus.OK);
	}
	@PatchMapping("/{backlogId}/{ptid}")
	public ResponseEntity<?> updateProkject(@Valid @RequestBody ProjectTask projectTask,BindingResult result,@PathVariable String backlogId,@PathVariable String ptid){
		ResponseEntity<?> errorMap=mapValidationErrorsService.MapValidationErrorsService(result);
		if(errorMap!=null)return errorMap;
		ProjectTask updatedTask=projectTaskService.updateByProjectSequence(projectTask, backlogId, ptid);
		
		return new ResponseEntity<ProjectTask>(updatedTask,HttpStatus.OK);
	}
}
