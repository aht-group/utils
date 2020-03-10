package org.jeesl.client.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.jeesl.model.json.system.io.ssi.docker.JsonDockerContainer;
import org.jeesl.model.json.system.io.ssi.docker.JsonDockerContainerPorts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.exlp.util.io.LoggerInit;

public class JeeslCurlDocker 
{	
	final static Logger logger = LoggerFactory.getLogger(JeeslCurlDocker.class);
	
	
	public JeeslCurlDocker(){}
//	
	public String[] xGet(String url) 
	{
		String[] stringGet = {"curl", "--unix-socket", "/var/run/docker.sock", "-X", "GET", url, "-H", "accept: application/json", "-H", "content-type: application/json"};
		return stringGet;
	}
	
	public String[] xPut(String url, String postSubmission)
	{
		String[] stringPut = {"curl", "--unix-socket", "/var/run/docker.sock", "-X", "GET", url, "-H", "accept: application/json", "-H", "content-type: application/json"};
		return stringPut;
	}

	private String runProcess(String[] strings) throws IOException, InterruptedException 
	{
		ProcessBuilder ps = new ProcessBuilder(strings);
		Process pr = ps.start();
		pr.waitFor();

		BufferedReader reader2 = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line2 = "";	
		
		while ((line2 = reader2.readLine()) != null) {sb.append(line2 + "\n");}
		
		return sb.toString();
	}

	public static void main(String[] args) 
	{		
		JeeslCurlDocker jCD = new JeeslCurlDocker();
		
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
		loggerInit.addAltPath("jeesl/client/config");
		loggerInit.init();
		
		ObjectMapper objectMapper = new ObjectMapper();
		try 
		{
			List<JsonDockerContainer> jDc = objectMapper.readValue(jCD.runProcess(jCD.xGet("http://v1.40/containers/json?all")), new TypeReference<List<JsonDockerContainer>>(){});
			logger.info("Id:  " +jDc.get(0).getId());
			logger.info("Image:  " +jDc.get(0).getImage());
			logger.info("State:  " +jDc.get(0).getState());
			logger.info("Status:  " +jDc.get(0).getStatus());
			
			List<JsonDockerContainerPorts> ports = jDc.get(0).getPorts();
			for (JsonDockerContainerPorts p : ports)
			{
				logger.info("IP: "+p.getIp());
				logger.info("PrivatePort: "+p.getPrivatePort());
				logger.info("PublicPort: "+p.getPublicPort());
				logger.info("Type: "+p.getType());
			}
			
		} catch (IOException | IndexOutOfBoundsException | InterruptedException e1) {logger.info("There are no running containers!");}
		
		try {logger.info(jCD.runProcess(jCD.xGet("http://v1.40/containers/json?all")));} catch (IOException | InterruptedException e) {}
	
//		String postSubmissionJSon = "{\"image\":\"hello-world\"}";

	}
} 