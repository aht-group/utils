package net.sf.ahtutils.doc.latex.table;

import java.util.List;

import net.sf.exlp.util.io.txt.AbstractTxtWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoubleLineTexTable extends AbstractTxtWriter implements TexTable
{
	final static Logger logger = LoggerFactory.getLogger(DoubleLineTexTable.class);
	
	public DoubleLineTexTable(String dirName,String fileName)
	{
		this.dirName=dirName;
		this.fileName=fileName;
	}
	
	public void setHeader(String colDefs, List<String> headers)
	{
		txt.add("\\begin{tabular}{"+colDefs+"}");
		txt.add("\\toprule");
		StringBuffer sb = new StringBuffer();
		for(String header : headers)
		{
			sb.append("\\textbf{"+header+"} & ");
		}
		sb.delete(sb.length()-2, sb.length());
		sb.append(" \\\\");
		txt.add(sb.toString());
		txt.add("\\midrule");
	}
	
	public void setFooter()
	{
		txt.add("\\bottomrule");
		txt.add("\\end{tabular}");
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
}
