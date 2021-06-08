package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.lf.EjbLfConfigurationFactory;
import org.jeesl.factory.ejb.module.lf.EjbLfIndicatorFactory;
import org.jeesl.factory.ejb.module.lf.EjbLfLogFrameFactory;
import org.jeesl.factory.ejb.module.lf.EjbLfMonitoringFactory;
import org.jeesl.factory.ejb.module.lf.EjbLfTargetTimeElementFactory;
import org.jeesl.factory.ejb.module.lf.EjbLfTargetTimeGroupFactory;
import org.jeesl.interfaces.model.module.lf.JeeslLfConfiguration;
import org.jeesl.interfaces.model.module.lf.JeeslLfLogframe;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicator;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicatorLevel;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicatorType;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfUnit;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfVerificationSource;
import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeElement;
import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeGroup;
import org.jeesl.interfaces.model.module.lf.time.JeeslLfTimeInterval;
import org.jeesl.interfaces.model.module.lf.value.JeeslLfValue;
import org.jeesl.interfaces.model.module.lf.value.JeeslLfValueType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LfFactoryBuilder<L extends JeeslLang,D extends JeeslDescription,R extends JeeslTenantRealm<L,D,R,?>,
							LF extends JeeslLfLogframe<L,D,R,I,IL,IT>,
							I extends JeeslLfIndicator<LF,IL,IT,IU,IV,TTG,LFM>,
							IL extends JeeslLfIndicatorLevel<L, D,R, IL, ?>,
							IT extends JeeslLfIndicatorType<L, D,R, IT, ?>,
							IU extends JeeslLfUnit<L,D,R,IU,?>,
							IV extends JeeslLfVerificationSource<L,D,R,IV,?>,
							TTG extends JeeslLfTimeGroup<L,?>,
							TTI extends JeeslLfTimeInterval<L,D,TTI,?>,
							TTE extends JeeslLfTimeElement<L,TTG>,
							LFM extends JeeslLfValue<I,VT,TTG,TTE>,
							VT extends JeeslLfValueType<L,D,R,VT,?>,
							LFC extends JeeslLfConfiguration<LF,?>>
extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(LfFactoryBuilder.class);

	private final Class<R> cRealm; public Class<R> getClassRealm() {return cRealm;}
	private final Class<TTG> cTTG; public Class<TTG> getClassTTG() {return cTTG;}
	private final Class<TTI> cTTI; public Class<TTI> getClassTTI() {return cTTI;}
	private final Class<TTE> cTTE; public Class<TTE> getClassTTE() {return cTTE;}
	private final Class<LF> cLF; public Class<LF>  getClassLF() {return cLF;}
	private final Class<I> cI; public Class<I>  getClassLFI() {return cI;}
	private final Class<LFM> cLFM; public Class<LFM>  getClassLFM() {return cLFM;}
	private final Class<LFC> cLFC; public Class<LFC>  getClassLFC() {return cLFC;}
	private final Class<IL> cIL; public Class<IL>  getClassIL() {return cIL;}
	private final Class<IT> cIT; public Class<IT>  getClassIT() {return cIT;}


	public LfFactoryBuilder(final Class<L> cL,
							final Class<D> cD,
							final Class<R> cRealm,
							final Class<LF> cLf,
							final Class<I> cI,
							final Class<IL> cIL,
							final Class<IT> cIT,
							final Class<TTG> cTTG,
							final Class<TTI> cTTI,
							final Class<TTE> cTTE,
							final Class<LFM> cLfM,
							final Class<LFC> cLFC)
	{
		super(cL,cD);
		this.cRealm = cRealm;
		this.cLF = cLf;
		this.cI = cI;
		this.cIL =cIL;
		this.cIT =cIT;
		this.cLFM = cLfM;
		this.cTTG = cTTG;
		this.cTTI = cTTI;
		this.cTTE = cTTE;
		this.cLFC = cLFC;
	}

	public EjbLfTargetTimeGroupFactory<TTG> ejbTargetTimeGroup() {return new EjbLfTargetTimeGroupFactory<>(cTTG);}

	public EjbLfTargetTimeElementFactory<TTG,TTE> ejbTargetTimeElement(){return new EjbLfTargetTimeElementFactory<>(cTTE);}

	public EjbLfLogFrameFactory<LF> ejbLfLogFrame() {return new EjbLfLogFrameFactory<>(cLF);}
	public EjbLfIndicatorFactory<I,IT,IU,IV> ejbLfIndicator() {return new EjbLfIndicatorFactory<>(cI);}
	public EjbLfMonitoringFactory<LFM> ejbLfMonitoring() {return new EjbLfMonitoringFactory<>(cLFM);}
	public EjbLfConfigurationFactory<LFC> ejbLfConfiguration() {return new EjbLfConfigurationFactory<>(cLFC);}
}