package com.univers.architecture.transporter.dao;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.univers.architecture.transporter.model.TaskExecution;
import com.univers.architecture.transporter.model.TaskExecutionStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskConfigRepositoryTest {

	@Autowired
	private ITaskExecutionRepository taskExecutionRepository;

	@Test
	public void createTaskExecution() {

		LocalDateTime startldt = LocalDateTime.of(2018, 6, 20, 15, 15, 15);
		Date startDate = Date.from(startldt.atZone(ZoneId.systemDefault()).toInstant());

		LocalDateTime endldt = LocalDateTime.of(2018, 6, 20, 15, 30, 15);
		Date endDate = Date.from(endldt.atZone(ZoneId.systemDefault()).toInstant());

		TaskExecution task1 = new TaskExecution();
		task1.setTaskConfigName("taskConfigName");
		task1.setStatus(TaskExecutionStatus.ERROR);
		task1.setTransportedFiles("file1, file2, file3");
		task1.setStartDate(startDate);
		task1.setMessage("long message");
		task1.setEndDate(endDate);

		this.taskExecutionRepository.save(task1);
		assertTrue(task1.getId() != null);
	}

}
