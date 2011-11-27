package net.sf.ahtutils.xml.xpath.access;

import net.sf.ahtutils.test.AbstractXmlTest;
import net.sf.ahtutils.xml.access.AclContainer;
import net.sf.ahtutils.xml.access.Role;
import net.sf.ahtutils.xml.access.RoleAutoAssign;
import net.sf.ahtutils.xml.xpath.AccessXpath;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXPathAccessAutoAssign extends AbstractXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXPathAccessAutoAssign.class);
    
	private AclContainer aclContainer;
	private final String code = "code";
	
	@Before
	public void iniAclContainer()
	{
		RoleAutoAssign raa = new RoleAutoAssign();
		raa.setCode(code);
		
		RoleAutoAssign.Add add = new RoleAutoAssign.Add();
		Role r1 = new Role();r1.setCode("r1");add.getRole().add(r1);
		Role r2 = new Role();r2.setCode("r2");add.getRole().add(r2);
		
		RoleAutoAssign.Rm rm = new RoleAutoAssign.Rm();
		rm.setImmediate(false);
		Role r3 = new Role();r3.setCode("r3");rm.getRole().add(r3);
		Role r4 = new Role();r4.setCode("r4");rm.getRole().add(r4);
		
		raa.setAdd(add);
		raa.setRm(rm);
		
		aclContainer = new AclContainer();
		aclContainer.getRoleAutoAssign().add(raa);
	}
	
	@Test
	public void find() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		RoleAutoAssign test = AccessXpath.getAutoAssign(aclContainer, code);
	    Assert.assertEquals(code,test.getCode());
	}

	@Test(expected=ExlpXpathNotFoundException.class)
	public void testNotFound() throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		AccessXpath.getAutoAssign(aclContainer, "-1");
	}
}