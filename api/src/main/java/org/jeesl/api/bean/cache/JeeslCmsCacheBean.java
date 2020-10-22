package org.jeesl.api.bean.cache;

import java.io.Serializable;

import org.jeesl.interfaces.model.io.cms.JeeslIoCmsSection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface JeeslCmsCacheBean <S extends JeeslIoCmsSection<?,S>> extends Serializable
{
	final static Logger logger = LoggerFactory.getLogger(JeeslCmsCacheBean.class);
	
	void clearCache(S section);
}