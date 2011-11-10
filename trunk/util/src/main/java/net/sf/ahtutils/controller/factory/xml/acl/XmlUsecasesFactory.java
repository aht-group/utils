package net.sf.ahtutils.controller.factory.xml.acl;

import java.util.List;

import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclUsecase;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.access.Usecases;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XmlUsecasesFactory
{
	static Log logger = LogFactory.getLog(XmlUsecasesFactory.class);
		
	private Usecases qUcs;
	private String lang;
	
	public XmlUsecasesFactory(Usecases qUcs, String lang)
	{
		this.qUcs=qUcs;
		this.lang=lang;
	}
	
	public <L extends UtilsLang,D extends UtilsDescription,CU extends UtilsAclCategoryUsecase<L,D,CU,U>,U extends UtilsAclUsecase<L,D,CU,U>>
		Usecases getUsecases(List<U> listUsecases)
	{
		Usecases ucs = new Usecases();
		
		if(qUcs.isSetUsecase())
		{
			XmlUsecaseFactory f = new XmlUsecaseFactory(qUcs.getUsecase().get(0),lang);
			for(U aclUsecase : listUsecases)
			{
				ucs.getUsecase().add(f.getUsecase(aclUsecase));
			}
		}
		
		return ucs;
	}
}