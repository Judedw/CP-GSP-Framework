package com.clearpicture.platform.componant;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @author Nuwan
 *
 */
public class StaticApplicationContext implements ApplicationContextAware {

	private static ApplicationContext context;

	public void setApplicationContext(ApplicationContext context) {
		StaticApplicationContext.context = context;
	}

	public static ApplicationContext getApplicationContext() {
		return context;
	}

}
