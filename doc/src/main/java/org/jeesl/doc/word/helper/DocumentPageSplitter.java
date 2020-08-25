package org.jeesl.doc.word.helper;

import com.aspose.words.Document;
import com.aspose.words.NodeType;
import com.aspose.words.Section;

public class DocumentPageSplitter 
{
	private PageNumberFinder pageNumberFinder;

	public DocumentPageSplitter(Document source) throws Exception
	{
		this.pageNumberFinder = PageNumberFinderFactory.create(source);
	}

	private Document getDocument()
	{
		return this.pageNumberFinder.getDocument();
	}

	public Document getDocumentOfPage(int pageIndex) throws Exception 
	{
		return this.getDocumentOfPageRange(pageIndex, pageIndex);
	}

	@SuppressWarnings("unchecked")
	public Document getDocumentOfPageRange(int startIndex, int endIndex) throws Exception 
	{
		Document result = (Document) this.getDocument().deepClone(false);
		for (Section section : (Iterable<Section>) this.pageNumberFinder.retrieveAllNodesOnPages(startIndex, endIndex, NodeType.SECTION))
		{
			result.appendChild(result.importNode(section, true));
		}

		return result;
	}
}