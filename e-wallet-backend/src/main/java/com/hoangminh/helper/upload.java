package com.hoangminh.helper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


import org.springframework.web.multipart.MultipartFile;

public class upload {
	public String uploadImage(MultipartFile file) throws IOException{
		MultipartFile img = file;
		//String fileName = img.getOriginalFilename();
//		Date date = Calendar.getInstance().getTime();  
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
//		String strDate = dateFormat.format(date);  
		String fileName = img.getOriginalFilename();
		String uploadDir = "user-photos/";
		Path uploadPath = Paths.get(uploadDir);
        
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        	//Files.createDirectories(uploadPath);
        }
         
        try (InputStream inputStream = img.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return "http://localhost:8080/user-photos/"+fileName;
        } catch (IOException ioe) {        
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
	}
}
