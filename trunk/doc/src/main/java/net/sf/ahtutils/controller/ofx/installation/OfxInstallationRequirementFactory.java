package net.sf.ahtutils.controller.ofx.installation;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.util.OfxMultilangFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxInstallationRequirementFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxInstallationRequirementFactory.class);

    private OfxMultilangFilter multiLangFilter;

    private Section section;

	public OfxInstallationRequirementFactory(String lang)
	{
        multiLangFilter = new OfxMultilangFilter(lang);

        try
        {
            section = JaxbUtil.loadJAXB("ofx.aht-utils/installation/requirements.xml", Section.class);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
	}

    public Section hardware()
    {
        section = multiLangFilter.filterLang(section);

        return section;
    }
}