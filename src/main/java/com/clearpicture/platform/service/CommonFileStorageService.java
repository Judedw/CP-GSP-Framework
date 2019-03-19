package com.clearpicture.platform.service;


import java.io.File;

/**
 * CommonFileStorageService - Sprint 8 : Rise of The TruVerus
 * <p>
 * Created by Raveen on 2019/03/13.
 */


public interface CommonFileStorageService {


    void commonTextFileUpload(String fileContent, String fileName);

    String readFileString(String fileName);

    void deleteFile(String fileName);

    void storeBase64Image(String content, String context, String imageName);

    File commonFileDownload(String fileName, String filePath);


}
