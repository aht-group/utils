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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractMdcConfigBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
								R extends JeeslTenantRealm<L,D,R,?>, RREF extends EjbWithId
								
								>
					extends AbstractMdcBean<L,D,LOC,R,RREF>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractMdcConfigBean.class);



	public AbstractMdcConfigBean(MdcFactoryBuilder<L,D,LOC,R> fbMdc)
	{
		super(fbMdc);
		
		
	}

	protected void postConstructMdcConfig(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									
									
									R realm)
	{
		super.postConstructMdc(bTranslation,bMessage,realm);

	}

	@Override protected void updatedRealmReference()
	{
		reload();
	}

	private void reload()
	{
		
	}
}