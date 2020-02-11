package org.jeesl.factory.xml.system.io.mail;

import net.sf.ahtutils.interfaces.model.system.mail.UtilsMailTracker;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.exlp.util.DateUtil;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.model.xml.system.io.mail.Tracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlTrackerFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlEmailAddressFactory.class);
	
	private Tracker q;
	
	public XmlTrackerFactory(Tracker q)
	{
		this.q=q;
	}
	
    public <T extends UtilsMailTracker<S,L,U,D>, S extends JeeslStatus<S,L,D>, L extends JeeslLang, U extends EjbWithId,D extends JeeslDescription>
    	Tracker create(T ejb)
    {
    	Tracker xml = new Tracker();
    	
    	if(q.isSetId()){xml.setId(ejb.getId());}
    	if(q.isSetRefId()){xml.setRefId(ejb.getRefId());}
    	if(q.isSetType()){xml.setType(ejb.getType().getCode());}
    	if(q.isSetCreated()){xml.setCreated(DateUtil.getXmlGc4D(ejb.getRecordCreated()));}
    	if(q.isSetSent()){xml.setSent(DateUtil.getXmlGc4D(ejb.getRecordSent()));}
    	if(q.isSetRetryCounter()){xml.setRetryCounter(ejb.getRetryCounter());}
    	
    	return xml;
    }
}