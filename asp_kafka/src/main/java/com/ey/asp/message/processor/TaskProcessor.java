package com.ey.asp.message.processor;

import com.asp.common.AppExecContext;
import com.asp.common.Message;

public interface TaskProcessor {
	public void execute(Message message,AppExecContext context);


}
