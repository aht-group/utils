package net.sf.ahtutils.controller.factory.xml.acl;

import net.sf.ahtutils.controller.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.controller.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclUsecase;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.Usecase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XmlUsecaseFactory
{
	static Log logger = LogFactory.getLog(XmlUsecaseFactory.class);
		
	private Usecase qUc;
	private String lang;
	
	public XmlUsecaseFactory(Usecase qUc, String lang)
	{
		this.qUc=qUc;
		this.lang=lang;
	}
	
	public <L extends UtilsLang,D extends UtilsDescription,CU extends UtilsAclCategoryUsecase<L,D,CU,U>,U extends UtilsAclUsecase<L,D,CU,U>>
		Usecase getUsecase(U usecase)
	{
		Usecase xml = new Usecase();
		
		if(qUc.isSetCode()){xml.setCode(usecase.getCode());}
		
		if(qUc.isSetLangs())
		{
			XmlLangsFactory f = new XmlLangsFactory(qUc.getLangs());
			xml.setLangs(f.getUtilsLangs(usecase.getName()));
		}
		
		if(qUc.isSetDescriptions())
		{
			XmlDescriptionsFactory f = new XmlDescriptionsFactory(qUc.getDescriptions());
			xml.setDescriptions(f.create(usecase.getDescription()));
		}
		
		return xml;
	}
}