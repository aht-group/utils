package net.sf.ahtutils.controller.factory.html;

import java.io.File;
import java.io.IOException;

import net.sf.ahtutils.controller.factory.AbstractFreemarkerFileFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateException;

@SuppressWarnings({"unchecked"})
public class CssGridBuilder extends AbstractFreemarkerFileFactory
{
	final static Logger logger = LoggerFactory.getLogger(CssGridBuilder.class);
		
	private File resourceDir;

	public CssGridBuilder(File fTmpDir, File resourceDir)
	{
		super(fTmpDir);
		this.resourceDir=resourceDir;
	}

	public void buildCss()
	{
		for(int slot=30;slot<=100;slot=slot+1)
		{
			for(int gutter=1;gutter<=5;gutter++)
			{
				buildCss(slot, gutter);
			}
		}
	}
	
	public void buildVcsTestFile(File vcsFile)
	{
		fillModel(70,5);
		try
		{
			this.createFile(vcsFile, "html.ahtutils-maven/grid.ftl");
		}
		catch (IOException e) {e.printStackTrace();}
		catch (TemplateException e) {e.printStackTrace();}
	}
	
	protected void buildCss(int slot, int gutter)
	{
		fillModel(slot,gutter);
		try
		{
			File fResource = new File(resourceDir,"grid-"+slot+"-"+gutter+".css");
			this.createFile(fResource, "html.ahtutils-maven/grid.ftl");
		}
		catch (IOException e) {e.printStackTrace();}
		catch (TemplateException e) {e.printStackTrace();}
	}
	
	private void fillModel(int slot, int gutter)
	{
		logger.debug("fillModel for slot="+slot+" gutter="+gutter);
		freemarkerNodeModel.clear();
		
		freemarkerNodeModel.put("width", ""+((slot+(2*gutter))*12));
		freemarkerNodeModel.put("gutter", ""+gutter);
		freemarkerNodeModel.put("doublegutter", ""+gutter*2);
		
		createSlots(slot, gutter);
	}
	
	private void createSlots(int slot, int gutter)
	{
		int margin = gutter*2;
		for(int i=1;i<=12;i++)
		{
			int px = i*slot;
			px = px + (i-1)*margin;
			freemarkerNodeModel.put("slot"+i, ""+px);
		}
	}
}