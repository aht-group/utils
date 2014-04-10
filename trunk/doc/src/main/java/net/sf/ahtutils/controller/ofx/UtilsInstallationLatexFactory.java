package net.sf.ahtutils.controller.ofx;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.exlp.util.io.txt.ExlpTxtWriter;
import net.sf.exlp.util.xml.JaxbUtil;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.renderer.latex.content.text.LatexCommentRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.renderer.util.OfxContentDebugger;
import org.openfuxml.util.filter.OfxLangFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsInstallationLatexFactory
{
	final static Logger logger = LoggerFactory.getLogger(UtilsInstallationLatexFactory.class);
	
	public static enum Type{JAVA,JBOSS,MAVEN,MYSQL,OWNCLOUD,POSTGRES,R};
	private Map<Type,String> map;
	
	protected OfxLangFilter multiLangFilter;
	
	public UtilsInstallationLatexFactory(String lang)
	{
		multiLangFilter = new OfxLangFilter(lang);
		
		map = new Hashtable<Type,String>();
        map.put(Type.JAVA, "ofx.aht-utils/installation/java.xml");
        map.put(Type.JBOSS, "ofx.aht-utils/installation/jboss.xml");
        map.put(Type.MAVEN, "ofx.aht-utils/installation/maven.xml");
        map.put(Type.MYSQL, "ofx.aht-utils/installation/mysql.xml");
        map.put(Type.OWNCLOUD, "ofx.aht-utils/installation/ownCloud.xml");
        map.put(Type.POSTGRES, "ofx.aht-utils/installation/postgres.xml");
		map.put(Type.R, "ofx.aht-utils/installation/r.xml");
	}

    public String getResource(Type type)
    {
        return map.get(type);
    }
	
	public void renderLatex(Type type, File dstFile) throws OfxAuthoringException
	{
		logger.info("Processing "+map.get(type));
        Section section = loadSection(type);
        renderLatex(section,dstFile);
	}

    private Section loadSection(Type type)
    {
        Section section = null;
        try
        {
            section = JaxbUtil.loadJAXB(getResource(type), Section.class);
            section = multiLangFilter.filterLang(section);
        }
        catch (FileNotFoundException e)
        {
            logger.error("This should not happen because a junit Tests verifies the existence of the file!");
            logger.error(e.getMessage());
        }
        JaxbUtil.debug(section);
        return section;
    }
	
	public void renderLatex(Section section, File dstFile) throws OfxAuthoringException
    {
        LatexSectionRenderer latexSectionFactory = new LatexSectionRenderer(1,new LatexPreamble());
		latexSectionFactory.render(section);
		List<String> content = new ArrayList<String>();
		content.addAll(LatexCommentRenderer.comment("Do no modify this file, it is automatically generated!",true));
		content.addAll(latexSectionFactory.getContent());
		
		OfxContentDebugger.debug(content);

		this.write(content, dstFile);
	}



    protected void write(List<String> content, File f)
    {
        logger.info("Writing to "+f.getAbsolutePath());
        ExlpTxtWriter writer = new ExlpTxtWriter();
        writer.add(content);
        writer.writeFile(f);
    }
}