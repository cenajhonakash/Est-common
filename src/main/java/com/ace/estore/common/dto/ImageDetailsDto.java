package com.ace.estore.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;

@Builder
@JsonInclude(Include.NON_NULL)
public record ImageDetailsDto(String publicId, String securedUrl, String format) {

}
