package com.epam.chatspring.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStoreService implements FileStoreService {

	@Value( "${config.downloadfolder}" )
	private String downloadFolder;
	
	public void saveFile(MultipartFile file){
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(downloadFolder + file.getOriginalFilename());
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
