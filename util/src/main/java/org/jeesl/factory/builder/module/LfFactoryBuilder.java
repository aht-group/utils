package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.lf.EjbLfIndicatorFactory;
import org.jeesl.factory.ejb.module.lf.EjbLfLogFrameFactory;
import org.jeesl.factory.ejb.module.lf.EjbLfMonitoringFactory;
import org.jeesl.factory.ejb.module.lf.EjbLfTargetTimeElementFactory;
import org.jeesl.factory.ejb.module.lf.EjbLfTargetTimeGroupFactory;
import org.jeesl.interfaces.model.module.lf.JeeslLfLogframe;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicator;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicatorMonitoring;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeElement;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeGroup;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LfFactoryBuilder<L extends JeeslLang,D extends JeeslDescription,
							LF extends JeeslLfLogframe<L,D,?,?,?,?>,
							LFI extends JeeslLfIndicator<LF,?,?,TTG,LFM>,
							TTG extends JeeslLfTargetTimeGroup<L,?>,
							TTE extends JeeslLfTargetTimeElement<L,TTG>,
							LFM extends JeeslLfIndicatorMonitoring<LFI,TTG,TTE>>
extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(LfFactoryBuilder.class);
	protected final Class<TTG> cTTG; public Class<TTG> getClassTTG() {return cTTG;}
	protected final Class<TTE> cTTE; public Class<TTE> getClassTTE() {return cTTE;}
	protected final Class<LF> cLF; public Class<LF>  getClassLF() {return cLF;}
	protected final Class<LFI> cLFI; public Class<LFI>  getClassLFI() {return cLFI;}
	protected final Class<LFM> cLFM; public Class<LFM>  getClassLFM() {return cLFM;}

	public LfFactoryBuilder(final Class<L> cL,final Class<D> cD,final Class<LF> cLf, final Class<LFI> cLfI,final Class<LFM> cLfM, final Class<TTG> cTTG, final Class<TTE> cTTE)
	{
		super(cL,cD);
		this.cLF = cLf;
		this.cLFI = cLfI;
		this.cLFM = cLfM;
		this.cTTG = cTTG;
		this.cTTE = cTTE;
	}


	public EjbLfTargetTimeGroupFactory<TTG> ejbTargetTimeGroup() {return new EjbLfTargetTimeGroupFactory<>(cTTG);}

	public EjbLfTargetTimeElementFactory<TTG,TTE> ejbTargetTimeElement(){return new EjbLfTargetTimeElementFactory<>(cTTE);}

	public EjbLfLogFrameFactory<LF> ejbLfLogFrame() {return new EjbLfLogFrameFactory<>(cLF);}
	public EjbLfIndicatorFactory<LFI> ejbLfIndicator() {return new EjbLfIndicatorFactory<>(cLFI);}
	public EjbLfMonitoringFactory<LFM> ejbLfMonitoring() {return new EjbLfMonitoringFactory<>(cLFM);}

}