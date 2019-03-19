package com.clearpicture.platform.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.clearpicture.platform.configuration.PlatformConfigProperties;
import com.clearpicture.platform.exception.ComplexValidationException;
import com.clearpicture.platform.service.CommonFileStorageService;
import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.stream.Collectors;

/**
 * CommonFileStorageServiceImpl - Sprint 8 : Rise of The TruVerus
 * <p>
 * Created by Raveen on 2019/03/13.
 */

@Service
public class CommonFileStorageServiceImpl implements CommonFileStorageService {

    @Autowired
    private PlatformConfigProperties configProperties;

    @Autowired
    private AmazonS3 amazonS3ClientService;


    @Override
    public void commonTextFileUpload(String fileContent, String fileName) {
        try {
            String bucketName = this.configProperties.getAwsS3Setting().getBucketName();
            String folderPath = this.configProperties.getAwsS3Setting().getSurveyFolderPath(); // eg : local/future-survey/
            String filePathName = folderPath + fileName;
            PutObjectRequest request = new PutObjectRequest(bucketName, filePathName, writeStringContentFile(fileContent, fileName));
            this.amazonS3ClientService.putObject(request);
        } catch (AmazonServiceException e) {
            e.printStackTrace();
            throw new ComplexValidationException("commonFile", "fileUpload.AwsError");
        } catch (SdkClientException e) {
            e.printStackTrace();
            throw new ComplexValidationException("commonFile", "fileUpload.sdkError");
        }
    }

    @Override
    public String readFileString(String fileName) {

        String bucketName = this.configProperties.getAwsS3Setting().getBucketName();
        String folderPath = this.configProperties.getAwsS3Setting().getSurveyFolderPath(); // eg : local/future-survey/

        try {
            S3Object object = this.amazonS3ClientService.getObject(new GetObjectRequest(bucketName, folderPath + fileName));
            InputStream objectData = object.getObjectContent();
            return this.convert(objectData, Charset.forName("UTF-8"));
        } catch (AmazonServiceException e) {
            e.printStackTrace();
            throw new ComplexValidationException("commonFile", "readFileStr.AwsError");
        } catch (SdkClientException e) {
            e.printStackTrace();
            throw new ComplexValidationException("commonFile", "readFileStr.sdkError");
        }
    }

    @Override
    public void deleteFile(String fileName) {

        String bucketName = this.configProperties.getAwsS3Setting().getBucketName();
        String folderPath = this.configProperties.getAwsS3Setting().getSurveyFolderPath(); // eg : local/future-survey/
        try {
            this.amazonS3ClientService.deleteObject(bucketName, folderPath + fileName);
        } catch (AmazonServiceException e) {
            e.printStackTrace();
            throw new ComplexValidationException("commonFile", "deleteFile.AwsError");
        } catch (SdkClientException e) {
            e.printStackTrace();
            throw new ComplexValidationException("commonFile", "deleteFile.sdkError");
        }
    }


    public void storeBase64Image(String content, String context, String imageName) {


        content = content.replaceFirst("^data:image/[^;]*;base64,?", "");
        byte[] decodedByte = Base64.getDecoder().decode(content);
        InputStream inputStream = new ByteArrayInputStream(decodedByte);

        String bucketName = this.configProperties.getAwsS3Setting().getBucketName();
        PlatformConfigProperties.AwsS3Setting awsSetting = this.configProperties.getAwsS3Setting();

        String folderPath = "";

        switch (context) {

            case "event":
                folderPath = awsSetting.getEventFolderPath();
                break;
            case "promo":
                folderPath = awsSetting.getPromoFolderPath();
                break;
            case "user":
                folderPath = awsSetting.getUserFolderPath();
                break;
            case "client":
                folderPath = awsSetting.getClientFolderPath();
                break;
        }

        try {
            ObjectMetadata metaData = new ObjectMetadata();
            metaData.setContentLength(decodedByte.length);
            metaData.setContentType("image/png");
            this.amazonS3ClientService.putObject(bucketName, folderPath + imageName, inputStream, metaData);
        } catch (AmazonServiceException e) {
            e.printStackTrace();
            throw new ComplexValidationException("commonFile", "b64ImageUpload.AwsError");
        } catch (SdkClientException e) {
            e.printStackTrace();
            throw new ComplexValidationException("commonFile", "b64ImageUpload.sdkError");
        }

    }


    public File commonFileDownload(String fileName, String filePath) {

        try {
            String bucketName = this.configProperties.getAwsS3Setting().getBucketName();
            S3Object object = this.amazonS3ClientService.getObject(bucketName, filePath + fileName);
            File file = new File(fileName);
            FileUtils.copyInputStreamToFile(object.getObjectContent(), file);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ComplexValidationException("commonFile", "commonImgDownload.ioError");
        } catch (AmazonServiceException e) {
            e.printStackTrace();
            throw new ComplexValidationException("commonFile", "commonImgDownload.AwsError");
        } catch (SdkClientException e) {
            e.printStackTrace();
            throw new ComplexValidationException("commonFile", "commonImgDownload.sdkError");
        }

    }


    /**
     * Convert Input Stream into String
     *
     * @param inputStream
     * @param charset
     * @return
     */
    private String convert(InputStream inputStream, Charset charset) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ComplexValidationException("commonFile", "convert.byteToString");
        }
    }


    /**
     * Write the new file object using byte array and given file name with extension
     *
     * @param content
     * @param fileName
     * @return
     */
    private File writeStringContentFile(String content, String fileName) {

        try {
            File file = new File(fileName);
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            out.append(content);
            out.flush();
            out.close();
            return file;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new ComplexValidationException("commonFile", "writeContent.unsupportedEncode");
        } catch (IOException e) {
            e.printStackTrace();
            throw new ComplexValidationException("commonFile", "writeContent.ioError");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ComplexValidationException("commonFile", "writeContent.writeError");
        }
    }


}
