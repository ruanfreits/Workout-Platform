package com.gym_project.projeto_bulkhouse.Services;
// package com.example.file_storage_api.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;

import java.util.Collections;
import java.nio.file.Paths;

@Service
public class S3Service
{
  	@Autowired
	private S3Client s3Client;

	private final String bucketName = "cassia-bucket";
    public List<String> listObjects() {
        try {
            ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(bucketName).build();  
        
            ListObjectsV2Response response = s3Client.listObjectsV2(request);
            System.out.println("Aqui os objetos:\n"+ response);

            if (response.contents().isEmpty()) {
                System.out.println("Nenhum objeto encontrado no bucket: " + bucketName);
                return Collections.emptyList();
            }
    
            return response.contents().stream()
                    .map(S3Object::key) // Obtém a chave (nome do objeto)
                    .collect(Collectors.toList());
    
        } catch (S3Exception e) {
            System.err.println("Erro ao listar objetos no bucket: " + e.awsErrorDetails().errorMessage());
            return Collections.emptyList();
        }
    }


    public void uploadFile(String keyName, String filePath)
	{
		PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(keyName).build();
		s3Client.putObject(putObjectRequest, RequestBody.fromFile(Paths.get(filePath)));
	}


    public void downloadFile(String keyName, String downloadPath)
    {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(keyName).build();
        s3Client.getObject(getObjectRequest, Paths.get(downloadPath));
    }

    public byte[] downloadFileTemp(String keyName)
    {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(keyName).build();
        s3Client.getObject(getObjectRequest);
        ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
        return objectBytes.asByteArray();
    }
}



// 	@Autowired
// 	private S3Client s3Client;

// 	private final String bucketName = "rambucket1234";


//     public List<String> listObjects() {
//         ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(bucketName).build();

//         ListObjectsV2Response response = s3Client.listObjectsV2(request);

//         return response.contents().stream()
//                 .map(S3Object::key) // Pega o nome do arquivo (chave)
//                 .collect(Collectors.toList());
//     }
// }