package com.csk.ppmtool.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
			BindingResult result,@PathVariable String backlogId,Principal principal) {
		ResponseEntity<?> errorMap=mapValidationErrorsService.MapValidationErrorsService(result);
		if(errorMap!=null)return errorMap;
		ProjectTask projectTask1=projectTaskService.addProjectTask(backlogId, projectTask,principal.getName());
 		return new ResponseEntity<ProjectTask>(projectTask1,HttpStatus.CREATED);
	}
	@GetMapping("/{backlogId}")
	public Iterable<ProjectTask> getBacklog(@PathVariable String backlogId,Principal principal){
		return projectTaskService.findBacklogById(backlogId,principal.getName());
	}
	@GetMapping("/{backlogId}/{ptid}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlogId,@PathVariable String ptid ,Principal principal){
		ProjectTask projectTask=projectTaskService.findPTByProjectSequence(backlogId,ptid,principal.getName());
		return new ResponseEntity<ProjectTask>(projectTask,HttpStatus.OK);
	}
	@PatchMapping("/{backlogId}/{ptid}")
	public ResponseEntity<?> updateProject(@Valid @RequestBody ProjectTask projectTask,BindingResult result
			,@PathVariable String backlogId,@PathVariable String ptid,Principal principal){
		ResponseEntity<?> errorMap=mapValidationErrorsService.MapValidationErrorsService(result);
		if(errorMap!=null)return errorMap;
		ProjectTask updatedTask=projectTaskService.updateByProjectSequence(projectTask, backlogId, ptid,principal.getName());
		
		return new ResponseEntity<ProjectTask>(updatedTask,HttpStatus.OK);
	}
	@DeleteMapping("/{backlogId}/{ptid}")
	public ResponseEntity<?> deleteProject(@PathVariable String backlogId,@PathVariable String ptid,Principal principal){
		projectTaskService.deletePTByProjectSequence(backlogId, ptid,principal.getName());
		return new ResponseEntity<String>("Project with ID: \""+ptid+" was deleted\"", HttpStatus.OK);
	}
}
