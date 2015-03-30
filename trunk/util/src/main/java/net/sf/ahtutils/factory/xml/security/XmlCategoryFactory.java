package net.sf.ahtutils.factory.xml.security;

import net.sf.ahtutils.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.xml.security.Category;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlCategoryFactory <L extends UtilsLang,D extends UtilsDescription,C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlRoleFactory.class);
		
	@SuppressWarnings("unused")
	private String lang;
	private net.sf.ahtutils.xml.security.Category q;
	
	public XmlCategoryFactory(String lang,net.sf.ahtutils.xml.security.Category q)
	{
		this.lang=lang;
		this.q=q;
	}
	
	public net.sf.ahtutils.xml.security.Category build(C category)
	{
		Category xml = new Category();
		
		if(q.isSetCode()){xml.setCode(category.getCode());}
		
		if(q.isSetLangs())
		{
			XmlLangsFactory<L> f = new XmlLangsFactory<L>(q.getLangs());
			xml.setLangs(f.getUtilsLangs(category.getName()));
		}
		
		if(q.isSetDescriptions())
		{
			XmlDescriptionsFactory f = new XmlDescriptionsFactory(q.getDescriptions());
			xml.setDescriptions(f.create(category.getDescription()));
		}
		
		return xml;
	}
}