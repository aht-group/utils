package net.sf.ahtutils.controller.factory.ofx.er;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ahtutils.model.qualifier.EjbErNode;
import net.sf.exlp.util.io.ClassUtil;
import net.sf.exlp.util.io.dir.RecursiveFileFinder;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.openfuxml.xml.addon.graph.Edge;
import org.openfuxml.xml.addon.graph.Edges;
import org.openfuxml.xml.addon.graph.Graph;
import org.openfuxml.xml.addon.graph.Node;
import org.openfuxml.xml.addon.graph.Nodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxErDiagramFactory
{
	private static enum Cardinality {OneToOne,OneToMany,ManyToOne,ManyToMany}
	final static Logger logger = LoggerFactory.getLogger(OfxErDiagramFactory.class);
	
	private File fBase;
	
	private Map<String,Node> mapNodes;
	private Map<String,Edge> mapEdges;
	private Graph graph;
	
	public OfxErDiagramFactory(File fBase)
	{
		this.fBase=fBase;
		
		mapNodes = new Hashtable<String,Node>();
		mapEdges = new Hashtable<String,Edge>();
		graph = new Graph();
		graph.setNodes(new Nodes());
		graph.setEdges(new Edges());
	}
	
	public void addPackages(String sEjbPackage) throws IOException, ClassNotFoundException
	{
		File fPackage = new File(fBase,sEjbPackage);
		RecursiveFileFinder finder = new RecursiveFileFinder(FileFilterUtils.suffixFileFilter(".java"));
    	List<File> list = finder.find(fPackage);
		for(File f : list){createNode(f);}
		int i=0;
		for(String key : mapNodes.keySet())
		{
			Node n = mapNodes.get(key);
			n.setId(i);i++;
			n.setCode(null);
			mapNodes.put(key, n);
			graph.getNodes().getNode().add(n);
		}
		for(File f : list){createEdge(f);}
	}
	
	public Graph create()
	{
		mergeEdges();
		JaxbUtil.debug(graph);
		return graph;
	}
	
	private void createNode(File f) throws ClassNotFoundException
	{
		Class<?> c = ClassUtil.forFile(fBase, f);
		Annotation a = c.getAnnotation(EjbErNode.class);
		if(a!=null)
		{
			EjbErNode er = (EjbErNode)a;
			Node node = new Node();
			node.setCode(c.getName());
			node.setLabel(er.name());
			mapNodes.put(node.getCode(), node);
		}
	}
	
	private void createEdge(File fClass) throws ClassNotFoundException
	{
		Class<?> c = ClassUtil.forFile(fBase, fClass);
		if(mapNodes.containsKey(c.getName()))
		{
			Node source = mapNodes.get(c.getName());
			Field fields[] = c.getDeclaredFields();
			for (int i = 0; i < fields.length; i++)
			{
				Field field = fields[i];
				Annotation annotations[] = field.getAnnotations();
				for (int j = 0; j < annotations.length; j++)
				{
					Annotation a = annotations[j];
					Cardinality cardinality = getCardinality(a.annotationType());
					
					if(cardinality!=null)
					{
						if(mapNodes.containsKey(field.getType().getName()))
						{
							Node target = mapNodes.get(field.getType().getName());
							createEdge(source, cardinality,target);
						}
						else if(field.getType().getName().equals(List.class.getName()))
						{
							ParameterizedType pT = (ParameterizedType) field.getGenericType();
					        Class<?> gC = (Class<?>) pT.getActualTypeArguments()[0];
					     
					        if(mapNodes.containsKey(gC.getName()))
					        {
					        	Node target = mapNodes.get(gC.getName());
					        	createEdge(source, cardinality,target);
					        }
						}
					}
				}
			}
		}
	}
	
	private void createEdge(Node source, Cardinality cardinality,Node target)
	{
		Edge e = new Edge();
		switch(cardinality)
		{
			case OneToOne:  e.setDirected(false);break;
			case OneToMany: e.setDirected(true);break;
			case ManyToOne: e.setDirected(true);break;
			case ManyToMany:e.setDirected(false);break;
		}
		
		e.setFrom(source.getId());
		e.setTo(target.getId());
		e.setType(cardinality.toString());
		mapEdges.put(e.getFrom()+"-"+e.getTo(), e);
	}
	
	private Cardinality getCardinality(Class<?> c)
	{
		if(c.getName().equals(javax.persistence.OneToOne.class.getName())){return Cardinality.OneToOne;}
		if(c.getName().equals(javax.persistence.OneToMany.class.getName())){return Cardinality.OneToMany;}
		if(c.getName().equals(javax.persistence.ManyToOne.class.getName())){return Cardinality.ManyToOne;}
		if(c.getName().equals(javax.persistence.ManyToMany.class.getName())){return Cardinality.ManyToMany;}
		return null;
	}
	
	private void mergeEdges()
	{
		Object[] keys = mapEdges.keySet().toArray();
		for(Object o : keys)
		{
			String keyF = (String)o;
			if(mapEdges.containsKey(keyF))
			{
				Edge eF = mapEdges.get(keyF);
				Cardinality cF = Cardinality.valueOf(eF.getType());
				String keyR = eF.getTo()+"-"+eF.getFrom();
				if(mapEdges.containsKey(keyR))
				{
					Edge eR = mapEdges.get(keyR);
					
					Cardinality cR = Cardinality.valueOf(eR.getType());
					boolean rmF = false;
					boolean rmR = false;
					if(cF==Cardinality.OneToOne && cR == Cardinality.OneToOne){rmR=true;}
					else if(cF==Cardinality.ManyToMany && cR == Cardinality.ManyToMany){rmR=true;}
					else if(cF==Cardinality.OneToMany && cR == Cardinality.ManyToOne){rmR=true;}
					else if(cF==Cardinality.ManyToOne && cR == Cardinality.OneToMany){rmF=true;}
					
					if(rmF){mapEdges.remove(keyF);}
					if(rmR){mapEdges.remove(keyR);}
				}
				else
				{
					if(cF==Cardinality.ManyToOne)
					{
						long from = eF.getFrom();
						long to = eF.getTo();
						eF.setTo(from);
						eF.setFrom(to);
						eF.setDirected(false);
					}
				}
			}
		}
		graph.getEdges().getEdge().addAll(mapEdges.values());
	}
}