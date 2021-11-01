package com.asp.common;

import org.springframework.stereotype.Component;

import com.asp.message.processor.TaskProcessor;


@Component
public interface MessageProcessorFactory {
	public TaskProcessor getMessageTaskProcessor(Message message)throws Exception;


}
