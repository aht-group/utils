package net.sf.ahtutils.controller.factory.java.security;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.controller.exception.AhtUtilsConfigurationException;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.View;
import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings({"rawtypes","unchecked"})
public class JavaSecurityViewRestrictorFactory
{
	final static Logger logger = LoggerFactory.getLogger(JavaSecurityViewRestrictorFactory.class);
		
	private File fJavaRestrictor;
	private String classRestrictor,viewQualifierBasePackage,classAbstractRestrictor,classPrefix;
	private Configuration freemarkerConfiguration;
	
	private Map freemarkerNodeModel;
	
	public JavaSecurityViewRestrictorFactory(File fJavaRestrictor, String classRestrictor, String classAbstractRestrictor, String viewQualifierBasePackage, String classPrefix)
	{
		this.fJavaRestrictor=fJavaRestrictor;
		this.classRestrictor=classRestrictor;
		this.classAbstractRestrictor=classAbstractRestrictor;
		this.classPrefix=classPrefix;
		this.viewQualifierBasePackage=viewQualifierBasePackage;
		
		freemarkerNodeModel = new HashMap();

		freemarkerConfiguration = new Configuration();
		freemarkerConfiguration.setClassForTemplateLoading(this.getClass(), "/");
	}
	
	public void create(String fXml) throws FileNotFoundException, AhtUtilsConfigurationException
	{
		Access access = JaxbUtil.loadJAXB(fXml, Access.class);
		create(access.getCategory());
	}

	public void create(List<Category> lCategory) throws AhtUtilsConfigurationException
	{
		checkPackageDir();
		
		freemarkerNodeModel.put("packageName", classRestrictor.substring(0,classRestrictor.lastIndexOf(".")));
		freemarkerNodeModel.put("className", classRestrictor.substring(classRestrictor.lastIndexOf(".")+1,classRestrictor.length()));
		
		freemarkerNodeModel.put("abstractImport", classAbstractRestrictor);
		freemarkerNodeModel.put("abstract", classAbstractRestrictor.substring(classAbstractRestrictor.lastIndexOf(".")+1,classAbstractRestrictor.length()));
		
		List<Map> lCodes = new ArrayList<Map>();
		for(Category category : lCategory)
		{
			if(category.isSetViews())
			{
				for(View view : category.getViews().getView())
				{
					StringBuffer sb = new StringBuffer();
					sb.append(viewQualifierBasePackage);
					sb.append(".").append(category.getCode()).append(".");
					sb.append(JavaSecurityViewIdentifierFactory.createClassName(classPrefix,view.getCode()));
					
					Map m = new HashMap();
					m.put("import", sb.toString());
					m.put("class", JavaSecurityViewIdentifierFactory.createClassName(classPrefix,view.getCode()));
					m.put("code", view.getCode());
					lCodes.add(m);
				}
			}
		}
		freemarkerNodeModel.put("codes", lCodes);
		try
		{
			createClass(null);
		}
		catch (IOException e) {e.printStackTrace();}
		catch (TemplateException e) {e.printStackTrace();}
	}
	
	protected void checkPackageDir() throws AhtUtilsConfigurationException
	{
		File fPackage = fJavaRestrictor.getParentFile();
		
		if(!fPackage.exists()){throw new AhtUtilsConfigurationException("Directory "+fPackage.getAbsolutePath()+" does not exist");}
		if(!fPackage.isDirectory()){throw new AhtUtilsConfigurationException(fPackage.getAbsolutePath()+" is not a directory");}
	}
	
	private void createClass(List<String> lIdentifier) throws IOException, TemplateException
	{
		if(fJavaRestrictor.exists()){fJavaRestrictor.delete();}
		
	
		
		Template ftl = freemarkerConfiguration.getTemplate("security.ahtutils-util/viewRestrictor.ftl","UTF-8");
		ftl.setEncoding("UTF-8");
		
		StringWriter sw = new StringWriter();
		ftl.process(freemarkerNodeModel, sw);
		sw.flush();
		
		StringIO.writeTxt(fJavaRestrictor, sw.toString());
	}

}