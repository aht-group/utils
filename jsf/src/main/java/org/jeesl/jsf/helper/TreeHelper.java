package org.jeesl.jsf.helper;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

public abstract class TreeHelper
{
	public interface Expression
	{
		public boolean condition(TreeNode node);
	}
	
	public static <T extends EjbWithParentAttributeResolver> void buildTree(JeeslFacade facade, TreeNode parent, List<T> objects, Class<T> type)
	{
		for(T o : objects)
		{
			TreeNode n = new DefaultTreeNode(o, parent);
			
			List<T> children = facade.allForParent(type, o);
			if (!children.isEmpty())
			{
				buildTree(facade, n, children, type);
			}
		}
	}
	
	public static TreeNode findNode(TreeNode node, Expression expression)
	{
		if (node == null) { return null; }
		
		if (expression.condition(node))
		{
			return node;
		}
		for (TreeNode child : node.getChildren())
		{
			TreeNode n = findNode(child, expression);
			if (n != null)
			{
				return n;
			}
		}
		return null;
	}
	
	public static List<TreeNode> findNodes(TreeNode node, Expression expression)
	{
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		if (node == null) { return nodes; }
		
		if (expression.condition(node))
		{
			nodes.add(node);
		}
		for (TreeNode child : node.getChildren())
		{
			nodes.addAll(findNodes(child, expression));
		}
		
		return nodes;
	}
}
