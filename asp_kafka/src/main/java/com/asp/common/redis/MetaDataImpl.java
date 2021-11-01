package com.asp.common.redis;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class MetaDataImpl implements MetaDataDao{
	
	@Autowired
	RedisTemplate redisTemplate;
	private static final String KEY = "Metadata";

	@Override
	public Boolean saveMetadata(Metadata metadata) {
		// TODO Auto-generated method stub
		try {
            Map userHash = new ObjectMapper().convertValue(metadata, Map.class);
            redisTemplate.opsForHash().put(KEY, metadata.getUserName(), userHash);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public Metadata findByName(String userName) {
		// TODO Auto-generated method stub
		Map userMap = (Map) redisTemplate.opsForHash().get(KEY, userName);
        Metadata metadata = new ObjectMapper().convertValue(userMap, Metadata.class);
        return metadata;
		
	}

}
