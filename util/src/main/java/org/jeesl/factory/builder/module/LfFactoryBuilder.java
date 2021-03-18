package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.lf.EjbLfLogFrameFactory;
import org.jeesl.factory.ejb.module.lf.EjbLfTargetTimeElementFactory;
import org.jeesl.factory.ejb.module.lf.EjbLfTargetTimeGroupFactory;
import org.jeesl.interfaces.model.module.lf.JeeslLfLogframe;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeElement;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeGroup;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LfFactoryBuilder<L extends JeeslLang,D extends JeeslDescription,
							LF extends JeeslLfLogframe<L, D, ?, ?>,
							//LFL extends JeeslLfLevel<?, ?, LFL, ?>,
							//LFT extends JeeslLfType<?, ?, LFT, ?>,
							TTG extends JeeslLfTargetTimeGroup<L,?>,
							//TTI extends JeeslLfTargetTimeInterval<?,?,TTI,?>,
							TTE extends JeeslLfTargetTimeElement<L,TTG>>
extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(LfFactoryBuilder.class);
	protected final Class<TTG> cTTG; public Class<TTG> getClassTTG() {return cTTG;}
	protected final Class<TTE> cTTE; public Class<TTE> getClassTTE() {return cTTE;}
	protected final Class<LF> cLF; public Class<LF>  getClassLF() {return cLF;}




	public LfFactoryBuilder(final Class<L> cL,final Class<D> cD,final Class<LF> cLf, final Class<TTG> cTTG, final Class<TTE> cTTE)
	{
		super(cL,cD);
		this.cLF = cLf;
		//this.cLFL = cLfL;
		//this.cLFT = cLfT;
		this.cTTG = cTTG;
		//this.cTTI = cTTI;
		this.cTTE = cTTE;
	}


	public EjbLfTargetTimeGroupFactory<TTG> ejbTargetTimeGroup() {return new EjbLfTargetTimeGroupFactory<>(cTTG);}

	public EjbLfTargetTimeElementFactory<TTG,TTE> ejbTargetTimeElement(){return new EjbLfTargetTimeElementFactory<>(cTTE);}

	public EjbLfLogFrameFactory<LF> ejbLogFrame() {return new EjbLfLogFrameFactory<>(cLF);}


}