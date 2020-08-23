package org.jeesl.doc.word.helper;

import com.aspose.words.Document;
import com.aspose.words.LayoutCollector;

public class PageNumberFinderFactory 
{
	private PageNumberFinderFactory() {}

	public static PageNumberFinder create(Document document) throws Exception 
	{
		LayoutCollector layoutCollector = new LayoutCollector(document);
		document.updatePageLayout();
		PageNumberFinder pageNumberFinder = new PageNumberFinder(layoutCollector);
		pageNumberFinder.splitNodesAcrossPages();
		return pageNumberFinder;
	}
}