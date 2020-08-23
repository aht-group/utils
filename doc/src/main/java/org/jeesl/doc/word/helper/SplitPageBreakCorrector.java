package org.jeesl.doc.word.helper;

import com.aspose.words.Body;
import com.aspose.words.CompositeNode;
import com.aspose.words.Node;
import com.aspose.words.NodeType;
import com.aspose.words.Paragraph;
import com.aspose.words.Run;
import com.aspose.words.Section;

public class SplitPageBreakCorrector {

	    private static final String PAGE_BREAK_STR = "\f";
	    private static final char PAGE_BREAK = '\f';

	    @SuppressWarnings("unchecked")
		public static void processSection(Section section) 
	    {
	        if (section.getChildNodes().getCount() == 0) { return; }
	        Body lastBody = section.getBody();
	        if (lastBody == null) { return; }
	        Run run = null;
	        for (Run r : (Iterable<Run>) lastBody.getChildNodes(NodeType.RUN, true)) 
	        {
	            if (r.getText().endsWith(PAGE_BREAK_STR)) { run = r; break; }
	        }
	        if (run != null) { removePageBreak(run); }
	        return;
	    }
	    public static void removePageBreakFromParagraph(Paragraph paragraph) 
	    {
	        Run run = (Run) paragraph.getFirstChild();
	        if (run.getText().equals(PAGE_BREAK_STR)) { paragraph.removeChild(run); }
	    }

	    @SuppressWarnings("unused")
		private static void processLastParagraph(Paragraph paragraph) 
	    {
	        Node lastNode = paragraph.getChildNodes().get(paragraph.getChildNodes().getCount() - 1);
	        if (lastNode.getNodeType() != NodeType.RUN) { return; }
	        Run run = (Run) lastNode;
	        removePageBreak(run);
	    }

	    @SuppressWarnings("rawtypes")
		private static void removePageBreak(Run run) 
	    {
	        Paragraph paragraph = run.getParentParagraph();
	        if (run.getText().equals(PAGE_BREAK_STR)) { paragraph.removeChild(run); }  
	        else if (run.getText().endsWith(PAGE_BREAK_STR)) { run.setText(run.getText().replaceAll("[" + PAGE_BREAK + "]+$", "")); }
	        if (paragraph.getChildNodes().getCount() == 0) { CompositeNode parent = paragraph.getParentNode(); parent.removeChild(paragraph); }
	    }
}
