package com.epam.chatspring.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadingController {

	private static String UPLOADED_FOLDER = "D://images//";

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public void singleFileUpload(@RequestParam MultipartFile file,
			@RequestParam(required = false) String nick, @RequestParam(required = false) String password) {
		try {
			System.out.println(nick + " " + password);
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
