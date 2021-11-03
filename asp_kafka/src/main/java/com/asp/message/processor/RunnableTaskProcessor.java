package com.asp.message.processor;


import com.asp.common.Message;

public class RunnableTaskProcessor implements Runnable{
	protected TaskProcessor processor;

	protected Message message;

	
	public RunnableTaskProcessor(TaskProcessor task, Message message) {
		this.processor = task;
		this.message = message;
	}

	@Override
	public void run() {
		 
		
		processor.execute(message);
		
	}

}
