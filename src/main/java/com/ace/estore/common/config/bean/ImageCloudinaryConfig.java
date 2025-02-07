package com.ace.estore.common.config.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ImageCloudinaryConfig {

	@Value("${cloud.cloudinary.cloud.key}")
	private String key;
	@Value("${cloud.cloudinary.cloud.secret}")
	private String secret;
	@Value("${cloud.cloudinary.cloud.name}")
	private String cloudName;

	@Bean
	Cloudinary cloudinary() {
		log.info("Bean for cloudinary service");
		return new Cloudinary(ObjectUtils.asMap("cloud_name", cloudName, "api_key", key, "api_secret", secret));
	}
}
