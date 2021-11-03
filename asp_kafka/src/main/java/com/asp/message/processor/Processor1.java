package com.asp.message.processor;




import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.asp.common.Message;
import com.asp.common.redis.MetaDataImpl;
import com.asp.common.redis.Metadata;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component("Processor1")
public class Processor1 implements TaskProcessor {

	private String Monitor;
	
	private static final Logger LOGGER = Logger
			.getLogger(Processor1.class);
	@Autowired
	MetaDataImpl metaDataImpl;
	Long id =(long) 1.0;
	
	public String getMonitor() {
		return Monitor;
	}

	public void setMonitor(String monitor) {
		Monitor = monitor;
	}

	public Processor1(String monitor) {
		super();
		Monitor = monitor;
	}

	public Processor1() {

	}

	@Override
	public void execute(Message message) {
		// TODO Auto-generated method stub
		JsonObject jsonObject = new JsonParser().parse(message.getParamsJson()).getAsJsonObject();
		Metadata metadata = new Metadata(id++,message.getUserName(),message.getGroupCode(),message.getMessageType());
		LOGGER.info("From Desktop, Monitor feature  = "+jsonObject.get("monitor").toString());
		ModelMapper modelMapper = new ModelMapper();
        Metadata metaData = modelMapper.map(metadata, Metadata.class);
        Boolean result = metaDataImpl.saveMetadata(metaData);
        if(result) {
        	LOGGER.info("Desktop metadata saved to cache");
        	LOGGER.info("Now retreiving from cache");
        	Metadata m = metaDataImpl.findByName(metaData.getUserName());
        	LOGGER.info("MetaData retrived from cache "+m);
        }
        else {
        	LOGGER.info("Something went wrong ..Desktop");
        }
                
		
	}

	

}
