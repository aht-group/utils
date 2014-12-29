package net.sf.ahtutils.factory.xml.text;

import net.sf.ahtutils.xml.text.Hint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlHintFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlHintFactory.class);
	
	public static Hint build(String value) {return build(null,null,value);}
	public static Hint build(String key,String value) {return build(key,null,value);}
	
	public static Hint build(String key,Integer version,String value)
	{
		Hint xml = new Hint();
		if(key!=null){xml.setKey(key);}
		if(version!=null){xml.setVersion(version);}
		xml.setValue(value);
		return xml;
	}
}