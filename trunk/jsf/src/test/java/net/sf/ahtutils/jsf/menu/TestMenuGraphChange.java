package net.sf.ahtutils.jsf.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;
import net.sf.ahtutils.test.AbstractAhtUtilsJsfTst;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMenuGraphChange extends AbstractAhtUtilsJsfTst
{
	final static Logger logger = LoggerFactory.getLogger(TestMenuGraphChange.class);
	
	private DirectedGraph<String, DefaultEdge> graph;
	
	private final String root="root";
	private final String node1="node1";
	private final String node1a="node1a";
	private final String node2="node2";
	
	@Before
	public void init()
	{
		graph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		graph.addVertex(root);
		
		graph.addVertex(node1); graph.addEdge(root,node1);
		graph.addVertex(node1a); graph.addEdge(node1,node1a);
		graph.addVertex(node2); graph.addEdge(root,"node2");
		graph.addVertex("node2.1"); graph.addEdge(node2,"node2.1");
		graph.addVertex("node2.2"); graph.addEdge(node2,"node2.2");
	}
	
	@Test
	public void addDuplicate()
	{
		graph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		graph.addVertex(root);
		graph.addVertex(root);
	}
	
	@Test
	public void testInit()
	{
		Assert.assertEquals(6, graph.vertexSet().size());
		Assert.assertEquals(5, graph.edgeSet().size());
	}
	
	@Test
	public void findOutgoingForVertex()
	{
		Assert.assertTrue(graph.containsVertex(node2));
		Assert.assertEquals(2, graph.outgoingEdgesOf(node2).size());
		
		Assert.assertTrue(graph.containsVertex(node1));
		Assert.assertEquals(1, graph.outgoingEdgesOf(node1).size());
		DefaultEdge edge = graph.outgoingEdgesOf(node1).iterator().next();
		
		String actual = graph.getEdgeTarget(edge);
		logger.info(actual);
	}
	
	@Test
	public void rmChildEdges()
	{
		Assert.assertEquals(6, graph.vertexSet().size());
		Assert.assertEquals(5, graph.edgeSet().size());
		Iterator<DefaultEdge> iterator = graph.outgoingEdgesOf(node1).iterator();
		List<DefaultEdge> list = new ArrayList<DefaultEdge>();
		while(iterator.hasNext())
		{
			list.add(iterator.next());
		}
		graph.removeAllEdges(list);
		Assert.assertEquals(6, graph.vertexSet().size());
		Assert.assertEquals(4, graph.edgeSet().size());
	}
	
	@Test
	public void rmTemplateEdges()
	{
		Assert.assertEquals(6, graph.vertexSet().size());
		Assert.assertEquals(5, graph.edgeSet().size());
		Iterator<DefaultEdge> iterator = graph.getAllEdges(node1, node1a).iterator();
		List<DefaultEdge> list = new ArrayList<DefaultEdge>();
		while(iterator.hasNext())
		{
			list.add(iterator.next());
		}
		graph.removeAllEdges(list);
		Assert.assertEquals(6, graph.vertexSet().size());
		Assert.assertEquals(4, graph.edgeSet().size());
		logger.info(graph.toString());
	}
}