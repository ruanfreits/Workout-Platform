package com.gym_project.projeto_bulkhouse.Controller;

import java.io.File;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.gym_project.projeto_bulkhouse.Services.S3Service;

import io.jsonwebtoken.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth/api/files")

public class S3Controller{
@Autowired
private S3Service S3Service;


@GetMapping("/all")
public List<String> getAllobjects(){
    return S3Service.listObjects();
}

	@PostMapping("/sendFile")
	public  String uploadFile(@RequestParam("file") MultipartFile file) throws IOException, java.io.IOException{
			//The file is saved temporarily on the server before uploading to S3.
			String keyName = file.getOriginalFilename();
			File tempFile = File.createTempFile("temp", null);
			file.transferTo(tempFile);
			
			S3Service.uploadFile(keyName, tempFile.getAbsolutePath());
			return "File uploaded successfully.";
	}

	//Downloads a file from AWS S3 to a specific location on your computer.
	@GetMapping("/download")
	public String downloadFile(@RequestParam("keyName") String keyName, @RequestParam("downloadPath") String downloadPath)
	{
		S3Service.downloadFile(keyName, downloadPath);
		return "File downloaded successfully.";
	}


	@GetMapping("/base64/{filename}")
	public ResponseEntity<String> getBase64File(@PathVariable String filename){		
			byte[] file = S3Service.downloadFileTemp(filename);

			String base64 = Base64.getEncoder().encodeToString(file);
			
			return ResponseEntity.ok(base64);
		}

}