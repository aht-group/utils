package net.sf.ahtutils.report.latex.table;

import java.util.List;

import net.sf.exlp.util.io.txt.AbstractTxtWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DoubleLineTexLongTable extends AbstractTxtWriter implements TexTable
{
	static Log logger = LogFactory.getLog(DoubleLineTexLongTable.class);
	
	private String caption,label;

	public DoubleLineTexLongTable(String dirName, String fileName)
	{
		this.dirName=dirName;
		this.fileName=fileName;
		caption="dummy";
		label="dummy";
	}
	
	public void setHeader(String colDefs, List<String> headers)
	{
		StringBuffer sbHeader = new StringBuffer();
		for(String header : headers)
		{
			sbHeader.append("\\textbf{"+header+"} & ");
		}
		sbHeader.delete(sbHeader.length()-2, sbHeader.length());
		sbHeader.append(" \\\\");
		
		txt.add("\\begin{longtable}{"+colDefs+"}");
		txt.add("");
		
		txt.add("\\toprule");
		txt.add(sbHeader.toString());
		txt.add("\\midrule");
		txt.add("\\endfirsthead");
		txt.add("");
		
		txt.add("\\multicolumn{"+headers.size()+"}{l}{continued from previous page} \\\\");
		txt.add("\\toprule");
		txt.add(sbHeader.toString());
		txt.add("\\midrule");
		txt.add("\\endhead");
		txt.add("");
	
		txt.add("\\bottomrule");
		txt.add("\\multicolumn{"+headers.size()+"}{r}{continued on next page} \\\\");
		txt.add("\\caption{"+caption+"} \\label{"+label+"} \\\\");
		txt.add("\\endfoot");
		txt.add("");
		
		txt.add("\\bottomrule");
		txt.add("\\caption{"+caption+"} \\label{"+label+"} \\\\");
		txt.add("\\endlastfoot");
		txt.add("");
	}
	
	public void setFooter()
	{
		txt.add("\\end{longtable}");
	}
	
	public void addDataRow(String col[])
	{
		StringBuffer sb = new StringBuffer();
		
		for(String s : col)
		{
			sb.append(" "+ s+" &");
		}
		sb.deleteCharAt(sb.length()-1);
		
		sb.append(" \\\\");
		txt.add(sb.toString());
	}
	
	public void setCaption(String caption) {this.caption = caption;}
	public void setLabel(String label) {this.label = label;}
	public void setFileName(String fileName) {this.fileName = fileName;}
}
