package com.asp.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ey.asp.message.processor.TaskProcessor;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component
public class MessageProcessorFactoryImpl implements MessageProcessorFactory {
	private static final Logger LOGGER = Logger.getLogger(MessageProcessorFactoryImpl.class);

	private Map<String, String> taskProcessorMap = new HashMap<>();

	@PostConstruct
	public void initProperties() throws IOException {
		LOGGER.info("About to load the TaskProcessors Mapping from JSON");
		try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream("taskprocessors.json");
				InputStreamReader reader = new InputStreamReader(stream)) {
			JsonParser parser = new JsonParser();
			JsonElement root = parser.parse(reader);
			JsonObject obj = root.getAsJsonObject();
			Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
			entries.forEach(entry -> {
				String key = entry.getKey();
				JsonElement ele = entry.getValue();
				String value = ele.getAsString();
				taskProcessorMap.put(key, value);
			});
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Loaded the TaskProcessors Mapping " + "from JSON successfully!!" +taskProcessorMap);
			}
		} catch (Exception ex) {
			String msg = "Unexpected error occured while " + "loading the TaskProcessors mapping from Json";
			LOGGER.error(msg);
			throw new IOException(msg, ex);
		}
		
	}

	@Override
	public TaskProcessor getMessageTaskProcessor(Message message) throws Exception {
	
		// TODO Auto-generated method stub
		String taskType = message.getMessageType();
		String beanName = taskProcessorMap.get(taskType);
		
		if(beanName == null) {
			String msg = String.format("The task type '%s' is  "
					+ "not mapped to a TaskProcessor", taskType);
			LOGGER.error(msg);
			throw new IOException(msg);			
		}
		
		try {
			return StaticContextHolder.getBean(beanName, TaskProcessor.class);
		} catch(Exception ex) {
			String msg = String.format("Unable to get the Spring "
					+ "TaskProcessor bean with the name: '%s'", beanName);
			LOGGER.error(msg);
			throw new IOException(msg, ex);
		}

	}
}
