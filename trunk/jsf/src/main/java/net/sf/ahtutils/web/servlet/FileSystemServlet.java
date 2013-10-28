package net.sf.ahtutils.web.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileSystemServlet extends HttpServlet
{
	final static Logger logger = LoggerFactory.getLogger(FileSystemServlet.class);
	private static final long serialVersionUID = 1L;
	
	protected static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
	
	protected void handle(File f, String contentType, HttpServletResponse response) throws IOException
	{
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(f.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + f.getName() + "\"");

        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try
        {
            input = new BufferedInputStream(new FileInputStream(f), DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) 
            {
                output.write(buffer, 0, length);
            }
        }
        finally
        {
            close(output);
            close(input);
        }
	}
	
	protected void close(Closeable resource)
    {
        if (resource != null)
        {
            try {resource.close();}
            catch (IOException e) {e.printStackTrace(); }
        }
    }
}
