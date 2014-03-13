package net.sf.ahtutils.jsf.components.layout;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.svenjacobs.loremipsum.LoremIpsum;

@FacesComponent(value="net.sf.ahtutils.jsf.components.layout.LoremIpsumGenerator")
public class LoremIpsumGenerator extends UINamingContainer
{
	final static Logger logger = LoggerFactory.getLogger(LoremIpsumGenerator.class);
	
	private LoremIpsum li;
	
	public LoremIpsumGenerator()
	{
		li = new LoremIpsum();
	}
	
	public String text()
	{
		return li.getParagraphs(5);
	}
}