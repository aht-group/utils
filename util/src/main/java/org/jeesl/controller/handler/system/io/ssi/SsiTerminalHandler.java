package org.jeesl.controller.handler.system.io.ssi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

public class SsiTerminalHandler
{
	private Process p;
	
	private StringBuffer sb;
			
	public SsiTerminalHandler()
	{
		sb = new StringBuffer();
	}
	
	public void shell() throws IOException
	{
		final List<String> commands = Arrays.asList("/bin/sh");
        p = new ProcessBuilder(commands).start();

        new Thread(() ->
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String line = null;
            try
            {
                while((line = br.readLine()) != null){sb.append(line).append(System.lineSeparator());}
            }
            catch(IOException e) {}
        }).start();

        new Thread(() ->
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            try
            {
                while((line = br.readLine()) != null){sb.append(line).append(System.lineSeparator());}
            }
            catch(IOException e) {}
        }).start();

        new Thread(() ->
        {
            int exitCode = 0;
            try {exitCode = p.waitFor();}
            catch(InterruptedException e) {e.printStackTrace();}
            System.out.printf("Exited with code %d\n", exitCode);
        }).start();
	}
	
	public String command(String cmd) throws IOException
	{
		sb.setLength(0);
		final BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
		bf.write(cmd);
		bf.newLine();
		bf.flush();
		try {
			Thread.sleep(1000*2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(sb.toString());
		return sb.toString();
	}
	
}