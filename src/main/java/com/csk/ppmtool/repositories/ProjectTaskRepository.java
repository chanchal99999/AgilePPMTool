package com.csk.ppmtool.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csk.ppmtool.domain.ProjectTask;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {
	
	List<ProjectTask> findByProjectIdentifierOrderByPriority(String id);
	ProjectTask findByProjectSequence(String sequence);
//	ProjectTask findPTByProjectSequence(String backlogId, String ptid);
}
