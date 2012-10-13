package net.sf.ahtutils.web.demo;

import java.util.Hashtable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import net.sf.ahtutils.controller.interfaces.mbean.JiraConfig;
import net.sf.ahtutils.web.mbean.util.AbstractJiraBean;

@ManagedBean
public class JiraConfigBean extends AbstractJiraBean implements JiraConfig
{
	private static final long serialVersionUID = 1L;

	public final String[] collectorKeys = {"General,Feedback"};
	
	@PostConstruct
	public void init()
	{
		jiraHost = "https://foo.bar";
		jiraScriptPath = "en_US-egflvs-123456789/123/123/1.2.3";
		
		for(String key : collectorKeys)
		{
			collectorId.put(key, "myCode");
		}
	}
}
