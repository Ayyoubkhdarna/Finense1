/**
 * 
 */
package com.univers.architecture.transporter.web;

import java.util.*;

import com.univers.architecture.transporter.dao.ITaskExecutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.univers.architecture.transporter.model.TaskExecution;
import com.univers.architecture.transporter.service.ITaskExecutionService;

/**
 * @author sabir
 *
 */
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TaskExecutionController {

	@Autowired
	private ITaskExecutionRepository taskExecutionRepository;

	private Sort.Direction getSortDirection(String direction) {
		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}

		return Sort.Direction.ASC;
	}

	@GetMapping("/sortedtasksexecutions")
	public ResponseEntity<List<TaskExecution>> getAllTasksExecutions(@RequestParam(defaultValue = "id,desc") String[] sort) {

		try {
			List<Sort.Order> orders = new ArrayList<Sort.Order>();

			if (sort[0].contains(",")) {
				for (String sortOrder : sort) {
					String[] _sort = sortOrder.split(",");
					orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
				}
			} else {
				orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
			}

			List<TaskExecution> tasksExecutions = taskExecutionRepository.findAll(Sort.by(orders));

			if (tasksExecutions.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(tasksExecutions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/tasksexecutions")
	public ResponseEntity<Map<String, Object>> getAllTasksExecutionsPage(
			@RequestParam(required = false) String taskConfigName,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) {

		try {
			List<Sort.Order> orders = new ArrayList<Sort.Order>();

			if (sort[0].contains(",")) {
				for (String sortOrder : sort) {
					String[] _sort = sortOrder.split(",");
					orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
				}
			} else {
				orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
			}

			List<TaskExecution> taskExecutions = new ArrayList<TaskExecution>();
			Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

			Page<TaskExecution> pageTuts;
			if (taskConfigName == null)
				pageTuts = taskExecutionRepository.findAll(pagingSort);
			else
				pageTuts = taskExecutionRepository.findByTaskConfigNameContaining(taskConfigName, pagingSort);

			taskExecutions = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("tasksExecutions", taskExecutions);
			response.put("currentPage", pageTuts.getNumber());
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@GetMapping("/tasksexecutions/{id}")
	public ResponseEntity<TaskExecution> getTaskExecutionById(@PathVariable("id") long id) {
		Optional<TaskExecution> taskExecutionData = taskExecutionRepository.findById(id);

		if (taskExecutionData.isPresent()) {
			return new ResponseEntity<>(taskExecutionData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/tasksexecutions")
	public ResponseEntity<TaskExecution> createTaskExecution(@RequestBody TaskExecution taskExecution) {
		try {
			TaskExecution _taskExecution = taskExecutionRepository.save(new TaskExecution(taskExecution.getTaskConfigName(), taskExecution.getDurationInSeconds(), taskExecution.getStatus(),taskExecution.getTransportedFiles(),taskExecution.getMessage(),taskExecution.isEmailErrorSent(),taskExecution.getStartDate(),taskExecution.getEndDate(),taskExecution.isInProgressCopyDetected(),taskExecution.getNbrCheckInProgressCopy()));
			return new ResponseEntity<>(_taskExecution, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PutMapping("/tasksexecutions/{id}")
	public ResponseEntity<TaskExecution> updateTaskExecution(@PathVariable("id") long id, @RequestBody TaskExecution taskExecution) {
		Optional<TaskExecution> taskExecutionData = taskExecutionRepository.findById(id);

		if (taskExecutionData.isPresent()) {
			TaskExecution _taskExecution = taskExecutionData.get();

			_taskExecution.setTaskConfigName(taskExecution.getTaskConfigName());
			_taskExecution.setDurationInSeconds(taskExecution.getDurationInSeconds());
			_taskExecution.setStatus(taskExecution.getStatus());

			_taskExecution.setTransportedFiles(taskExecution.getTransportedFiles());
			_taskExecution.setMessage(taskExecution.getMessage());
			_taskExecution.setEmailErrorSent(taskExecution.isEmailErrorSent());
			_taskExecution.setStartDate(taskExecution.getStartDate());
			_taskExecution.setEndDate(taskExecution.getEndDate());
			_taskExecution.setInProgressCopyDetected(taskExecution.isInProgressCopyDetected());
			_taskExecution.setNbrCheckInProgressCopy(taskExecution.getNbrCheckInProgressCopy());
			return new ResponseEntity<>(taskExecutionRepository.save(_taskExecution), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/tasksexecutions/{id}")
	public ResponseEntity<HttpStatus> deleteTaskExecution(@PathVariable("id") long id) {
		try {
			taskExecutionRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/tasksexecutions")
	public ResponseEntity<HttpStatus> deleteAllTasksExecutions() {
		try {
			taskExecutionRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
