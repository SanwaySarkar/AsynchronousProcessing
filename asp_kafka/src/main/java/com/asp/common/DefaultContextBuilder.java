package com.asp.common;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class DefaultContextBuilder implements ContextBuilder {

	private static final Logger LOGGER = Logger
			.getLogger(DefaultContextBuilder.class);
	
	
	@Override
	public AppExecContext createAppContext(String userName) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Building the AppExecContext...");
		}
		AppExecContext context = new AppExecContext();
		context.setUserName(userName);
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Built the AppExecContext!!");
		}		
		return context;
	}

	
}
