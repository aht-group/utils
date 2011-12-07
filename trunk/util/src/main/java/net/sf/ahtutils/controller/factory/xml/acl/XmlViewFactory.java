package net.sf.ahtutils.controller.factory.xml.acl;

import net.sf.ahtutils.controller.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.controller.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.View;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlViewFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlViewFactory.class);
		
	private View qUc;
	private String lang;
	
	public XmlViewFactory(View qUc, String lang)
	{
		this.qUc=qUc;
		this.lang=lang;
	}
	
	public <L extends UtilsLang,D extends UtilsDescription,CU extends UtilsAclCategoryUsecase<L,D,CU,U>,U extends UtilsAclView<L,D,CU,U>>
		View getUsecase(U usecase)
	{
		View xml = new View();
		
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