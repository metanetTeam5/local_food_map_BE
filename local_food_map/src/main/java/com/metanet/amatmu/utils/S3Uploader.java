package com.metanet.amatmu.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.metanet.amatmu.member.exception.MemberErrorCode;
import com.metanet.amatmu.member.exception.MemberException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class S3Uploader {

	@Autowired
	private AmazonS3Client amazonS3Client;
	
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	
	public String fileUpload(MultipartFile file) throws MemberException {
	    String newFileName = MultipartFileUtil.createNewFileName(file.getOriginalFilename());
	    ObjectMetadata objMeta = MultipartFileUtil.getObjMeta(file);
	    try {
	      amazonS3Client.putObject(
	          new PutObjectRequest(bucket, newFileName, file.getInputStream(), objMeta));
	    } catch (Exception e) {
	      throw new MemberException(MemberErrorCode.UPLOAD_PROFILEIMG_FAILED);
	    }
	    return amazonS3Client.getUrl(bucket, newFileName).toString();
	  }
}
