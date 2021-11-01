package com.asp.common.redis;

public interface MetaDataDao {
	 	public Boolean saveMetadata(Metadata metadata) ;
	    public Metadata findByName(String userName) ;

}
