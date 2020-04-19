import java.util.Random;
public class StreamCipher
{
	public static void main(String[] args)
	{
		String i = "testing testing...";
		System.out.println("plaintext: " + i);
		long keys = generateRandomKeys();
		String encr = encrypt(i, keys).toString();  
		System.out.println("encrypted: " + encr);
		String dec = decrypt(encr, keys).toString();
		System.out.println("decrypted: " + dec);
		
	}   
	    
	public static long generateRandomKeys() {
		Random rd = new Random(); // creating Random object
	    long key = rd.nextLong(); // displaying a random long value
	    return key;
	}
	
	public static long mainKeys(long r_keys) {
		Random rng = new Random(r_keys);
		long randomNum = rng.nextLong();
		return randomNum;
	}
	
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