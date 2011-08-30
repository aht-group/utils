package net.sf.ahtutils.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import net.sf.exlp.util.xml.JDomUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReportUtilHash {
	
	private Document document;
	
	public ReportUtilHash(String jrxml)
	{
		//Read jrxml file to w3c document
    	org.jdom.Document jdomDoc =  JDomUtil.load(jrxml);
		document =JDomUtil.toW3CDocument(jdomDoc);
	}
	

	public String readAndRemoveHash()
    {
    	String hashCode = "none";
    	
    	//Use XPath to locate and read the property element containing the hash value
	    Object result = null;
		XPathFactory factory = XPathFactory.newInstance();
	    XPath xpath = factory.newXPath();
	    XPathExpression expr = null;
		try {
			expr = xpath.compile("//property[@name='hash']");
			result = expr.evaluate(document, XPathConstants.NODESET);
		} catch (XPathExpressionException e1) {
			e1.printStackTrace();
		}
	    NodeList nodes = (NodeList) result;
	    if (nodes.getLength()>0)
	    {
		    Node hash = nodes.item(0);
		    
		    if (!hash.equals(null))
		    {	
		    	Element hash2 = (Element)hash;
			    hashCode = hash2.getAttribute("value");
		    }
			
			//Remove the hash property from the report XML
			hash.getParentNode().removeChild(hash);
	    }
	    return hashCode;
    }
	
	public String calculateHash()
	{
			
			//Calculate a new hash from the report
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				Source xmlSource = new DOMSource(document);
				Result outputTarget = new StreamResult(outputStream);
				try {
				TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget);
			} catch (TransformerConfigurationException e1) {
				e1.printStackTrace();
			} catch (TransformerException e1) {
				e1.printStackTrace();
			} catch (TransformerFactoryConfigurationError e1) {
				e1.printStackTrace();
			}
				InputStream is = new ByteArrayInputStream(outputStream.toByteArray());
				MessageDigest md = null;
			    DigestInputStream digestStream;
			    try {
				md = MessageDigest.getInstance("MD5");
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
			    digestStream = new DigestInputStream(is, md);

			    try {
				while(digestStream.read() != -1);
			    } catch (IOException e1) {
				e1.printStackTrace();}
				
			    try {
				digestStream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			    byte[] calculatedHash = md.digest();
			    
			    StringBuffer hexString = new StringBuffer();
			    for (int i = 0; i < calculatedHash.length; i++) {
			        hexString.append(Integer.toHexString(0xFF & calculatedHash[i]));
			    }
			    return hexString.toString();
	}
	
	
}
