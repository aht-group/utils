package org.jeesl.factory.xml.system.status;

import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.model.xml.module.ts.Statistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlStatisticsFactory<S extends JeeslStatus<S,L,D>,L extends JeeslLang, D extends JeeslDescription>
{
	final static Logger logger = LoggerFactory.getLogger(XmlStatisticsFactory.class);

	private String localeCode;
	private Statistic q;

	public XmlStatisticsFactory(Statistic q){this(null,q);}
	public XmlStatisticsFactory(String localeCode,Statistic q)
	{
		this.localeCode=localeCode;
		this.q=q;
	}

	public static <E extends Enum<E>> Statistic build(E code){return build(code.toString());}
	public static <E extends Enum<E>> Statistic build(String code){return build(code.toString(),null);}
	public static Statistic build(String code,String label)
	{
		Statistic xml = new Statistic();
		xml.setCode(code);
		return xml;
	}
}