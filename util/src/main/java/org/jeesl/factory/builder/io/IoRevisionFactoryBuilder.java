package org.jeesl.factory.builder.io;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.io.revision.EjbRevisionAttributeFactory;
import org.jeesl.factory.ejb.system.io.revision.EjbRevisionDiagramFactory;
import org.jeesl.factory.ejb.system.io.revision.EjbRevisionEntityFactory;
import org.jeesl.factory.ejb.system.io.revision.EjbRevisionMappingEntityFactory;
import org.jeesl.factory.ejb.system.io.revision.EjbRevisionMappingViewFactory;
import org.jeesl.factory.ejb.system.io.revision.EjbRevisionScopeFactory;
import org.jeesl.factory.ejb.system.io.revision.EjbRevisionViewFactory;
import org.jeesl.factory.xml.system.io.revision.XmlDiagramFactory;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionScope;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionCategory;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionView;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionViewMapping;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.system.io.revision.er.JeeslRevisionDiagram;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.model.xml.jeesl.QueryRevision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IoRevisionFactoryBuilder<L extends JeeslLang, D extends JeeslDescription,
									RC extends JeeslRevisionCategory<L,D,RC,?>,	
									RV extends JeeslRevisionView<L,D,RVM>,
									RVM extends JeeslRevisionViewMapping<RV,RE,REM>,
									RS extends JeeslRevisionScope<L,D,RC,RA>,
									RST extends JeeslStatus<RST,L,D>,
									RE extends JeeslRevisionEntity<L,D,RC,REM,RA,ERD>,
									REM extends JeeslRevisionEntityMapping<RS,RST,RE>,
									RA extends JeeslRevisionAttribute<L,D,RE,RER,RAT>,
									RER extends JeeslStatus<RER,L,D>,
									RAT extends JeeslStatus<RAT,L,D>,
									ERD extends JeeslRevisionDiagram<L,D,RC>
>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(IoRevisionFactoryBuilder.class);

	private final Class<RC> cCategory; public Class<RC> getClassCategory(){return cCategory;}
	private final Class<RV> cView; public Class<RV> getClassView(){return cView;}
	private final Class<RVM> cViewMapping; public Class<RVM> getClassViewMapping(){return cViewMapping;}
	private final Class<RS> cScope; public Class<RS> getClassScope(){return cScope;}
	private final Class<RST> cScopeType; public Class<RST> getClassScopeType(){return cScopeType;}
	private final Class<RE> cEntity; public Class<RE> getClassEntity(){return cEntity;}
	private final Class<REM> cMappingEntity; public Class<REM> getClassEntityMapping(){return cMappingEntity;}
	private final Class<RA> cAttribute; public Class<RA> getClassAttribute(){return cAttribute;}
	private final Class<RER> cRelation; public Class<RER> getClassRelation(){return cRelation;}
	private final Class<RAT> cRat; public Class<RAT> getClassAttributeType(){return cRat;}
	private final Class<ERD> cErd; public Class<ERD> getClassDiagram(){return cErd;}
    
	public IoRevisionFactoryBuilder(final Class<L> cL, final Class<D> cD,
									Class<RC> cCategory,
									Class<RV> cView,
									Class<RVM> cViewMapping,
									Class<RS> cScope,
									Class<RST> cScopeType,
									Class<RE> cEntity,
									Class<REM> cMappingEntity,
									Class<RA> cAttribute,
									final Class<RER> cRelation,
									Class<RAT> cRat,
									final Class<ERD> cErd)
	{
		super(cL,cD);
		this.cCategory=cCategory;
		this.cView=cView;
		this.cViewMapping=cViewMapping;
		this.cScope=cScope;
		this.cScopeType=cScopeType;
		this.cEntity=cEntity;
		this.cMappingEntity=cMappingEntity;
		this.cAttribute=cAttribute;
		this.cRelation=cRelation;
		this.cRat=cRat;
		this.cErd=cErd;
	}
	
	public EjbRevisionViewFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT> ejbView()
	{
		return new EjbRevisionViewFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT>(cView);
	}
	
	public EjbRevisionMappingViewFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT> ejbMappingView()
	{
		return new EjbRevisionMappingViewFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT>(cViewMapping);
	}
	
	public EjbRevisionScopeFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT> ejbScope()
	{
		return new EjbRevisionScopeFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT>(cScope);
	}
	
	public EjbRevisionEntityFactory<L,D,RC,RV,RVM,RE,REM,RA,RER,RAT,ERD> ejbEntity() {return new EjbRevisionEntityFactory<>(cL,cD,cEntity);}
	
	public EjbRevisionMappingEntityFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT> ejbMappingEntity()
	{
		return new EjbRevisionMappingEntityFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT>(cMappingEntity);
	}
	
	public EjbRevisionAttributeFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT> ejbAttribute()
	{
		return new EjbRevisionAttributeFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT>(cAttribute);
	}
	
	public EjbRevisionDiagramFactory<L,D,RC,ERD> ejbDiagram(){return new EjbRevisionDiagramFactory<>(cErd);}
	
	public XmlDiagramFactory<L,D,RC,ERD> xmlDiagram(QueryRevision q){return new XmlDiagramFactory<>(q);}
}