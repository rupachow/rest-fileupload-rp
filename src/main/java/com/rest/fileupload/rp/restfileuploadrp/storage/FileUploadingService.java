package com.rest.fileupload.rp.restfileuploadrp.storage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rest.fileupload.rp.restfileuploadrp.entity.FileMetaData;
import com.rest.fileupload.rp.restfileuploadrp.entity.FilesEntity;

@Service
public class FileUploadingService implements UploadingService {

	//public Logger log = LoggerFactory.getLogger(FileSystemStorageService.class);

	private final Path rootLocation;

	@Autowired
	FileMetaData fls;

	@Autowired
	public FileUploadingService(FileLocationProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void store(MultipartFile file) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (file.isEmpty()) {
				throw new FileExcept("Failed to store empty file " + filename);
			}
			if (filename.contains("..")) {
				// This is a security check
				throw new FileExcept(
						"Cannot store file with relative path outside current directory "
								+ filename);
			}
			Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
					StandardCopyOption.REPLACE_EXISTING);
			FilesEntity n = new FilesEntity(file.getOriginalFilename(),file.getContentType());
			fls.save(n);
		}
		catch (IOException e) {
			throw new FileExcept("Failed to store file " + filename, e);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
					.filter(path -> !path.equals(this.rootLocation))
					.map(path -> this.rootLocation.relativize(path));
		}
		catch (IOException e) {
			throw new FileExcept("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new FilesNotFoundException(
						"Could not read file: " + filename);

			}
		}
		catch (MalformedURLException e) {
			throw new FilesNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new FileExcept("Could not initialize storage", e);
		}
	}
}
