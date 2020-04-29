/*
 * This class is used for key generation, encryption, and decryption
 * using stream cipher. Used in ServerThread.java
 */

import java.util.Random;

public class StreamCipher
{  	    
	// generating random keys to use it in the main keys
	public static long generateRandomKeys() {
		Random rd = new Random(); // creating Random object
	    long key = rd.nextLong(); // displaying a random long value
	    return key;
	}
	
	// main keys that are used for encryption and decryption
	public static long mainKeys(long r_keys) {
		Random rng = new Random(r_keys);
		long randomNum = rng.nextLong();
		return randomNum;
	}
	
	// encrypting the strings
	public static StringBuilder encrypt(String str, long random_key)
	{   
		StringBuilder res = new StringBuilder("");
		Random rng = new Random(random_key);
		long randomNum = rng.nextLong();
		String s = str;
	    for (int i = 0; i < s.length(); i++)
	    {
	        char allChars = s.charAt(i);
	        long cipherNums = allChars ^ randomNum;
	        res.append(cipherNums+" ");
	    }
	    return res;
	}   

	// decrypting the strings
    public static StringBuilder decrypt(String enc, long random_key)
    {   
        int x;
        StringBuilder res = new StringBuilder("");
        Random rng = new Random(random_key);
        String[] splitted = enc.trim().split("\\s+");
        //int randomNum = rng.nextInt(256);
		long randomNum = rng.nextLong();

        for(x = 0; x < splitted.length ; x++) {
			if(splitted[x].matches("-?\\d+")) {
	        	long next = Long.parseLong(splitted[x]);
	            long decipher = next ^ randomNum;
	            res.append((char)decipher);
	        } 
    	}
        return res;
    }
}