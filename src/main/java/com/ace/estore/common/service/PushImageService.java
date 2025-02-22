package com.ace.estore.common.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ace.estore.common.dto.ImageDetailsDto;

public interface PushImageService {

	public List<ImageDetailsDto> uploadImages(List<MultipartFile> file);

	public String imageUrl(String publicId);

	public String transformedImageUrl(String publicId, String width, String height, String crop);
}
