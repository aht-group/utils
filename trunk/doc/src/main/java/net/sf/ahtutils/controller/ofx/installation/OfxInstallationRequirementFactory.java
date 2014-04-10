package net.sf.ahtutils.controller.ofx.installation;

import java.io.FileNotFoundException;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.xml.JaxbUtil;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.factory.xml.ofx.list.XmlListFactory;
import org.openfuxml.factory.xml.ofx.list.XmlListItemFactory;
import org.openfuxml.util.filter.OfxLangFilter;
import org.openfuxml.xml.content.list.List;
import org.openfuxml.xml.xpath.content.SectionXpath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxInstallationRequirementFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxInstallationRequirementFactory.class);

	
	private static String idHardware ="installation.requirement.hardware";
    private OfxLangFilter multiLangFilter;

    private Section section;

	public OfxInstallationRequirementFactory(String lang)
	{
        multiLangFilter = new OfxLangFilter(lang);

        try
        {
            section = JaxbUtil.loadJAXB("ofx.aht-utils/installation/requirements.xml", Section.class);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
	}

    public Section hardware(int ram)
    {
        section = multiLangFilter.filterLang(section);
        
        try
        {
			Section sHardware = SectionXpath.getRenderer(section, idHardware);
			
			List list = XmlListFactory.build(XmlListFactory.Ordering.unordered.toString());
			list.getItem().add(XmlListItemFactory.build(ram+" GB RAM"));
			
			sHardware.getContent().add(list);
			
			JaxbUtil.info(sHardware);
		}
        catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
        catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
        
        
        return section;
    }
}