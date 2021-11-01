package com.asp.common;


import org.apache.log4j.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;

@Service

public class StaticContextHolder implements BeanFactoryAware {

	
	
	  private static final Logger LOGGER =
	  Logger.getLogger(StaticContextHolder.class);
	 
	 
	
    public static BeanFactory CONTEXT;

    public StaticContextHolder() {
    }

    public static Object getBean(String s) throws BeansException {
        return CONTEXT.getBean(s);
    }

    public static <T> T getBean(
    		String s, Class<T> tClass) throws BeansException {
        return CONTEXT.getBean(s, tClass);
    }

    public static <T> T getBean(Class<T> tClass) throws BeansException {
        return CONTEXT.getBean(tClass);
    }

    public static Object getBean(
    		String s, Object... objects) throws BeansException {
        return CONTEXT.getBean(s, objects);
    }

    public static boolean containsBean(String s) {
        return CONTEXT.containsBean(s);
    }

    @Override
    /**
     * This method should only be called once, by the spring container, at the
     * time of initialization of the Spring context. If in any scenario, this
     * method is called more than once, then log an error (indicating that 
     * something is wrong with the Spring Initialization process).
     */
    public void setBeanFactory(
    			BeanFactory applicationContext) throws BeansException {
		
		  if(LOGGER.isInfoEnabled()) { LOGGER.info("Setting the application context " +
		  "in the StaticContextHolder instance."); }
		 
        // Issue error message if this method is invoked more than once during
    	// the life cycle of the application.
		
		  LOGGER.assertLog(CONTEXT == null,
		  "CONTEXT is not null. Double Spring context creation?"); CONTEXT =
		  applicationContext;
		  
		  if(LOGGER.isInfoEnabled()) {
		  LOGGER.info("Successfully set the application context " +
		  "in the StaticContexgtHolder instance"); }
		 
    	this.CONTEXT = applicationContext;
    }    
}