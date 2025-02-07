package com.ace.estore.common.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ace.estore.common.constants.ApplicationConstants;
import com.ace.estore.common.dto.ImageDetailsDto;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PushImageServiceImpl implements PushImageService {

	@Autowired
	private Cloudinary cloudinary;

//	public PushImageServiceImpl(Cloudinary cloudinary) {
//		this.cloudinary = cloudinary;
//	}

	@Override
	public List<ImageDetailsDto> uploadImages(List<MultipartFile> file) {
		return file.stream().map(image -> {
			try {
				Map result = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
				return ImageDetailsDto.builder().publicId(result.get("public_id").toString())
						.securedUrl(result.get("secure_url").toString()).format(result.get("format").toString())
						.build();
			} catch (IOException e) {
				log.info("Error uploading image to cloud due to: " + e.getMessage());
			}
			return null;
		}).filter(image -> Objects.nonNull(image)).collect(Collectors.toList());
	}

	@Override
	public String imageUrl(String publicId) {
		return cloudinary.url().generate(publicId);
	}

	@Override
	public String transformedImageUrl(String publicId, String width, String height, String crop) {
		return cloudinary.url()
				.transformation(new Transformation<>()
						.width(StringUtils.hasLength(width) ? width : ApplicationConstants.DEFAULT_IMAGE_WIDTH)
						.height(StringUtils.hasLength(height) ? height : ApplicationConstants.DEFAULT_IMAGE_HEIGHT)
						.crop(StringUtils.hasLength(crop) ? crop : ApplicationConstants.DEFAULT_IMAGE_CROP))
				.generate(publicId);
	}

}
