package com.clearpicture.platform.validation.annotation;

import com.clearpicture.platform.validation.validator.PasswordPolicyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { PasswordPolicyValidator.class })
public @interface PasswordPolicy {

	Flag[] flags() default {};

	String message() default "pattern did not match.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	public static enum Flag {

		UNIX_LINES(java.util.regex.Pattern.UNIX_LINES),

		CASE_INSENSITIVE(java.util.regex.Pattern.CASE_INSENSITIVE),

		COMMENTS(java.util.regex.Pattern.COMMENTS),

		MULTILINE(java.util.regex.Pattern.MULTILINE),

		DOTALL(java.util.regex.Pattern.DOTALL),

		UNICODE_CASE(java.util.regex.Pattern.UNICODE_CASE),

		CANON_EQ(java.util.regex.Pattern.CANON_EQ);

		private final int value;

		private Flag(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	@interface List {

		PasswordPolicy[] value();
	}

}
