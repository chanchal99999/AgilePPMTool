package com.csk.ppmtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csk.ppmtool.domain.Backlog;
import com.csk.ppmtool.domain.Project;
import com.csk.ppmtool.domain.ProjectTask;
import com.csk.ppmtool.exceptions.ProjectNotFoundException;
import com.csk.ppmtool.repositories.BacklogRepoitory;
import com.csk.ppmtool.repositories.ProjectRepository;
import com.csk.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	@Autowired
	private BacklogRepoitory backlogRepoitory;
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	@Autowired
	private ProjectRepository projectRepository;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		// Exceptions: Project not found
		// Project not found
		try {
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
			if (projectTask.getPriority() == null) {// future we need projectTask.getProject()==0 to handle the form
				projectTask.setPriority(3);
			}
			return projectTaskRepository.save(projectTask);
		} catch (Exception e) {
			throw new ProjectNotFoundException("Project not Found");
		}

	}

	public Iterable<ProjectTask> findBacklogById(String id) {

		Project project = projectRepository.findByProjectIdentifier(id);
		if (project == null) {
			throw new ProjectNotFoundException("Project with ID " + id + " does not exit!");
		}
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
	}
	public ProjectTask findByProjectSequence(String sequence) {
		return projectTaskRepository.findByProjectSequence(sequence);
	}

	public ProjectTask findPTByProjectSequence(String backlogId, String ptid) {
		Backlog backlog=backlogRepoitory.findByProjectIdentifier(backlogId);
		if(backlog==null) {
			throw new ProjectNotFoundException("Project with ID " + backlogId + " does not exit!");
		}
		ProjectTask projectTask=projectTaskRepository.findByProjectSequence(ptid);
		if(projectTask==null) {
			throw new ProjectNotFoundException("Project Task " + ptid + " does not found");
		}
		if(!projectTask.getProjectIdentifer().equals(backlogId)) {
			throw new ProjectNotFoundException("Project Task '"+ptid+"' does not exit in project: '"+backlogId+"'");
		}
		return projectTask;
	}
	public ProjectTask updateByProjectSequence(ProjectTask updatedTask,String backlogId,String ptid) {
		ProjectTask projectTask=projectTaskRepository.findByProjectSequence(ptid);
//		ProjectTask projectTask=projectTaskRepository.findByProjectSequence(updatedTask.getProjectSequence());
		projectTask=updatedTask;
		return projectTaskRepository.save(projectTask);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
