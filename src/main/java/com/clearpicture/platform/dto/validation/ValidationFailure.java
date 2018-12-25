package com.clearpicture.platform.dto.validation;

import lombok.Data;

/**
 * 
 * @author Nuwan
 *
 */
@Data
public class ValidationFailure {

	public ValidationFailure(String field, String code) {
		this.field = field;
		this.code = code;
	}

	private String field;

	private String code;

}
