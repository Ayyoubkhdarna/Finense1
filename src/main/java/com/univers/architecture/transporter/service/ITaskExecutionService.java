/**
 * 
 */
package com.univers.architecture.transporter.service;

import java.util.List;

import com.univers.architecture.transporter.model.TaskExecution;

/**
 * @author sabir
 *
 */
public interface ITaskExecutionService {

	public List<TaskExecution> getAllTaskExecution();
}
