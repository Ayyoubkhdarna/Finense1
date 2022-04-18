package com.univers.architecture.transporter.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Afin de pouvoir prendre une config d'un env A et la déployer vers un env B,
 * sans modifier ce dernier on utilise des id de type UUID.
 * 
 * @author sabir
 * 
 */
@MappedSuperclass
public abstract class AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate = new Date();

	public AbstractBaseEntity() {
		this.id = UUID.randomUUID().toString();
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AbstractBaseEntity)) {
			return false;
		}
		AbstractBaseEntity other = (AbstractBaseEntity) obj;
		return getId().equals(other.getId());
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
