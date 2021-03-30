package org.jeesl.web.mbean.prototype.module.mdc;

import java.io.Serializable;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslMdcFacade;
import org.jeesl.factory.builder.module.MdcFactoryBuilder;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.module.mdc.activity.JeeslMdcActivity;
import org.jeesl.interfaces.model.module.mdc.activity.JeeslMdcScope;
import org.jeesl.interfaces.model.module.mdc.activity.JeeslMdcStatus;
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
								R extends JeeslTenantRealm<L,D,R,?>, RREF extends EjbWithId,
								ACTIVITY extends JeeslMdcActivity<R,SCOPE,STATUS,AS>,
								SCOPE extends JeeslMdcScope<L,D,R,SCOPE,?>,
								STATUS extends JeeslMdcStatus<L,D,STATUS,?>,
								
								AS extends JeeslAttributeSet<L,D,?,?>
								>
					extends AbstractAdminBean<L,D,LOC>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractMdcBean.class);
	
	protected final MdcFactoryBuilder<L,D,LOC,R,ACTIVITY,SCOPE,STATUS,AS> fbMdc;

	protected JeeslMdcFacade<L,D,R,ACTIVITY,SCOPE,STATUS> fMdc;
	
	
	
	protected R realm;
	protected RREF rref;
	
	
	protected Section ofxUser; public Section getOfxUser() {return ofxUser;}
	
	public AbstractMdcBean(MdcFactoryBuilder<L,D,LOC,R,ACTIVITY,SCOPE,STATUS,AS> fbMdc)
	{
		super(fbMdc.getClassL(),fbMdc.getClassD());
		this.fbMdc=fbMdc;
	}

	protected void postConstructMdc(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									JeeslMdcFacade<L,D,R,ACTIVITY,SCOPE,STATUS> fMdc,
									R realm)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fMdc=fMdc;
		this.realm=realm;
	}
	
	protected void updateRealmReference(RREF rref)
	{
		this.rref=rref;
	
		updatedRealmReference();
	}
	protected abstract void updatedRealmReference();
	

}