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

@Component("Processor2")
public class Processor2 implements TaskProcessor{
	
	private static final Logger LOGGER = Logger
			.getLogger(Processor2.class);
	
	@Autowired
	MetaDataImpl metaDataImpl;
	Long id =(long) 1.0;
	
	
	private String size;
	private String processor;
	public Processor2(String size, String processor) {
		super();
		this.size = size;
		this.processor = processor;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getProcessor() {
		return processor;
	}
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	public Processor2() {
		
	}
	@Override
	public void execute(Message message) {
		// TODO Auto-generated method stub
		JsonObject jsonObject = new JsonParser().parse(message.getParamsJson()).getAsJsonObject();
		Metadata metadata = new Metadata(id++,message.getUserName(),message.getGroupCode(),message.getMessageType());
		//LOGGER.info("From Laptop size = "+jsonObject.get("size").toString()+" processor "+jsonObject.get("processor").toString());
		ModelMapper modelMapper = new ModelMapper();
        Metadata metaData = modelMapper.map(metadata, Metadata.class);
        Boolean result = metaDataImpl.saveMetadata(metaData);
        if(result) {
        	LOGGER.info("Laptop MetaData saved to cache");
        	LOGGER.info("Now retreiving from cache");
        	Metadata m = metaDataImpl.findByName(metaData.getUserName());
        	LOGGER.info("MetaData retrived from cache "+m);
        }
        else {
        	LOGGER.info("Something went wrong.. Laptop");
        }

        
	}
	
	
	

}
