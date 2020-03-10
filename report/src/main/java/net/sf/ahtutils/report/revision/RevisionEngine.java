package net.sf.ahtutils.report.revision;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jxpath.JXPathContext;
import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.interfaces.model.io.revision.core.JeeslRevisionCategory;
import org.jeesl.interfaces.model.io.revision.core.JeeslRevisionContainer;
import org.jeesl.interfaces.model.io.revision.core.JeeslRevisionView;
import org.jeesl.interfaces.model.io.revision.core.JeeslRevisionViewMapping;
import org.jeesl.interfaces.model.io.revision.data.JeeslRevision;
import org.jeesl.interfaces.model.io.revision.data.JeeslRevisionScope;
import org.jeesl.interfaces.model.io.revision.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.io.revision.entity.JeeslRevisionEntityMapping;
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

import net.sf.ahtutils.xml.audit.Change;

public class RevisionEngine<L extends JeeslLang,D extends JeeslDescription,
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
{
	final static Logger logger = LoggerFactory.getLogger(RevisionEngine.class);
	
	private JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,?> fRevision;
	
	private RevisionEngineScopeResolver<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,REV,C,R,V,U,A,AT,USER> resr;
	private RevisionEngineAttributeResolver<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,REV,C,R,V,U,A,AT,USER> rear;
	
	private final Class<RV> cView;
	private final Class<RS> cScope;
	private final Class<RE> cEntity;
	
	private String lang;
	private Map<String,RVM> map;
	
	private Map<RAT,DecimalFormat> mapDecimalFormatter;
	private Map<RAT,SimpleDateFormat> mapDateFormatter;
	
	public RevisionEngine(JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,?> fRevision, final Class<RV> cView, final Class<RS> cScope, final Class<RE> cEntity, final Class<RAT> cRat)
	{
		this.fRevision=fRevision;
		this.cView=cView;
		this.cScope=cScope;
		this.cEntity=cEntity;
		
		map = new HashMap<String,RVM>();
		mapDecimalFormatter = new HashMap<RAT,DecimalFormat>();
		mapDateFormatter = new HashMap<RAT,SimpleDateFormat>();
		
		buildTypes(cRat);
		
		rear = RevisionEngineFactory.attribute(mapDecimalFormatter,mapDateFormatter);
		resr = RevisionEngineFactory.scope(fRevision,rear);
	}
	
	private void buildTypes(Class<RAT> cRat)
	{
		for(RAT rat : fRevision.all(cRat))
		{
			if(rat.getCode().startsWith(JeeslRevisionAttribute.Type.number.toString())){mapDecimalFormatter.put(rat, new DecimalFormat(rat.getSymbol()));}
			else if(rat.getCode().startsWith(JeeslRevisionAttribute.Type.date.toString())){mapDateFormatter.put(rat, new SimpleDateFormat(rat.getSymbol()));}
		}
	}
	
	public void init(String lang, RV view)
	{
		this.lang=lang;
		view = fRevision.load(cView, view);
		map.clear();
		for(RVM m : view.getMaps())
		{
			m.setEntity(fRevision.load(cEntity, m.getEntity()));
			m.getEntityMapping().setScope(fRevision.load(cScope, m.getEntityMapping().getScope()));
			map.put(m.getEntity().getCode(),m);
		}
		logger.info(this.getClass().getSimpleName()+" initialized with "+map.size()+" entities");
	}
	
	public Change build(JeeslRevisionContainer<REV,?,USER> revision)
	{
		Object o = revision.getEntity();
		String key = o.getClass().getName();
		Change xml;
		boolean entityIsAvailable = map.containsKey(key);
		
		if(entityIsAvailable){xml = build(map.get(key),o);}
		else{return null;}
		xml.setAid(revision.getType().ordinal());
		return xml;
	}
	
	public Change build(RVM rvm, Object o)
	{
		logger.info(o.getClass().getSimpleName());
		JXPathContext context = JXPathContext.newContext(o);
		
		Change change = new Change();
		change.setType(rvm.getEntity().getName().get(lang).getLang());
		
		StringBuffer sb = new StringBuffer();
		for(RA attribute : rvm.getEntity().getAttributes())
		{
			if(attribute.isShowPrint())
			{
				sb.append(rear.build(lang,attribute,context));
				sb.append(" ");
			}
		}
		change.setText(sb.toString().trim());
		change.setScope(resr.build(lang,rvm,context,o));
		
		return change;
	}
}