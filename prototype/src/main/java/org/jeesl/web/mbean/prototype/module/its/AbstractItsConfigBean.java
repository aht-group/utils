package org.jeesl.web.mbean.prototype.module.its;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslItsFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.factory.builder.module.ItsFactoryBuilder;
import org.jeesl.factory.ejb.module.its.EjbItsConfigFactory;
import org.jeesl.interfaces.model.module.its.JeeslItsIssue;
import org.jeesl.interfaces.model.module.its.JeeslItsIssueStatus;
import org.jeesl.interfaces.model.module.its.config.JeeslItsConfig;
import org.jeesl.interfaces.model.module.its.config.JeeslItsConfigOption;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractItsConfigBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
										R extends JeeslMcsRealm<L,D,R,?>, RREF extends EjbWithId,
										C extends JeeslItsConfig<L,D,R,O>,
										O extends JeeslItsConfigOption<L,D,O,?>,
										I extends JeeslItsIssue<R,I>,
										STATUS extends JeeslItsIssueStatus<L,D,R,STATUS,?>,
										U extends JeeslSimpleUser>
//					extends AbstractAdminBean<L,D,LOC>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractItsConfigBean.class);
	
	protected final ItsFactoryBuilder<L,D,R,C,O,I,STATUS> fbIts;
	
	protected JeeslItsFacade<L,D,R,C,O,I,STATUS> fIts;
	
	private List<C> configs; public List<C> getConfigs() {return configs;}

	private R realm;
    private RREF rref;
    private C config; public C getConfig() {return config;} public void setConfig(C config) {this.config = config;}

	public AbstractItsConfigBean(ItsFactoryBuilder<L,D,R,C,O,I,STATUS> fbIts)
	{
//		super(fbAsset.getClassL(),fbAsset.getClassD());
		this.fbIts=fbIts;
	}
	
	protected void postConstructItsOption(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									JeeslItsFacade<L,D,R,C,O,I,STATUS> fIts,
									R realm)
	{
//		super.initJeeslAdmin(bTranslation,bMessage);
		this.fIts=fIts;
		this.realm=realm;
	}
	
	protected void updateRealmReference(RREF rref
										,Class<C> cC, Class<O> cO, EjbItsConfigFactory<R,C,O> efConfig
			)
	{
		this.rref=rref;
		configs = fIts.all(cC,realm,rref);
		Map<O,C> map = efConfig.toMapOption(configs);
		for(O o : fIts.all(cO))
		{
			if(!map.containsKey(o))
			{
				try
				{
					C c = efConfig.build(realm,rref,o);
					configs.add(fIts.save(c));
				}
				catch (JeeslConstraintViolationException | JeeslLockingException e) {e.printStackTrace();}
			}
			logger.info(o.toString());
		}
	}
	
	public void selectConfig()
	{
		logger.info(AbstractLogMessage.selectEntity(config));
	}
}