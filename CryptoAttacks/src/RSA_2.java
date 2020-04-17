import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;
import java.util.Scanner;

public class RSA_2 {
    
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		RSA_1 rsa = new RSA_1();
		String plainText = "Hello World!";
		FileWriter alice = null;
		try {
			alice = new FileWriter("alice_msg.txt", true);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		File f = new File("alice_msg.txt");

		if(f.exists()){
			f.delete();
			try {
				f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		
        // Generate public and private keys using RSA
        Map<String, Object> keys = null;
		try {
			keys = rsa.getRSAKeys();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
 
        PrivateKey privateKey = (PrivateKey) keys.get("private");
        PublicKey publicKey = (PublicKey) keys.get("public");
 
        String encryptedText = null;
		String descryptedText = null;
		try {
			encryptedText = rsa.encryptMessage(plainText, privateKey);
			alice.write(encryptedText+"\n");
		    alice.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		try {
			FileInputStream fis=new FileInputStream("alice_msg.txt");       
			Scanner sc=new Scanner(fis);

			while(sc.hasNextLine()){
				System.out.println("in");
				System.out.println(sc.nextLine());
		        descryptedText = rsa.decryptMessage(sc.nextLine(), publicKey);
		        System.out.println(descryptedText);
		        sc.close();  
			}  
			
		} catch (Exception ee) {
			System.out.println(ee.toString());
		}

        //System.out.println(descryptedText);
        System.out.println(encryptedText);
	}
    
}