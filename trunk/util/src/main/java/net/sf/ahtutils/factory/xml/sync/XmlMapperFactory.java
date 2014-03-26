package net.sf.ahtutils.factory.xml.sync;

import net.sf.ahtutils.xml.sync.Mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlMapperFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlMapperFactory.class);
		
	public static Mapper create(Class<?> classe, long oldId, long newId)
	{
		Mapper xml = new Mapper();
		xml.setClazz(classe.getName());
		xml.setOldId(oldId);
		xml.setNewId(newId);
		return xml;
	}
}