package com.epam.chatspring.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStoreService implements FileStoreService {

	@Value( "${config.downloadfolder}" )
	private String downloadFolder;
	
	private static final Logger logger = Logger.getLogger(FileSystemStoreService.class);
	
	public void saveFile(MultipartFile file){
		try {
			byte[] bytes = file.getBytes();
			String fileName = file.getOriginalFilename();
			logger.debug(String.format("Saving %s on %s", fileName, downloadFolder));
			Path path = Paths.get(downloadFolder + fileName);
			Files.write(path, bytes);
		} catch (IOException e) {
			logger.error(String.format("When saving file: %s", e));
		}
	}
}
