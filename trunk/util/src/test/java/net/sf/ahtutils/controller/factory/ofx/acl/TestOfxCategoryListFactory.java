package net.sf.ahtutils.controller.factory.ofx.acl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import net.sf.ahtutils.test.AhtUtilsTstBootstrap;
import net.sf.ahtutils.xml.access.RoleCategory;
import net.sf.ahtutils.xml.access.TestXmlRoleCategory;
import net.sf.ahtutils.xml.status.TestXmlDescription;
import net.sf.ahtutils.xml.status.TestXmlLang;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.xml.content.list.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestOfxCategoryListFactory extends AbstractOfxAclFactoryTest
{
	final static Logger logger = LoggerFactory.getLogger(TestOfxCategoryListFactory.class);
	
	private OfxCategoryListFactory factory;
	
	private final String lang ="de";
	private RoleCategory rc1;
//	private RoleCategory rc2;
	private java.util.List<RoleCategory> list;
	
//	private OfxLatexRenderer parentSection;
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"listRoleCategory.xml");
		fTxt = new File(rootDir,"listRoleCategory.txt");
	}
	
	@Before
	public void init()
	{	
		factory = new OfxCategoryListFactory(lang);
		list = new ArrayList<RoleCategory>();
		rc1 = createCategory(1);list.add(rc1);
	}
	
	private RoleCategory createCategory(int id)
	{
		RoleCategory rc = TestXmlRoleCategory.create(true);
		rc.getLangs().getLang().add(TestXmlLang.create(false,lang,"Category "+id));
		rc.getDescriptions().getDescription().add(TestXmlDescription.create(false,lang,id+" This category is for testing purposes only."));
		return rc;
	}
	
	@Test
	public void testXml() throws FileNotFoundException
	{
		List actual = factory.create(list);
		saveXml(actual,fXml,false);
		List expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), List.class);
		this.assertJaxbEquals(expected, actual);
	}
	
	@Test
	public void testRenderer() throws FileNotFoundException
	{
		List actual = factory.create(list);
		
		
	}
	
	public static void main(String[] args) throws FileNotFoundException
    {
		AhtUtilsTstBootstrap.init();
		
		TestOfxCategoryListFactory.initFiles();
		TestOfxCategoryListFactory test = new TestOfxCategoryListFactory();
		test.setSaveReference(true);
		test.init();
		test.testXml();
    }
}