package org.jeesl.web.mbean.prototype.module.mdc;

import java.io.Serializable;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.factory.builder.module.MdcFactoryBuilder;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.web.mbean.prototype.system.AbstractAdminBean;
import org.openfuxml.content.ofx.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractMdcBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
								R extends JeeslTenantRealm<L,D,R,?>, RREF extends EjbWithId
								
								>
					extends AbstractAdminBean<L,D,LOC>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractMdcBean.class);
	
	protected final MdcFactoryBuilder<L,D,LOC,R> fbMdc;
//
//	protected JeeslHdFacade<L,D,LOC,R,TICKET,CAT,STATUS,EVENT,TYPE,LEVEL,PRIORITY,MSG,M,MT,FAQ,SCOPE,FGA,DOC,SEC,USER> fHd;
	
	
	
	protected R realm;
	protected RREF rref;
	
	
	protected Section ofxUser; public Section getOfxUser() {return ofxUser;}
	
	public AbstractMdcBean(MdcFactoryBuilder<L,D,LOC,R> fbMdc)
	{
		super(fbMdc.getClassL(),fbMdc.getClassD());
		this.fbMdc=fbMdc;
	}

	protected void postConstructMdc(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									
									R realm)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
//		this.fHd=fHd;
		this.realm=realm;
	}
	
	protected void updateRealmReference(RREF rref)
	{
		this.rref=rref;
	
		updatedRealmReference();
	}
	protected abstract void updatedRealmReference();
	

}