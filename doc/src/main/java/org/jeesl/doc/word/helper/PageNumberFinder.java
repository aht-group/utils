package org.jeesl.doc.word.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import com.aspose.words.Document;
import com.aspose.words.LayoutCollector;
import com.aspose.words.Node;
import com.aspose.words.NodeType;
import com.aspose.words.Paragraph;
import com.aspose.words.Run;

@SuppressWarnings("rawtypes")
public class PageNumberFinder
{
	private Hashtable nodeStartPageLookup = new Hashtable();
	private Hashtable nodeEndPageLookup = new Hashtable();
	private LayoutCollector collector;
	private Hashtable reversePageLookup;

	
	
	public PageNumberFinder(LayoutCollector collector){this.collector = collector;}
	public Document getDocument() {return this.collector.getDocument();}

	public int getPage(Node node) throws Exception
	{
		return this.nodeStartPageLookup.containsKey(node) ? (Integer) this.nodeStartPageLookup.get(node) : this.collector.getStartPageIndex(node);
	}

	public int getPageEnd(Node node) throws Exception 
	{
		return this.nodeEndPageLookup.containsKey(node) ? (Integer) this.nodeEndPageLookup.get(node) : this.collector.getEndPageIndex(node);
	}

	public int pageSpan(Node node) throws Exception {return this.getPageEnd(node) - this.getPage(node) + 1;}

	@SuppressWarnings("unchecked")
	public ArrayList retrieveAllNodesOnPages(int startPage, int endPage, int nodeType) throws Exception 
	{
		if (startPage < 1 || startPage > this.getDocument().getPageCount()) 
		{
			throw new IllegalStateException("'startPage' is out of range");
		}

		if (endPage < 1 || endPage > this.getDocument().getPageCount() || endPage < startPage)
		{
			throw new IllegalStateException("'endPage' is out of range");
		}

		this.checkPageListsPopulated();
		ArrayList pageNodes = new ArrayList();
		for (int page = startPage; page <= endPage; page++) 
		{
			if (!this.reversePageLookup.containsKey(page)) {continue;}

			for (Node node : (Iterable<Node>) this.reversePageLookup.get(page))
			{
				if (node.getParentNode() != null && (nodeType == NodeType.ANY || node.getNodeType() == nodeType) && !pageNodes.contains(node)) {pageNodes.add(node);}
			}
		}
		return pageNodes;
	}

	@SuppressWarnings("unchecked")
	public void splitNodesAcrossPages() throws Exception
	{
		for (Paragraph paragraph : (Iterable<Paragraph>) this.getDocument().getChildNodes(NodeType.PARAGRAPH, true)) 
		{
			if (this.getPage(paragraph) != this.getPageEnd(paragraph)) {this.splitRunsByWords(paragraph);}
		}
		this.clearCollector();
		this.getDocument().accept(new SectionSplitter(this));
	}

	@SuppressWarnings("unchecked")
	void addPageNumbersForNode(Node node, int startPage, int endPage) 
	{
		if (startPage > 0) {this.nodeStartPageLookup.put(node, startPage);}
		if (endPage > 0) {this.nodeEndPageLookup.put(node, endPage);}
	}

	private static boolean isHeaderFooterType(Node node) {return node.getNodeType() == NodeType.HEADER_FOOTER || node.getAncestor(NodeType.HEADER_FOOTER) != null;}

	@SuppressWarnings("unchecked")
	private void checkPageListsPopulated() throws Exception 
	{
		if (this.reversePageLookup != null) {return;}
		this.reversePageLookup = new Hashtable();
		
		for (Node node : (Iterable<Node>) this.getDocument().getChildNodes(NodeType.ANY, true)) 
		{
			if (isHeaderFooterType(node)) {continue;}

			int startPage = this.getPage(node);
			int endPage = this.getPageEnd(node);
			for (int page = startPage; page <= endPage; page++) 
			{
				if (!this.reversePageLookup.containsKey(page)) {this.reversePageLookup.put(page, new ArrayList());}
				((ArrayList) this.reversePageLookup.get(page)).add(node);
			}
		}
	}

	private void splitRunsByWords(Paragraph paragraph) throws Exception 
	{
		for (Run run : paragraph.getRuns()) 
		{
			if (this.getPage(run) == this.getPageEnd(run)) {continue;}
			this.splitRunByWords(run);
		}
	}

	private void splitRunByWords(Run run) 
	{
		String[] words = run.getText().split(" ");
		List<String> list = Arrays.asList(words);
		Collections.reverse(list);
		String[] reversedWords = (String[]) list.toArray();

		for (String word : reversedWords) 
		{
			int pos = run.getText().length() - word.length() - 1;
			if (pos > 1) {splitRun(run, run.getText().length() - word.length() - 1);}
		}
	}

	private static Run splitRun(Run run, int position) 
	{
		Run afterRun = (Run) run.deepClone(true);
		afterRun.setText(run.getText().substring(position));
		run.setText(run.getText().substring(0, position));
		run.getParentNode().insertAfter(afterRun, run);
		return afterRun;
	}

	private void clearCollector() throws Exception 
	{
		this.collector.clear();
		this.getDocument().updatePageLayout();

		this.nodeStartPageLookup.clear();
		this.nodeEndPageLookup.clear();
	}
}