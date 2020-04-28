package org.jeesl.factory.sql.system.io.cms;

import javax.persistence.Table;

public class SqlIoCmsMarkupFactory
{
	public static String insert(Class<?> c,long id, String lkey, long typeId, String markup)
	{
		if(c.getAnnotation(Table.class)==null) {throw new RuntimeException("Not a @Table)");}
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ").append(c.getAnnotation(Table.class).name());
		sb.append(" (id,lkey,markup_id,content)");
		sb.append(" VALUES(");
		sb.append(id);
		sb.append(", '").append(lkey).append("'");
		sb.append(", ").append(typeId);
		sb.append(", '").append(markup.replace("'","''")).append("'");
		sb.append(");");
		
		
		return sb.toString();
	}
}