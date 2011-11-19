package net.sf.ahtutils.report;
//
//import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
import java.io.OutputStream;
//import java.io.FileInputStream;
//import java.util.ArrayList;
//import java.util.Hashtable;
//import java.util.Iterator;
//import java.util.List;
//
//import net.sf.exlp.util.xml.JDomUtil;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//import org.jdom.Document;
//import org.jdom.Element;
//import org.jdom.JDOMException;
//import org.jdom.output.Format;
//import org.jdom.xpath.XPath;
//
//
public class ReportUtilXls {
//	
//	static Log logger = LogFactory.getLog(ReportUtilXls.class);
//	
public static OutputStream RemoveEmptyCells(String fileName)
{
	OutputStream out = new ByteArrayOutputStream();
//		try {
//			InputStream is = new FileInputStream(new File(fileName));
//			out = process(is);
//		} catch (FileNotFoundException e) {e.printStackTrace();}
		return out;
}
//	
//	public static OutputStream RemoveEmptyCells(OutputStream os)
//	{
//		ByteArrayOutputStream out = (ByteArrayOutputStream)os;
//		InputStream is = new ByteArrayInputStream(out.toByteArray());
//		out = (ByteArrayOutputStream) process(is);
//		return out;
//	}
//
//	
//	public static OutputStream process(InputStream in)
//	{
//		OutputStream os = new ByteArrayOutputStream();
//		ByteArrayOutputStream out = (ByteArrayOutputStream)os;
//		InputStream is = in;
//		try
//		{
//			POIFSFileSystem fs = new POIFSFileSystem( is );
//			HSSFWorkbook wb = new HSSFWorkbook(fs);
//
//			HSSFSheet sheet = wb.getSheetAt(0);
//         
//	         // Iterate over each row in the sheet
//	         Iterator rows = sheet.rowIterator(); 
//	         ArrayList<Short> toBeDeleted = new ArrayList<Short>();
//	         while(rows.hasNext())
//	         {           
//	             HSSFRow row = (HSSFRow) rows.next();
//	             Iterator cells = row.cellIterator();
//	             while(cells.hasNext())
//	             {
//	                 HSSFCell cell = (HSSFCell) cells.next();
//	                 switch (cell.getCellType())
//	                 {
//	                     case HSSFCell.CELL_TYPE_NUMERIC:
//	                         break;
//	                     case HSSFCell.CELL_TYPE_STRING: 
//	                         break;
//	                     default:
//	                         if (!toBeDeleted.contains(cell.getCellNum())) {toBeDeleted.add(cell.getCellNum());}
//	                     	 break;
//	                 }
//	             }
//	         }
//	         ArrayList<Short> deleted = new ArrayList<Short>();
//             for (Short column : toBeDeleted)
//             {
//            	Boolean delete = true;
//             	for (int i = 0; i<sheet.getLastRowNum(); i++)
//             	{
//             		if (sheet.getRow(i).getCell(column).getCellType()==HSSFCell.CELL_TYPE_NUMERIC || sheet.getRow(i).getCell(column).getCellType()==HSSFCell.CELL_TYPE_STRING)
//             		{
//             			delete = false;
//             		}
//             	}
//             	if (delete)
//             	{
//             		sheet.setColumnHidden(column, true);
//             	}
//             	deleted.add(column);
//             }
//             logger.info("Excel report post processing removed " +deleted.size() +" columns: " +deleted.toString());
//	         os = new ByteArrayOutputStream();
//	         wb.write(os);
//	  	} 
//	  	catch (IOException e) {logger.error(e);}
//	  	return os;
//	}
}
