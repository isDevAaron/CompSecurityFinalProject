import java.util.Random;
public class StreamCipher
{
	public static void main(String[] args)
	{
		String i = "this is an example string";
		System.out.println("plaintext: " + i);
		String encr = encrypt(i).toString();  
		System.out.println("encrypted: " + encr);
		String dec = decrypt(encr).toString();
		System.out.println("decrypted: " + dec);
	}   
	    
	public static StringBuilder encrypt(String str)
	{   
		StringBuilder res = new StringBuilder("");
	    
	    String key1 = "100";
	    long key = Long.parseLong(key1);
		Random rng = new Random(key);
		int randomNum = rng.nextInt(256);
		String s = str;
	    for (int i = 0; i < s.length(); i++)
	    {
	        char allChars = s.charAt(i);
	        int cipherNums = allChars ^ randomNum;
	        res.append(cipherNums+" ");
	    }
	    return res;
	}   

    public static StringBuilder decrypt(String enc)
    {   
    	String key1 = "100";
        int x;
        StringBuilder res = new StringBuilder("");
        long key = Long.parseLong(key1);
        Random rng = new Random(key);
        String[] splitted = enc.trim().split("\\s+");
        int randomNum = rng.nextInt(256);

        for(x = 0; x < splitted.length ; x++) {
			if(splitted[x].matches("-?\\d+")) {
	        	int next = Integer.parseInt(splitted[x]);
	            int decipher = next ^ randomNum;
	            res.append((char)decipher);
	        } 
    	}
        return res;
    }
}