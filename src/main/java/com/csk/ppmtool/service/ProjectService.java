package com.csk.ppmtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csk.ppmtool.domain.Backlog;
import com.csk.ppmtool.domain.Project;
import com.csk.ppmtool.exceptions.ProjectIdException;
import com.csk.ppmtool.repositories.BacklogRepoitory;
import com.csk.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepoitory backlogRepository;
	
	public Project saveOrUpdateProject(Project project) {
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			if(project.getId()==null) {
				Backlog backlog=new Backlog();
				project.setBaacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}
//			if(project.getId()!=null) {
			else {
				project.setBaacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			return projectRepository.save(project);
		}
		catch(Exception e) {
			throw new ProjectIdException("Project Id : \""+project.getProjectIdentifier().toUpperCase()+"\" already exits");
		}
	}
	
	public Project findProjectByIdentifier(String projectId) {
		Project project=projectRepository.findByProjectIdentifier(projectId);
		if(project==null) {
			throw new ProjectIdException("Project Id : \""+projectId+"\" does not exits");
		}
		return project;
	}
	public Iterable<Project> findAllProject(){
		return projectRepository.findAll();
	}
	
	public void deleteProjectByIdentifier(String projectId) {
		Project project=projectRepository.findByProjectIdentifier(projectId);
		if(project==null) {
			throw new ProjectIdException("Cannot Project with Id : \""+projectId+"\" This project does not exits");
		}
		projectRepository.delete(project);
	}
	
}
