package net.sf.ahtutils.controller.factory.ofx.graph;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.xml.status.Status;
import net.sf.exlp.util.xml.JaxbUtil;

import org.openfuxml.xml.addon.graph.Edge;
import org.openfuxml.xml.addon.graph.Edges;
import org.openfuxml.xml.addon.graph.Graph;
import org.openfuxml.xml.addon.graph.Node;
import org.openfuxml.xml.addon.graph.Nodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxStatusTransitionDiagramFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxStatusTransitionDiagramFactory.class);
	
	private Map<Integer,Status> mapStatus;
	private Map<String,Node> mapNodes;
	private Map<String,Edge> mapEdges;
	
	private Graph graph;
	
	public OfxStatusTransitionDiagramFactory()
	{
		mapStatus = new Hashtable<Integer,Status>();
		mapNodes = new Hashtable<String,Node>();
		mapEdges = new Hashtable<String,Edge>();
		graph = new Graph();
		graph.setNodes(new Nodes());
		graph.setEdges(new Edges());
	}
	
	public void addStatus(List<Status> list) throws IOException
	{
		int i=0;
		for(Status status : list)
		{
			Node node = new Node();
			node.setId(i);
			node.setCode(status.getCode());
			node.setLabel(status.getCode());
			graph.getNodes().getNode().add(node);
			mapNodes.put(node.getCode(), node);
			mapStatus.put(i, status);
			i++;
		}
	}
	
	public Graph create()
	{
		createEdges();
		JaxbUtil.debug(graph);
		return graph;
	}
	
	private void createEdges()
	{
		for(Status status : mapStatus.values())
		{
			if(status.isSetTransistions())
			{
				for(Status s : status.getTransistions().getStatus())
				{
					Node src = mapNodes.get(status.getCode());
					Node dst = mapNodes.get(s.getCode());
					
					Edge e = new Edge();
					e.setDirected(true);
					e.setFrom(src.getId());
					e.setTo(dst.getId());
					graph.getEdges().getEdge().add(e);
				}
			}
		}
	}
}