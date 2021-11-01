package com.asp.common.redis;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MetaDataRepository extends CrudRepository<Metadata, Long>{
	
	public List<Metadata> findByName(String userName) ;

}
