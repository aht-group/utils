package org.jeesl.client.app;

import java.io.FileNotFoundException;

import javax.naming.NamingException;

import org.apache.commons.cli.Option;
import org.jeesl.JeeslBootstrap;
import org.jeesl.api.rest.system.io.mail.JeeslIoMailRest;
import org.jeesl.exception.processing.UtilsConfigurationException;
import org.jeesl.factory.xml.system.io.ssi.XmlSystemFactory;
import org.jeesl.factory.xml.system.io.ssi.docker.XmlContainerFactory;
import org.jeesl.factory.xml.system.io.ssi.docker.XmlDockerFactory;
import org.jeesl.mail.smtp.TextMailSender;
import org.jeesl.model.xml.system.io.ssi.Docker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.util.cli.UtilsCliOption;
import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.xml.JaxbUtil;

public class JeeslDocker
{
	final static Logger logger = LoggerFactory.getLogger(JeeslDocker.class);
	
	public JeeslDocker()
	{

	}
	
	private void collectInfos()
	{
		Docker docker = XmlDockerFactory.build();
		docker.setSystem(XmlSystemFactory.build("jeesl"));
		docker.getContainer().add(XmlContainerFactory.build("pg11dev","ok"));
		
		JaxbUtil.info(docker);
	}

	
	public static void main(String args[]) throws FileNotFoundException, UtilsConfigurationException, NamingException, ExlpConfigurationException
	{
		JeeslBootstrap.init();
		JeeslDocker docker = new JeeslDocker();
		
		docker.collectInfos();
	}
}