package net.sf.ahtutils.doc;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.ofx.content.XmlRawFactory;


public class DocumentationCommentBuilder
{
	public static void translationKeys(Comment comment, Configuration config, String key) throws OfxAuthoringException
	{
		configKeyReference(comment, config, key, "Translation Keys are defined in");
	}
	
	public static void configKeyReference(Comment comment, Configuration config, String key, String description) throws OfxAuthoringException
	{
		if(config.containsKey(key))
		{
			comment.getRaw().add(XmlRawFactory.build(description+": "+config.getString(key)));
		}
		else
		{
			throw new OfxAuthoringException("Cannot find key:"+key+" in config");
		}
	}
	
	public static void tableHeaders(Comment comment,String[] headerKeys)
	{
		int i=1;
		for(String s : headerKeys)
		{
			tableKey(comment,s,"Table Header ("+i+")");
			i++;
		}	
	}
	
	public static void tableKey(Comment comment, String key){tableKey(comment,key,null);}
	public static void tableKey(Comment comment, String key, String description)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(" - ");
		sb.append(key);
		if(description!=null){sb.append(" (").append(description).append(")");}
		
		comment.getRaw().add(XmlRawFactory.build(sb.toString()));
	}
	
	public static void fixedId(Comment comment, String id)
	{
		comment.getRaw().add(XmlRawFactory.build("The ID (label) for this element is fixed: "+id));
	}
	
	public static void doNotModify(Comment comment)
	{
		comment.getRaw().add(XmlRawFactory.build(""));
		comment.getRaw().add(XmlRawFactory.build("Do not modify this file, it is automatically generated."));
	}
}