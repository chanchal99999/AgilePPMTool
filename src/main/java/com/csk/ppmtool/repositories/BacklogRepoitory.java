package com.csk.ppmtool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.csk.ppmtool.domain.Backlog;

@Repository
public interface BacklogRepoitory extends CrudRepository<Backlog, Long> {
	
	Backlog findByProjectIdentifier(String Identifier);
	
}
