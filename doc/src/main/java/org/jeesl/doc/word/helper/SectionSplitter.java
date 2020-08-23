package org.jeesl.doc.word.helper;

import java.util.ArrayList;
import java.util.Collections;

import com.aspose.words.Cell;
import com.aspose.words.CompositeNode;
import com.aspose.words.DocumentVisitor;
import com.aspose.words.HeaderFooter;
import com.aspose.words.HeaderFooterCollection;
import com.aspose.words.Node;
import com.aspose.words.NodeType;
import com.aspose.words.Paragraph;
import com.aspose.words.Row;
import com.aspose.words.Run;
import com.aspose.words.Section;
import com.aspose.words.SectionStart;
import com.aspose.words.SmartTag;
import com.aspose.words.StructuredDocumentTag;
import com.aspose.words.Table;
import com.aspose.words.VisitorAction;

public class SectionSplitter extends DocumentVisitor 
{
	private PageNumberFinder pageNumberFinder;

	public SectionSplitter(PageNumberFinder pageNumberFinder) {this.pageNumberFinder = pageNumberFinder;}

	public int visitParagraphStart(Paragraph paragraph) throws Exception {return this.continueIfCompositeAcrossPageElseSkip(paragraph);}

	public int visitTableStart(Table table) throws Exception {return this.continueIfCompositeAcrossPageElseSkip(table);}

	public int visitRowStart(Row row) throws Exception {return this.continueIfCompositeAcrossPageElseSkip(row);}

	public int visitCellStart(Cell cell) throws Exception {return this.continueIfCompositeAcrossPageElseSkip(cell);}

	public int visitStructuredDocumentTagStart(StructuredDocumentTag sdt) throws Exception {return this.continueIfCompositeAcrossPageElseSkip(sdt);}

	public int visitSmartTagStart(SmartTag smartTag) throws Exception {return this.continueIfCompositeAcrossPageElseSkip(smartTag);}

	public int visitSectionStart(Section section) throws Exception 
	{
		Section previousSection = (Section) section.getPreviousSibling();
		if (previousSection != null) 
		{
			HeaderFooterCollection previousHeaderFooters = previousSection.getHeadersFooters();
			if (!section.getPageSetup().getRestartPageNumbering()) 
			{
				section.getPageSetup().setRestartPageNumbering(true);
				section.getPageSetup().setPageStartingNumber(previousSection.getPageSetup().getPageStartingNumber() + this.pageNumberFinder.pageSpan(previousSection));
			}

			for (HeaderFooter previousHeaderFooter : (Iterable<HeaderFooter>) previousHeaderFooters) 
			{
				if (section.getHeadersFooters().getByHeaderFooterType(previousHeaderFooter.getHeaderFooterType()) == null) 
				{
					HeaderFooter newHeaderFooter = (HeaderFooter) previousHeaderFooters.getByHeaderFooterType(previousHeaderFooter.getHeaderFooterType()).deepClone(true);
					section.getHeadersFooters().add(newHeaderFooter);
				}
			}
		}
		
		return this.continueIfCompositeAcrossPageElseSkip(section);
	}

	public int visitSmartTagEnd(SmartTag smartTag) throws Exception {this.splitComposite(smartTag);return VisitorAction.CONTINUE;}

	public int visitStructuredDocumentTagEnd(StructuredDocumentTag sdt) throws Exception {this.splitComposite(sdt);return VisitorAction.CONTINUE;}

	public int visitCellEnd(Cell cell) throws Exception {this.splitComposite(cell);return VisitorAction.CONTINUE;}

	public int visitRowEnd(Row row) throws Exception {this.splitComposite(row);return VisitorAction.CONTINUE;}

	public int visitTableEnd(Table table) throws Exception {this.splitComposite(table);return VisitorAction.CONTINUE;}

	@SuppressWarnings({ "unchecked" })
	public int visitParagraphEnd(Paragraph paragraph) throws Exception 
	{
		if (paragraph.isEndOfSection() && paragraph.getChildNodes().getCount() == 1 && "\f".equals(paragraph.getChildNodes().get(0).getText())) 
		{
			Run run = new Run(paragraph.getDocument());
			paragraph.appendChild(run);
			int currentEndPageNum = this.pageNumberFinder.getPageEnd(paragraph);
			this.pageNumberFinder.addPageNumbersForNode(run, currentEndPageNum, currentEndPageNum);
		}

		for (Paragraph clonePara : (Iterable<Paragraph>) splitComposite(paragraph)) 
		{
			if (paragraph.isListItem()) 
			{
				double textPosition = clonePara.getListFormat().getListLevel().getTextPosition();
				clonePara.getListFormat().removeNumbers();
				clonePara.getParagraphFormat().setLeftIndent(textPosition);
			}

			if (paragraph.isInCell())
			{
				clonePara.getParagraphFormat().setSpaceBefore(0);
				paragraph.getParagraphFormat().setSpaceAfter(0);
			}
		}
		
		return VisitorAction.CONTINUE;
	}

