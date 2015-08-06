package net.sf.ahtutils.doc.latex.builder;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractUtilsDocTest;
import net.sf.ahtutils.test.AhtUtilsDocBootstrap;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;

public class TestLatexDevelopmentDocumentationBuilder extends AbstractUtilsDocTest
{
	final static Logger logger = LoggerFactory.getLogger(TestLatexDevelopmentDocumentationBuilder.class);
	
	private MultiResourceLoader mrl;
    private UtilsLatexDevelopmentDocumentationBuilder b;
	
	
	public void init()
	{	
		super.initOfx();
		mrl = new MultiResourceLoader();
        b = new UtilsLatexDevelopmentDocumentationBuilder(null,null,null,cmm,dsm);
	}

	public static void main(String args[]) throws Exception
	{
		AhtUtilsDocBootstrap.init();

        File f = new File("target","latex.tex");

        TestLatexDevelopmentDocumentationBuilder test = new TestLatexDevelopmentDocumentationBuilder();
        test.initOfx();
        test.init();
	}
}