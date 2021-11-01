package com.ey.asp.message.processor;


import com.asp.common.AppExecContext;
import com.asp.common.ContextBuilder;
import com.asp.common.Message;

public class RunnableTaskProcessor implements Runnable{
	protected TaskProcessor processor;

	protected Message message;

	
	private ContextBuilder contextBuilder;
	
	public RunnableTaskProcessor(TaskProcessor task, Message message) {
		this.processor = task;
		this.message = message;
	}

	@Override
	public void run() {
		 
		AppExecContext context =null;
		processor.execute(message, context);
		
	}

}