	@SuppressWarnings("unchecked")
	public int visitSectionEnd(Section section) throws Exception
	{
		for (Section cloneSection : (Iterable<Section>) this.splitComposite(section)) 
		{
			cloneSection.getPageSetup().setSectionStart(SectionStart.NEW_PAGE);
			cloneSection.getPageSetup().setRestartPageNumbering(true);
			cloneSection.getPageSetup().setPageStartingNumber(section.getPageSetup().getPageStartingNumber() +
					(section.getDocument().indexOf(cloneSection) - section.getDocument().indexOf(section)));
			cloneSection.getPageSetup().setDifferentFirstPageHeaderFooter(false);

			SplitPageBreakCorrector.processSection(cloneSection);
		}

		SplitPageBreakCorrector.processSection(section);

		this.pageNumberFinder.addPageNumbersForNode(section.getBody(), this.pageNumberFinder.getPage(section), this.pageNumberFinder.getPageEnd(section));
		return VisitorAction.CONTINUE;
	}

	@SuppressWarnings("rawtypes")
	private int continueIfCompositeAcrossPageElseSkip(CompositeNode composite) throws Exception {return (this.pageNumberFinder.pageSpan(composite) > 1) ? VisitorAction.CONTINUE : VisitorAction.SKIP_THIS_NODE;}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList splitComposite(CompositeNode composite) throws Exception
	{
		ArrayList splitNodes = new ArrayList</* unknown Type use JavaGenericArguments */>();
		for (Node splitNode : (Iterable<Node>) this.findChildSplitPositions(composite)) {splitNodes.add(this.splitCompositeAtNode(composite, splitNode));}

		return splitNodes;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList findChildSplitPositions(CompositeNode node) throws Exception
	{
		ArrayList splitList = new ArrayList();
		int startingPage = this.pageNumberFinder.getPage(node);
		Node[] childNodes = node.getNodeType() == NodeType.SECTION ? ((Section) node).getBody().getChildNodes().toArray() : node.getChildNodes().toArray();
		for (Node childNode : childNodes) 
		{
			int pageNum = this.pageNumberFinder.getPage(childNode);
			if (childNode instanceof Run) {pageNum = this.pageNumberFinder.getPageEnd(childNode);}
			if (pageNum > startingPage) {splitList.add(childNode);startingPage = pageNum;}
			if (this.pageNumberFinder.pageSpan(childNode) > 1) {this.pageNumberFinder.addPageNumbersForNode(childNode, pageNum, pageNum);}
		}

		Collections.reverse(splitList);
		return splitList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private CompositeNode splitCompositeAtNode(CompositeNode baseNode, Node targetNode) throws Exception 
	{
		CompositeNode cloneNode = (CompositeNode) baseNode.deepClone(false);
		Node node = targetNode;
		int currentPageNum = this.pageNumberFinder.getPage(baseNode);

		if (baseNode.getNodeType() != NodeType.ROW) 
		{
			CompositeNode composite = cloneNode;
			if (baseNode.getNodeType() == NodeType.SECTION) 
			{
				cloneNode = (CompositeNode) baseNode.deepClone(true);
				Section section = (Section) cloneNode;
				section.getBody().removeAllChildren();
				composite = section.getBody();
			}

			while (node != null) 
			{
				Node nextNode = node.getNextSibling();
				composite.appendChild(node);
				node = nextNode;
			}
		}
		else 
		{
			int targetPageNum = this.pageNumberFinder.getPage(targetNode);
			Node[] childNodes = baseNode.getChildNodes().toArray();
			for (Node childNode : childNodes) 
			{
				int pageNum = this.pageNumberFinder.getPage(childNode);
				if (pageNum == targetPageNum)
				{
					cloneNode.getLastChild().remove();
					cloneNode.appendChild(childNode);
				} 
				else if (pageNum == currentPageNum) 
				{	
					cloneNode.appendChild(childNode.deepClone(false));
					if (cloneNode.getLastChild().getNodeType() != NodeType.CELL)
					{
						((CompositeNode) cloneNode.getLastChild()).appendChild(((CompositeNode) childNode).getFirstChild().deepClone(false));
					}
				}
			}
		}
		
		baseNode.getParentNode().insertAfter(cloneNode, baseNode);
		int currentEndPageNum = this.pageNumberFinder.getPageEnd(baseNode);
		this.pageNumberFinder.addPageNumbersForNode(baseNode, currentPageNum, currentEndPageNum - 1);
		this.pageNumberFinder.addPageNumbersForNode(cloneNode, currentEndPageNum, currentEndPageNum);

		for (Node childNode : (Iterable<Node>) cloneNode.getChildNodes(NodeType.ANY, true)) {this.pageNumberFinder.addPageNumbersForNode(childNode, currentEndPageNum, currentEndPageNum);}

		return cloneNode;
	}
}