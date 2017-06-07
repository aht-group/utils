package org.jeesl.factory.xml.system.revision;

import org.jeesl.interfaces.model.system.security.user.JeeslRevision;
import org.jeesl.model.xml.system.revision.Revision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.DateUtil;

public class XmlRevisionFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlRevisionFactory.class);
	
	public static Revision build(JeeslRevision revision)
	{
		Revision xml = new Revision();
		xml.setRecord(DateUtil.toXmlGc(revision.getInfo().getAuditRecord()));
		
		return xml;
	}
}