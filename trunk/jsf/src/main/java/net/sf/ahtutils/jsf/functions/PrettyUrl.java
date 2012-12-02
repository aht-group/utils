package net.sf.ahtutils.jsf.functions;

public final class PrettyUrl
{
    private PrettyUrl() { }
    
    public static String prettyUrl(String input)
    {
    	input=input.replace(" ", "-");
    	input=input.replace("----", "-");
    	input=input.replace("---", "-");
    	input=input.replace("-", "-");
    	input=input.replace(":-", ":");
        return input;
    }

}
