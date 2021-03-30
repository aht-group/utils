package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.mdc.EjbMdcActivityFactory;
import org.jeesl.interfaces.model.module.attribute.JeeslAttributeSet;
import org.jeesl.interfaces.model.module.mdc.activity.JeeslMdcActivity;
import org.jeesl.interfaces.model.module.mdc.activity.JeeslMdcScope;
import org.jeesl.interfaces.model.module.mdc.activity.JeeslMdcStatus;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MdcFactoryBuilder<L extends JeeslLang,D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
							R extends JeeslTenantRealm<L,D,R,?>,
							ACTIVITY extends JeeslMdcActivity<R,SCOPE,STATUS,AS>,
							SCOPE extends JeeslMdcScope<L,D,R,SCOPE,?>,
							STATUS extends JeeslMdcStatus<L,D,STATUS,?>,
							AS extends JeeslAttributeSet<?,?,?,?>
							>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(MdcFactoryBuilder.class);
	
	private final Class<ACTIVITY> cActivity; public Class<ACTIVITY> getClassActivity() {return cActivity;}
	private final Class<SCOPE> cScope; public Class<SCOPE> getClassScope() {return cScope;}
	private final Class<STATUS> cStatus; public Class<STATUS> getClassStatus() {return cStatus;}
	
	private final Class<AS> cAttributeSet; public Class<AS> getClassAttributeSet() {return cAttributeSet;}

	public MdcFactoryBuilder(final Class<L> cL,final Class<D> cD,
							final Class<ACTIVITY> cActivity,
							final Class<SCOPE> cScope,
							final Class<STATUS> cStatus,
							
							final Class<AS> cAttributeSet)
	{       
		super(cL,cD);
		this.cActivity=cActivity;
		this.cScope=cScope;
		this.cStatus=cStatus;
		this.cAttributeSet=cAttributeSet;
	}

	public EjbMdcActivityFactory<R,ACTIVITY,SCOPE,STATUS,AS> ejbActivity() {return new EjbMdcActivityFactory<>(this);}
}