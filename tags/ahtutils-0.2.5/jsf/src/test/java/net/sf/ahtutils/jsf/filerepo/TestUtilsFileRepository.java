package net.sf.ahtutils.jsf.filerepo;

import java.io.File;

import net.sf.ahtutils.test.AbstractAhtUtilsJsfTst;
import net.sf.exlp.util.io.HashUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestUtilsFileRepository extends AbstractAhtUtilsJsfTst
{
	final static Logger logger = LoggerFactory.getLogger(TestUtilsFileRepository.class);
		
	private UtilsFileRepository ufr;
	private String content;
	private String hash;
	private String filename;
	
	@Before
	public void init()
	{
		ufr = new UtilsFileRepository(fTarget);
		ufr.setFs("/");
		content = "af23df34";
		hash = HashUtil.hash(content);
		filename = rndL()+".jpg";
	}
	
	@Test
	public void getDir()
	{
		File aFile = ufr.getDir(hash);
		
		String expected = fTarget.getAbsolutePath()+"/6a/e2/f4";
		Assert.assertEquals(expected, aFile.getAbsolutePath());
	}
	
	@Test
	public void getName()
	{
		String actual = ufr.getName(filename, hash);

		String expected = hash+".jpg";
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void save()
	{
		ufr.save(filename, content.getBytes());
		File f = new File(ufr.getDir(hash),ufr.getName(filename, hash));
		logger.debug(f.getAbsolutePath());
		Assert.assertTrue(f.getParentFile().exists());
		Assert.assertTrue(f.exists());
	}
}