package com.codelab.rest.webservices.trainingtool.service;

import com.codelab.rest.webservices.trainingtool.exception.FileDownloadException;
import com.codelab.rest.webservices.trainingtool.exception.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
public interface FileService {
    String uploadFile(MultipartFile multipartFile) throws FileUploadException, IOException;

    Object downloadFile(String fileName) throws FileDownloadException, IOException;

    boolean delete(String fileName);
}