package net.sf.ahtutils.controller.factory.xml.acl;

import net.sf.ahtutils.controller.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.controller.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclUsecase;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.UsecaseCategory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlUsecaseCategoryFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlUsecaseCategoryFactory.class);
		
	private UsecaseCategory qUcc;
	private String lang;
	
	public XmlUsecaseCategoryFactory(UsecaseCategory qUcc, String lang)
	{
		this.qUcc=qUcc;
		this.lang=lang;
	}
	
	public <L extends UtilsLang,D extends UtilsDescription,CU extends UtilsAclCategoryUsecase<L,D,CU,U>,U extends UtilsAclUsecase<L,D,CU,U>>
		UsecaseCategory getUsecaseCategory(CU category)
	{
		UsecaseCategory xml = new UsecaseCategory();
		
		if(qUcc.isSetCode()){xml.setCode(category.getCode());}
		
		if(qUcc.isSetLangs())
		{
			XmlLangsFactory f = new XmlLangsFactory(qUcc.getLangs());
			xml.setLangs(f.getUtilsLangs(category.getName()));
		}
		
		if(qUcc.isSetDescriptions())
		{
			XmlDescriptionsFactory f = new XmlDescriptionsFactory(qUcc.getDescriptions());
			xml.setDescriptions(f.create(category.getDescription()));
		}
		
		if(qUcc.isSetUsecases())
		{
			XmlUsecasesFactory f = new XmlUsecasesFactory(qUcc.getUsecases(), lang);
			xml.setUsecases(f.getUsecases(category.getUsecases()));
		}
		
		return xml;
	}
}