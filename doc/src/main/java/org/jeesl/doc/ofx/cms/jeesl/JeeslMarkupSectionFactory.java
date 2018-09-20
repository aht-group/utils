package org.jeesl.doc.ofx.cms.jeesl;

import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsMarkup;
import org.openfuxml.content.ofx.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeslMarkupSectionFactory<M extends JeeslIoCmsMarkup<?>>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslMarkupSectionFactory.class);
	
	private final JeeslMarkupFactory ofxMarkup;
	
	public JeeslMarkupSectionFactory()
	{
		ofxMarkup = new JeeslMarkupFactory();
	}
	
	public Section build(M markup)
	{
/*
		logger.info("markup: "+(markup!=null));
		logger.info("markup.getType(): "+(markup.getType()!=null));
		logger.info("markup.getType().getCode(): "+(markup.getType().getCode()!=null));
		logger.info("markup.getContent(): "+(markup.getContent()!=null));
*/		
		return ofxMarkup.build(markup.getType().getCode(), markup.getContent());
	}
}