package com.csk.ppmtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csk.ppmtool.domain.Backlog;
import com.csk.ppmtool.domain.ProjectTask;
import com.csk.ppmtool.exceptions.ProjectNotFoundException;
import com.csk.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	@Autowired
	private ProjectService projectService;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {
		// Exceptions: Project not found
		// Project not found
		
			// PTs to be added to a specific project, project != null, BL exists
			Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();
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
				projectTask.setStatus("TO_DO");
			}
			// INITIAL priority when priority null
			if (projectTask.getPriority() == null ||projectTask.getPriority()==0) {// future we need projectTask.getProject()==0 to handle the form
				projectTask.setPriority(3);
			}
			return projectTaskRepository.save(projectTask);
		

	}

	public Iterable<ProjectTask> findBacklogById(String id, String username) {

		projectService.findProjectByIdentifier(id, username);
		/*
		 * Project project = projectRepository.findByProjectIdentifier(id); if (project
		 * == null) { throw new ProjectNotFoundException("Project with ID \"" + id +
		 * "\" does not exit!"); }
		 */
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
	}
	public ProjectTask findByProjectSequence(String sequence) {
		return projectTaskRepository.findByProjectSequence(sequence);
	}

	public ProjectTask findPTByProjectSequence(String backlogId, String ptid,String username) {
		projectService.findProjectByIdentifier(backlogId, username);
		/*
		 * Backlog backlog=backlogRepoitory.findByProjectIdentifier(backlogId);
		 * if(backlog==null) { throw new ProjectNotFoundException("Project with ID \"" +
		 * backlogId + "\" does not exit!"); }
		 */		
		ProjectTask projectTask=projectTaskRepository.findByProjectSequence(ptid);
		if(projectTask==null) {
			throw new ProjectNotFoundException("Project Task \"" + ptid + "\" does not found");
		}
		if(!projectTask.getProjectIdentifer().equals(backlogId)) {
			throw new ProjectNotFoundException("Project Task \""+ptid+"\" does not exit in project: \""+backlogId+"\"");
		}
		return projectTask;
	}
	public ProjectTask updateByProjectSequence(ProjectTask updatedTask,String backlogId,String ptid,String username) {
		ProjectTask projectTask=findPTByProjectSequence(backlogId,ptid,username);
//		ProjectTask projectTask=projectTaskRepository.findByProjectSequence(updatedTask.getProjectSequence());
		projectTask=updatedTask;
		return projectTaskRepository.save(projectTask);
	}
	public void deletePTByProjectSequence(String backlogId,String ptid,String username) {
		ProjectTask projectTask=findPTByProjectSequence(backlogId,ptid,username);
		/*
		 * Backlog backlog=projectTask.getBacklog(); List<ProjectTask>
		 * pt=backlog.getProjectTasks(); pt.remove(projectTask);
		 * backlogRepoitory.save(backlog);
		 */
		projectTaskRepository.delete(projectTask);
	}
	
	
}
