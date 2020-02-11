package org.jeesl.factory.xml.system.symbol;

import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicStyle;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.symbol.Size;
import net.sf.ahtutils.xml.symbol.Sizes;

public class XmlSizesFactory <L extends JeeslLang, D extends JeeslDescription,
							G extends JeeslGraphic<L,D,GT,F,FS>, GT extends JeeslStatus<GT,L,D>,
							F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends JeeslStatus<FS,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlSizesFactory.class);
		
	private Sizes q;
	
	public XmlSizesFactory(Sizes q)
	{
		this.q=q;
		
	}
	
	public Sizes build(G graphic)
	{
		Sizes xml = build();
		
		if(q.isSetSize())
		{
			xml.getSize().add(XmlSizeFactory.build(JeeslGraphicStyle.Size.outer, graphic.getSize()));
		}
		
		return xml;
	}
	
	public static Sizes build()
	{
		Sizes xml = new Sizes();
		return xml;
	}
	
	public static Sizes build(Size size)
	{
		Sizes xml = build();
		xml.getSize().add(size);
		return xml;
	}
}