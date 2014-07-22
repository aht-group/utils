package net.sf.ahtutils.factory.xml.navigation;

import net.sf.ahtutils.xml.navigation.MenuItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlMenuItemFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlMenuItemFactory.class);
		
	public static MenuItem clone(MenuItem item)
	{
		MenuItem xml = new MenuItem();
		xml.setName(item.getName());
		xml.setHref(item.getHref());
		xml.setCode(item.getCode());

		return xml;
	}
}