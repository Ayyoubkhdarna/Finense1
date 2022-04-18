/**
 * 
 */
package com.univers.architecture.transporter.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.univers.architecture.transporter.model.TaskExecution;

import java.util.List;

/**
 * @author sabir
 *
 */
public interface ITaskExecutionRepository extends JpaRepository<TaskExecution, Long> {

    Page<TaskExecution> findByTaskConfigNameContaining(String taskConfigName, Pageable pageable);
}
