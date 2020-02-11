package org.jeesl.factory.ejb.system.io.revision;

import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionCategory;
import org.jeesl.interfaces.model.system.io.revision.er.JeeslRevisionDiagram;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbRevisionDiagramFactory<L extends JeeslLang, D extends JeeslDescription,
								C extends JeeslRevisionCategory<L,D,C,?>,
								ERD extends JeeslRevisionDiagram<L,D,C>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbRevisionDiagramFactory.class);

	final Class<ERD> cErDiagram;

	public EjbRevisionDiagramFactory(final Class<ERD> cErDiagram)
	{
        this.cErDiagram = cErDiagram;
	}

	public ERD build(C category, String code, String dotGraph)
	{
		ERD ejb = null;
		try
		{
			ejb = cErDiagram.newInstance();
			ejb.setCode(code);
			ejb.setDotGraph(dotGraph);
			ejb.setDocumentation(true);
			ejb.setDotManual(false);
			ejb.setCategory(category);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}

		return ejb;
	}
}