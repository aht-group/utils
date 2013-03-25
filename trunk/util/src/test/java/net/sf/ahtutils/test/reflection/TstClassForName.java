package net.sf.ahtutils.test.reflection;

import net.sf.ahtutils.xml.aht.Aht;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstClassForName 
{
	final static Logger logger = LoggerFactory.getLogger(TstClassForName.class);
	
	public static void main(String args[]) throws Exception
    {
		String clName = Aht.class.getName();
		System.out.println(clName);
		
		Class cl = Class.forName(clName);
		System.out.println(cl.getName());
    }
}