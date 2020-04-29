/*
 * This class is being used to generate random strins which will be used
 * in block cipher encryption. We are doing a triple key encryption
 * and hence, this class will be used to generate three random unique strings
 * It is used in ServerThread.java
 */

public class RandomString { 

	static String getAlphaNumericString(int n) 
	{ 
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
									+ "abcdefghijklmnopqrstuvxyz"; 
		StringBuilder sb = new StringBuilder(n); 
		for (int i = 0; i < n; i++) { 
			int index = (int)(AlphaNumericString.length() * Math.random()); 
			sb.append(AlphaNumericString .charAt(index)); 
		} 
		return sb.toString(); 
	} 
} 
