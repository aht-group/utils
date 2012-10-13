package net.sf.ahtutils.controller.interfaces.mbean;

import java.util.Map;

public interface JiraConfig
{	
	public static enum Code{jiraHost,jiraScriptPath,jiraCollector}
	
	public String getJiraHost();
	public String getJiraScriptPath();
	public Map<String,String> getCollectorId();
}
