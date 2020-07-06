package org.jeesl.factory.sql.module.workflow;

import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.model.module.workflow.instance.JeeslWorkflowLink;
import org.jeesl.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlWorkflowLinkFactory
{
	final static Logger logger = LoggerFactory.getLogger(SqlWorkflowLinkFactory.class);
	
	public static <WL extends JeeslWorkflowLink<?,?>> String delete(Class<WL> c, WL link)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM ");
		try {sb.append(ReflectionUtil.toTable(c));} catch (JeeslNotFoundException e){e.printStackTrace();}
		sb.append(" WHERE id=").append(link.getId());
		sb.append(";");
		return sb.toString();
	}
}