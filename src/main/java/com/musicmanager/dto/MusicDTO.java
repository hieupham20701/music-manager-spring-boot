package com.musicmanager.dto;

import java.util.Date;

public class MusicDTO {

	private Long id;

	private String name;

	private String description;

	private byte[] file;
	private Date createdDate;
	private Date modifiedDate;

	private String categoryDTO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCategoryDTO() {
		return categoryDTO;
	}

	public void setCategoryDTO(String categoryDTO) {
		this.categoryDTO = categoryDTO;
	}

	
}
