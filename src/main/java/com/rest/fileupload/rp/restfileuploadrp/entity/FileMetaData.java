package com.rest.fileupload.rp.restfileuploadrp.entity;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface FileMetaData extends JpaRepository<FilesEntity,String> {
	
}
