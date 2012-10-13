package net.sf.ahtutils.web.demo;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import net.sf.ahtutils.model.interfaces.UtilsProperty;
import net.sf.ahtutils.model.interfaces.mbean.JiraConfig;

@ManagedBean
public class JiraConfigBean implements JiraConfig {
	
	private UtilsProperty jiraHost;
	private UtilsProperty jiraScriptPath;
	
	@PostConstruct
	public void init()
	{
		jiraHost = new AhtUtilsProperty();
		jiraHost.setId(0);
		jiraHost.setKey("jiraHost");
		jiraHost.setValue("https://foo.bar");
		
		jiraScriptPath = new AhtUtilsProperty();
		jiraScriptPath.setId(0);
		jiraScriptPath.setKey("jiraScriptPath");
		jiraScriptPath.setValue("en_US-egflvs-123456789/123/123/1.2.3");
	}

	@Override
	public UtilsProperty getJiraHost() {
		return jiraHost;
	}

	@Override
	public UtilsProperty getJiraScriptPath() {
		return jiraScriptPath;
	}
	
	

}
