package com.csk.ppmtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csk.ppmtool.domain.Backlog;
import com.csk.ppmtool.domain.ProjectTask;
import com.csk.ppmtool.repositories.BacklogRepoitory;
import com.csk.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	@Autowired
	private BacklogRepoitory backlogRepoitory;
	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		// Exceptions: Project not found

		// PTs to be added to a specific project, project != null, BL exists
		Backlog backlog = backlogRepoitory.findByProjectIdentifier(projectIdentifier);
		// set the bl to pt
		projectTask.setBacklog(backlog);
		// we want our project sequence to be like this: IDPRO-1 IDPRO-2 ...100 101
		Integer BacklogSequence = backlog.getPTSequence();
		// Update the BL SEQUENCE
		BacklogSequence++;
		backlog.setPTSequence(BacklogSequence);
		// Add Sequence to Project Task
		projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
		projectTask.setProjectIdentifer(projectIdentifier);

		// Initial status when status is null
		if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
			projectTask.setStatus("To_Do");
		}
		// INITIAL priority when priority null
		if (projectTask.getPriority() == null) {//future we need projectTask.getProject()==0 to handle the form
			projectTask.setPriority(3);
		}
		return projectTaskRepository.save(projectTask);
	}
}
