package net.sf.ahtutils.jsf.functions;

public final class PrettyUrl
{
	private static PrettyUrl instance;
	
	private String blankReplace;

	public PrettyUrl()
    {
		blankReplace = "-";
    }
    
    public static String prettyUrl(String input)
    {
    	if(instance==null){instance = new PrettyUrl();}
    	return instance.format(input);
    }
    
    public String format(String input)
    {
    	input=input.replace(" ", blankReplace);
    	input=input.replace("----", "-");
    	input=input.replace("---", "-");
    	input=input.replace("-", "-");
    	input=input.replace(":-", ":");
    	input=input.replace("ä", "ae");
    	input=input.replace("ö", "oe");
    	input=input.replace("ü", "ue");
    	input=input.replace("?", "S");
    	input=input.replace("/", "-");
        return input;
    }
    
    public void setBlankReplace(String blankReplace) {this.blankReplace = blankReplace;}
}
