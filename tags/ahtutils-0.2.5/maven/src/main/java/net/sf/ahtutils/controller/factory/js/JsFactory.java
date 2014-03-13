package net.sf.ahtutils.controller.factory.js;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;

import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.lang.SystemUtils;
import org.mozilla.javascript.EvaluatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

public class JsFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsFactory.class);
		
	private String charset;
	private JavaScriptCompressor compressor;
	
	public JsFactory(File baseDir) throws EvaluatorException, IOException
	{
		charset = "UTF-8";
		
		Reader in = fillReader(baseDir);
		compressor = new JavaScriptCompressor(in, new UtilsJsErrorReporter());
        in.close(); in = null;
 
	}
	
	public void write(File jsMinFile) throws IOException
	{
		Writer out = null;
        if (jsMinFile == null)
        {
            out = new OutputStreamWriter(System.out, charset);
        }
        else
        {
            out = new OutputStreamWriter(new FileOutputStream(jsMinFile), charset);
        }

        boolean munge = false;					// Minify only, do not obfuscate
        boolean preserveAllSemiColons = false;  // Preserve all semicolons
        boolean disableOptimizations = false;   // Disable all micro optimizations
        int linebreakpos = 80;
        boolean verbose = false;
        
        compressor.compress(out, linebreakpos, munge, verbose,preserveAllSemiColons, disableOptimizations);
        out.flush();
        out.close();
	}

	private Reader fillReader(File dir) throws IOException
	{
		StringBuffer sb = new StringBuffer();
		String[] files = dir.list(new SuffixFileFilter(".js"));
		for (String s : files)
		{
			File f = new File(dir,s);
			logger.info("Adding "+s);
			sb.append(readFile(f)).append(SystemUtils.LINE_SEPARATOR);
		}
		
	    Reader r = new StringReader(sb.toString());
	    return r;
	}
	
	private String readFile(File f) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), charset));
	    String line;
	    StringBuffer sb = new StringBuffer();
	    while ((line = br.readLine()) != null)
	    {
	        sb.append(line).append(SystemUtils.LINE_SEPARATOR);
	    }
	    br.close();
	    return sb.toString();
	}
}