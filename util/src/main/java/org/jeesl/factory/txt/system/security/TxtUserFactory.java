package org.jeesl.factory.txt.system.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtUserFactory <USER extends JeeslUser<?>>
{
	final static Logger logger = LoggerFactory.getLogger(TxtUserFactory.class);
    
    public TxtUserFactory()
    {
    	
    } 
    
    public String name(USER user)
    {
    	StringBuffer sb = new StringBuffer();
    	sb.append(user.getFirstName());
    	sb.append(" ");
    	sb.append(user.getLastName());
    	return sb.toString();
    }
    
    
    public static String buildSalt() 
    {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[32];
        random.nextBytes(bytes);
        return Base64.encodeBase64String(bytes);
    }

    public static String toHash(String clear, String salt)
    {
        MessageDigest digest;
		try{digest = MessageDigest.getInstance("SHA-512");}
		catch (NoSuchAlgorithmException e) {throw new RuntimeException(e.getMessage());}
        digest.reset();
        digest.update(stringToByte(salt));
        byte[] hashedBytes = digest.digest(clear.getBytes());
        return Base64.encodeBase64String(hashedBytes);
    }

	private static byte[] stringToByte(String input)
	{
        if (Base64.isBase64(input)) {return Base64.decodeBase64(input);}
        else {return Base64.encodeBase64(input.getBytes());}
    }
}