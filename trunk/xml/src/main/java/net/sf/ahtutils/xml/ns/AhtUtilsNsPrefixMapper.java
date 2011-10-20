package net.sf.ahtutils.xml.ns;

import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class AhtUtilsNsPrefixMapper extends NamespacePrefixMapper implements NsPrefixMapperInterface
{
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix)
    {
        if("http://ahtutils.aht-group.com".equals(namespaceUri) ){return "aht";}
        if("http://ahtutils.aht-group.com/status".equals(namespaceUri) ){return "s";}
        if("http://ahtutils.aht-group.com/report".equals(namespaceUri) ){return "r";}
        if("http://ahtutils.aht-group.com/access".equals(namespaceUri) ){return "acl";}
        if("http://ahtutils.aht-group.com/dbseed".equals(namespaceUri) ){return "db";}
        if("http://ahtutils.aht-group.com/mail".equals(namespaceUri) ){return "m";}
  
        return suggestion;
    }

    public String[] getPreDeclaredNamespaceUris()
    {
    	String[] result = new String[3];
    	result[2] = "http://www.openfuxml.org/chart";
    	result = new String[0];
        return result;
    }
}