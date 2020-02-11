package org.jeesl.web.mbean.prototype.system.constraint;

import java.io.Serializable;
import java.util.Comparator;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.system.JeeslSystemConstraintFacade;
import org.jeesl.factory.builder.system.ConstraintFactoryBuilder;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraint;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintResolution;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintScope;
import org.jeesl.interfaces.model.system.constraint.algorithm.JeeslConstraintAlgorithm;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.util.comparator.ejb.system.constraint.SystemConstraintAlgorithmComparator;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSystemConstraintBean <L extends JeeslLang, D extends JeeslDescription,
													ALGCAT extends JeeslStatus<ALGCAT,L,D>,
													ALGO extends JeeslConstraintAlgorithm<L,D,ALGCAT>,
													SCOPE extends JeeslConstraintScope<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
													CONCAT extends JeeslStatus<CONCAT,L,D>,
													CONSTRAINT extends JeeslConstraint<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>,
													LEVEL extends JeeslStatus<LEVEL,L,D>,
													TYPE extends JeeslStatus<TYPE,L,D>,
													RESOLUTION extends JeeslConstraintResolution<L,D,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION>>
					extends AbstractAdminBean<L,D>
					implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractSystemConstraintBean.class);

	protected JeeslSystemConstraintFacade<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> fConstraint;
	protected final ConstraintFactoryBuilder<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> fbConstraint;

	protected final Comparator<ALGO> cpAlgorithm;
	
	public AbstractSystemConstraintBean(ConstraintFactoryBuilder<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> fbConstraint)
	{
		super(fbConstraint.getClassL(),fbConstraint.getClassD());
		this.fbConstraint=fbConstraint;
		
		cpAlgorithm = (new SystemConstraintAlgorithmComparator<ALGCAT,ALGO>()).factory(SystemConstraintAlgorithmComparator.Type.position);
	}
	
	protected void initConstraint(JeeslTranslationBean bTranslation, JeeslFacesMessageBean bMessage, JeeslSystemConstraintFacade<L,D,ALGCAT,ALGO,SCOPE,CONCAT,CONSTRAINT,LEVEL,TYPE,RESOLUTION> fConstraint)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fConstraint=fConstraint;
	}
}