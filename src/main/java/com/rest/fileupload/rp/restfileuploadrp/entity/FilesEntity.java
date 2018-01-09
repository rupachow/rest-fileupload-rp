package com.rest.fileupload.rp.restfileuploadrp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FilesEntity {

	@Id
	@GeneratedValue
	public long id;
	public String fileName;
	public String contentType;

	public FilesEntity(){

	}

	public FilesEntity(String fileName, String contentType) {
		super();
		this.fileName = fileName;
		this.contentType = contentType;
	}

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
