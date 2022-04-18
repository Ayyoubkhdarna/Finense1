/**
 * 
 */
package com.univers.architecture.transporter.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.univers.architecture.transporter.dao.ITaskExecutionRepository;
import com.univers.architecture.transporter.model.TaskExecution;

/**
 * @author sabir
 *
 */
@Service
public class TaskExecutionServiceImpl implements ITaskExecutionService {

	private static final Logger log = LoggerFactory.getLogger(TaskExecutionServiceImpl.class);

	@Autowired
	private ITaskExecutionRepository taskExecutionRepository;

	@Override
	public List<TaskExecution> getAllTaskExecution() {

		log.warn("Loading all taskExecution ! be careful about performance");

		return (List<TaskExecution>) this.taskExecutionRepository.findAll();
	}

}
