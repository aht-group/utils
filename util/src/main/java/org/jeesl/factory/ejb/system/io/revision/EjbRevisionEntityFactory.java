package org.jeesl.factory.ejb.system.io.revision;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.factory.ejb.system.status.EjbDescriptionFactory;
import org.jeesl.factory.ejb.system.status.EjbLangFactory;
import org.jeesl.factory.ejb.util.EjbPositionFactory;
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
import org.jeesl.model.xml.system.revision.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbRevisionEntityFactory<L extends JeeslLang,D extends JeeslDescription,
									RC extends JeeslRevisionCategory<L,D,RC,?>,
									RV extends JeeslRevisionView<L,D,RVM>,
									RVM extends JeeslRevisionViewMapping<RV,RE,REM>,
									
									RE extends JeeslRevisionEntity<L,D,RC,REM,RA,ERD>,
									REM extends JeeslRevisionEntityMapping<?,?,RE>,
									RA extends JeeslRevisionAttribute<L,D,RE,RER,RAT>, RER extends JeeslStatus<RER,L,D>,
									RAT extends JeeslStatus<RAT,L,D>,
									ERD extends JeeslRevisionDiagram<L,D,RC>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbRevisionEntityFactory.class);
	
	final Class<RE> cEntity;
	
	private EjbLangFactory<L> efLang;
	private EjbDescriptionFactory<D> efDescription;
    
	public EjbRevisionEntityFactory(final Class<L> cL,final Class<D> cD,final Class<RE> cEntity)
	{       
        this.cEntity = cEntity;
		efLang = EjbLangFactory.factory(cL);
		efDescription = EjbDescriptionFactory.factory(cD);
	}
	
	public RE build(RC category, Entity xml)
	{
		RE ejb = build(category,new ArrayList<RE>());
		ejb.setCode(xml.getCode());
		ejb.setPosition(xml.getPosition());
		try
		{
			ejb.setName(efLang.getLangMap(xml.getLangs()));
			ejb.setDescription(efDescription.create(xml.getDescriptions()));
		}
		catch (JeeslConstraintViolationException e) {e.printStackTrace();}
		return ejb;
	}
    
	public RE build(RC category, List<RE> list)
	{
		RE ejb = null;
		try
		{
			ejb = cEntity.newInstance();
			ejb.setCategory(category);
			ejb.setVisible(true);
			ejb.setTimeseries(false);
			ejb.setDocumentation(false);
			EjbPositionFactory.next(ejb, list);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public void applyValues(RE ejb, Entity xml)
	{
		if(xml.isSetRemark()){ejb.setDeveloperInfo(xml.getRemark().getValue());}
		else{ejb.setDeveloperInfo(null);}
		
		ejb.setPosition(xml.getPosition());
	}
	
	public List<ERD> toDiagrams(List<RE> entities)
	{
		Set<ERD> sDiagrams = new HashSet<>();
		for(RE re : entities) {if(re.getDiagram()!=null && !sDiagrams.contains(re.getDiagram())) {sDiagrams.add(re.getDiagram());}}
		return new ArrayList<ERD>(sDiagrams);
	}
}