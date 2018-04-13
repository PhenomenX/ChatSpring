package com.epam.chatspring.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStoreService {
	public void saveFile(MultipartFile file);
}
