package net.sf.ahtutils.controller.factory.xml.acl;

import java.util.List;

import net.sf.ahtutils.model.interfaces.acl.UtilsAclCategoryUsecase;
import net.sf.ahtutils.model.interfaces.acl.UtilsAclView;
import net.sf.ahtutils.xml.access.Views;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlViewsFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlViewsFactory.class);
		
	private Views qUcs;
	private String lang;
	
	public XmlViewsFactory(Views qUcs, String lang)
	{
		this.qUcs=qUcs;
		this.lang=lang;
	}
	
	public <L extends JeeslLang,D extends JeeslDescription,CU extends UtilsAclCategoryUsecase<L,D,CU,U>,U extends UtilsAclView<L,D,CU,U>>
		Views getUsecases(List<U> listUsecases)
	{
		Views ucs = new Views();
		
		if(qUcs.isSetView())
		{
			XmlViewFactory f = new XmlViewFactory(qUcs.getView().get(0),lang);
			for(U aclUsecase : listUsecases)
			{
				ucs.getView().add(f.getUsecase(aclUsecase));
			}
		}
		
		return ucs;
	}
	
	public static Views build()
	{
		return new Views();
	}
}