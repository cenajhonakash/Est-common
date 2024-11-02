package com.ace.estore.common.utils;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.ace.estore.common.constants.ApplicationConstants;

@Component
public class EsCommonUtils {

	public LocalDateTime convertStringToLocalDateTimeMs(String date) {
		return LocalDateTime.parse(date, ApplicationConstants.DT_FORMATTER_MS);
	}

	public Double calculateDiscountedRate(Double costPrice, Double discount) {
		return costPrice - (costPrice * discount / 100);
	}
}
