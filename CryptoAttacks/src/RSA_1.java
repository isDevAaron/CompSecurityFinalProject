import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
 
import javax.crypto.Cipher;
 
// Java 8 example for RSA encryption/decryption.
// Uses strong encryption with 2048 key size.
public class RSA_1 {
 
    /*public static void main(String[] args) throws Exception {
        String plainText = "Hello World!";
 
        // Generate public and private keys using RSA
        Map<String, Object> keys = getRSAKeys();
 
        PrivateKey privateKey = (PrivateKey) keys.get("private");
        PublicKey publicKey = (PublicKey) keys.get("public");
 
        String encryptedText = encryptMessage(plainText, privateKey);
        String s = "TmFS/rx0xwkj7O1BopGFBkmk7q5itKKFqHQdfKtlLysVwR/ZPOcgODEmP9b+ViLZrw3F765XVtl+ZRik/J5raU9iz/et+n3ZVadBQ035V0KRjx2jQ8zCw/wM7dURib2qkfqbcBto3HnJ+SBLQZXD7Ku3eRg5RoufSiDqyFSW0/E8bhc0I9M8I7Fr3vgEAYAavyaZzIrFTtk+uMvslLzsqC4TtXKv8OcyLNO4pmphFuB8MLtzeVJeMKYW9YIJUMapD1xS6Ss6J8ANMDfAOBbSrBuOv+yjRZcnDjD/cSqedzZ4t4hiB1T7sGYA0c2qMm1Qw92kVHYpceQ3tD+XtkTh0w==";
       
        String descryptedText = decryptMessage(s, publicKey);
        System.out.println(descryptedText);
        System.out.println(encryptedText);
        
        //String d = decryptMessage(s, publicKey);		
        //System.out.println(d);
 
    }*/
 
    
    // Get RSA keys. Uses key size of 2048.
    static Map<String,Object> getRSAKeys() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
 
        Map<String, Object> keys = new HashMap<String,Object>();
        keys.put("private", privateKey);
        keys.put("public", publicKey);
        return keys;
    }
 
    // Decrypt using RSA public key
    static String decryptMessage(String encryptedText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText)));
    }
 
    // Encrypt using RSA private key
    static String encryptMessage(String plainText, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));
    }
}