/**
 * 
 */
package com.univers.architecture.transporter.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Infos d'exécutions d'une Task dans le temps
 * 
 * @author sabir
 *
 */
@Entity
@Table(name = "task_execution")
public class TaskExecution extends AbstractBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 808913583825662039L;

	// TaskConfig could be deleted
	@Column
	private String taskConfigName;

	@Column
	private long durationInSeconds;

	@Column
	@Enumerated(EnumType.STRING)
	private TaskExecutionStatus status;

	@Column
	private String transportedFiles;

	@Column
	private String message;

	@Column
	private boolean emailErrorSent;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	public TaskExecution() {
		super();
	}

	public TaskExecution(String taskConfigName, long durationInSeconds, TaskExecutionStatus status, String transportedFiles, String message, boolean emailErrorSent, Date startDate, Date endDate, boolean inProgressCopyDetected, int nbrCheckInProgressCopy) {
		this.taskConfigName = taskConfigName;
		this.durationInSeconds = durationInSeconds;
		this.status = status;
		this.transportedFiles = transportedFiles;
		this.message = message;
		this.emailErrorSent = emailErrorSent;
		this.startDate = startDate;
		this.endDate = endDate;
		this.inProgressCopyDetected = inProgressCopyDetected;
		this.nbrCheckInProgressCopy = nbrCheckInProgressCopy;
	}

	public String getTaskConfigName() {
		return taskConfigName;
	}

	public void setTaskConfigName(String taskConfigName) {
		this.taskConfigName = taskConfigName;
	}

	private boolean inProgressCopyDetected;

	private int nbrCheckInProgressCopy;

	public boolean isInProgressCopyDetected() {
		return inProgressCopyDetected;
	}

	public void setInProgressCopyDetected(boolean inProgressCopyDetected) {
		this.inProgressCopyDetected = inProgressCopyDetected;
	}

	public int getNbrCheckInProgressCopy() {
		return nbrCheckInProgressCopy;
	}

	public void setNbrCheckInProgressCopy(int nbrCheckInProgressCopy) {
		this.nbrCheckInProgressCopy = nbrCheckInProgressCopy;
	}

	public void incrementNbrCheckInProgressCopy() {
		this.nbrCheckInProgressCopy++;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;

	}

	/**
	 * @param message
	 *            the message to set
	 */

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isEmailErrorSent() {
		return emailErrorSent;
	}

	public void setEmailErrorSent(boolean emailErrorSent) {
		this.emailErrorSent = emailErrorSent;
	}

	/**
	 * @return the durationInSeconds
	 */
	public long getDurationInSeconds() {
		return durationInSeconds;
	}

	/**
	 * @param durationInSeconds
	 *            the durationInSeconds to set
	 */
	public void setDurationInSeconds(long durationInSeconds) {
		this.durationInSeconds = durationInSeconds;
	}

	/**
	 * @return the status
	 */
	public TaskExecutionStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(TaskExecutionStatus status) {
		this.status = status;
	}

	/**
	 * @return the transportedFiles
	 */
	public String getTransportedFiles() {
		return transportedFiles;
	}

	/**
	 * @param transportedFiles
	 *            the transportedFiles to set
	 */
	public void setTransportedFiles(String transportedFiles) {
		this.transportedFiles = transportedFiles;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setEnDateAndDuration() {
		Date endDate = new Date();
		long durationInSeconds = (endDate.getTime() - startDate.getTime()) / 1000;
		this.setDurationInSeconds(durationInSeconds);
		this.setEndDate(endDate);
	}

}
