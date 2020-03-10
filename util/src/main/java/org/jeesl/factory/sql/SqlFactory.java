package org.jeesl.factory.sql;

import java.util.List;

import javax.persistence.Table;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.util.query.sql.JeeslSqlQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlFactory
{
	final static Logger logger = LoggerFactory.getLogger(SqlFactory.class);
	
	public static <E extends Enum<E>, T extends EjbWithId> void update(StringBuilder sb, Class<?> c, String alias, E attribute, T t, boolean newLine)
	{
		if(c.getAnnotation(Table.class)==null) {throw new RuntimeException("Not a @Table)");}
		sb.append("UPDATE ").append(c.getAnnotation(Table.class).name());
		sb.append(" SET ").append(id(alias,attribute)).append("=").append(t.getId());
		newLine(newLine,sb);
	}
	
	public static void newLine(boolean newLine, StringBuilder sb)
	{
		if(newLine){sb.append("\n");}
	}
	
	public static <E extends Enum<E>> String sum(String item, E attribute, String as, boolean newLine)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("sum(").append(path(item,attribute)).append(")");
		sb.append(" AS ").append(as);
		newLine(newLine,sb);
		return sb.toString();
	}
	
	public static <E extends Enum<E>> String id(String alias, E attribute)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(path(alias,attribute)).append("_id");
		return sb.toString();
	}
	
	public static <E extends Enum<E>> String path(String alias, E attribute)
	{
		StringBuilder sb = new StringBuilder();
		if(alias!=null && alias.length()>0) {sb.append(alias).append(".");}
		sb.append(attribute.toString());
		return sb.toString();
	}
	
	public static <T extends EjbWithId> String delete(Class<T> c)
	{
		if(c.getAnnotation(Table.class)==null) {throw new RuntimeException("Not a @Table)");}
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(c.getAnnotation(Table.class).name());
		return sb.toString();
	}
	
	public static <T extends EjbWithId> String delete(T t)
	{
		if(t.getClass().getAnnotation(Table.class)==null) {throw new RuntimeException("Not a @Table)");}
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(t.getClass().getAnnotation(Table.class).name());
		sb.append(" WHERE id="+t.getId());
		sb.append(";");
		return sb.toString();
	}
	
	public static <T extends EjbWithId> String drop(Class<T> c)
	{
		if(c.getAnnotation(Table.class)==null) {throw new RuntimeException("Not a @Table)");}
		return drop(c.getAnnotation(Table.class).name());
	}
	
	public static <T extends EjbWithId> String drop(String table)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("DROP TABLE ");
		sb.append(table);
		sb.append(";");
		return sb.toString();
	}
	
	public static String from(String table, String as, boolean newLine)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("FROM ").append(table).append(" AS ").append(as);
		newLine(newLine,sb);
		return sb.toString();
	}
	
	public static <E extends Enum<E>> String distinct(StringBuilder sb, String alias, E attribute, boolean newLine)
	{
		sb.append(" DISTINCT ON (");
		sb.append(id(alias,attribute));
		sb.append(")");
		newLine(newLine,sb);
		return sb.toString();
	}
	
	public static <T extends EjbWithId> void from(StringBuilder sb, Class<T> c, String alias, boolean newLine)
	{
		if(c.getAnnotation(Table.class)==null) {throw new RuntimeException("Not a @Table)");}
		sb.append("FROM ").append(c.getAnnotation(Table.class).name());
		sb.append(" AS ").append(alias);
		newLine(newLine,sb);
	}
	
	public static <E extends Enum<E>> String inIdList(String item, E attribute, List<EjbWithId> ids, boolean newLine)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(item).append(".").append(attribute).append("_id");
		sb.append(" IN (").append(JeeslSqlQuery.inIdList(ids)).append(")");
		newLine(newLine,sb);
		return sb.toString();
	}
	
	public static void limit(StringBuilder sb, int limit, boolean newLine)
	{
		limit(sb,true,limit,newLine);
	}
	public static void limit(StringBuilder sb, boolean apply, int limit, boolean newLine)
	{
		if(apply)
		{
			sb.append(" LIMIT ").append(limit);
			newLine(newLine,sb);
		}
	}
	
	public static <T extends EjbWithId> void valueId(boolean first, StringBuilder sb, T id)
	{
		if(!first) {sb.append(",");}
		sb.append(id.getId());
	}
	public static void valueBool(boolean first, StringBuilder sb, boolean value)
	{
		if(!first) {sb.append(",");}
		if(value) {sb.append("'t'");}
		else {sb.append("'f'");}
	}
	public static void valueInt(boolean first, StringBuilder sb, int value)
	{
		if(!first) {sb.append(",");}
		sb.append(value);
	}
	public static void valueString(boolean first, StringBuilder sb, String value)
	{
		if(!first) {sb.append(",");}
		sb.append("'").append(value).append("'");
	}
	

	
	public static <E extends Enum<E>, T extends EjbWithId> void where(StringBuilder sb, String alias, boolean negate, E attribute, T where, boolean newLine)
	{
		sb.append(" WHERE");
		sb.append(" ").append(id(alias,attribute));
		if(where!=null)
		{
			if(negate) {logger.warn("NOT is NYI");}
			sb.append("=").append(where.getId());
		}
		else
		{
			sb.append(" IS");
			if(negate) {sb.append(" NOT");}
			sb.append(" NULL");
		}
		newLine(newLine,sb);
	}
	public static <E extends Enum<E>> void whereAnd(StringBuilder sb, String alias, boolean notNegate, E attribute, long value, boolean newLine)
	{
		sb.append(" AND");
		sb.append(" ").append(id(alias,attribute));
		sb.append("=").append(value);
		newLine(newLine,sb);
	}
}