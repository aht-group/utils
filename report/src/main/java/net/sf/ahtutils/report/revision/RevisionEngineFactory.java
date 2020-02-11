package net.sf.ahtutils.report.revision;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevision;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionScope;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionCategory;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionView;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionViewMapping;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RevisionEngineFactory
{
	final static Logger logger = LoggerFactory.getLogger(RevisionEngineFactory.class);
	
	public static <L extends JeeslLang,D extends JeeslDescription,
					RC extends JeeslRevisionCategory<L,D,RC,?>,
					RV extends JeeslRevisionView<L,D,RVM>,
					RVM extends JeeslRevisionViewMapping<RV,RE,REM>,
					RS extends JeeslRevisionScope<L,D,RC,RA>,
					RST extends JeeslStatus<RST,L,D>,
					RE extends JeeslRevisionEntity<L,D,RC,REM,RA,?>,
					REM extends JeeslRevisionEntityMapping<RS,RST,RE>,
					RA extends JeeslRevisionAttribute<L,D,RE,RER,RAT>,
					RER extends JeeslStatus<RER,L,D>,
					RAT extends JeeslStatus<RAT,L,D>,
					REV extends JeeslRevision,
					C extends JeeslSecurityCategory<L,D>,
					R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
					V extends JeeslSecurityView<L,D,C,R,U,A>,
					U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
					A extends JeeslSecurityAction<L,D,R,V,U,AT>,
					AT extends JeeslSecurityTemplate<L,D,C>,
					USER extends JeeslUser<R>>
		RevisionEngine<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,REV,C,R,V,U,A,AT,USER> engine(JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,?> fRevision, final Class<RV> cView, final Class<RS> cScope, final Class<RE> cEntity, final Class<RAT> cRat)
	{
		return new RevisionEngine<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,REV,C,R,V,U,A,AT,USER>(fRevision, cView, cScope, cEntity, cRat);
	}
	
	public static <L extends JeeslLang,D extends JeeslDescription,
					RC extends JeeslRevisionCategory<L,D,RC,?>,
					RV extends JeeslRevisionView<L,D,RVM>,
					RVM extends JeeslRevisionViewMapping<RV,RE,REM>,
					RS extends JeeslRevisionScope<L,D,RC,RA>,
					RST extends JeeslStatus<RST,L,D>,
					RE extends JeeslRevisionEntity<L,D,RC,REM,RA,?>,
					REM extends JeeslRevisionEntityMapping<RS,RST,RE>,
					RA extends JeeslRevisionAttribute<L,D,RE,RER,RAT>,
					RER extends JeeslStatus<RER,L,D>,
					RAT extends JeeslStatus<RAT,L,D>,
					REV extends JeeslRevision,
					C extends JeeslSecurityCategory<L,D>,
					R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
					V extends JeeslSecurityView<L,D,C,R,U,A>,
					U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
					A extends JeeslSecurityAction<L,D,R,V,U,AT>,
					AT extends JeeslSecurityTemplate<L,D,C>,
					USER extends JeeslUser<R>>
		RevisionEngineAttributeResolver<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,REV,C,R,V,U,A,AT,USER> attribute(Map<RAT,DecimalFormat> mapDecimalFormatter, Map<RAT,SimpleDateFormat> mapDateFormatter)
	{
		return new RevisionEngineAttributeResolver<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,REV,C,R,V,U,A,AT,USER>(mapDecimalFormatter,mapDateFormatter);
	}
	
	public static <L extends JeeslLang,D extends JeeslDescription,
					RC extends JeeslRevisionCategory<L,D,RC,?>,
					RV extends JeeslRevisionView<L,D,RVM>,
					RVM extends JeeslRevisionViewMapping<RV,RE,REM>,
					RS extends JeeslRevisionScope<L,D,RC,RA>,
					RST extends JeeslStatus<RST,L,D>,
					RE extends JeeslRevisionEntity<L,D,RC,REM,RA,?>,
					REM extends JeeslRevisionEntityMapping<RS,RST,RE>,
					RA extends JeeslRevisionAttribute<L,D,RE,RER,RAT>,
					RER extends JeeslStatus<RER,L,D>,
					RAT extends JeeslStatus<RAT,L,D>,
					REV extends JeeslRevision,
					C extends JeeslSecurityCategory<L,D>,
					R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
					V extends JeeslSecurityView<L,D,C,R,U,A>,
					U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
					A extends JeeslSecurityAction<L,D,R,V,U,AT>,
					AT extends JeeslSecurityTemplate<L,D,C>,
					USER extends JeeslUser<R>>
		RevisionEngineScopeResolver<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,REV,C,R,V,U,A,AT,USER> scope(JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,?> fRevision, RevisionEngineAttributeResolver<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,REV,C,R,V,U,A,AT,USER> rear)
	{
		return new RevisionEngineScopeResolver<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,REV,C,R,V,U,A,AT,USER>(fRevision,rear);
	}
}