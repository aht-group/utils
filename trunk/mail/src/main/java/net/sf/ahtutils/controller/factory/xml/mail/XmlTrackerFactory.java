package net.sf.ahtutils.controller.factory.xml.mail;

import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.mail.UtilsMailTracker;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.xml.mail.Tracker;
import net.sf.exlp.util.DateUtil;

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
	
    public <T extends UtilsMailTracker<S,L,U>, S extends UtilsStatus<L>, L extends UtilsLang, U extends EjbWithId>
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