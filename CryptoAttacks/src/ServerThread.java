import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;

class ServerThread extends Thread {
	Socket sock;	              // Socket connected to the Client
	PrintWriter writeSock;        // Used to write data to socket
	BufferedReader readSock;
	String encryptedText;
	String decryptedText;
	
	// Constructor
	public ServerThread(Socket s) {
	    try {
	    	this.sock = s;
			writeSock = new PrintWriter(sock.getOutputStream(), true);
			readSock = new BufferedReader( new InputStreamReader(
		            sock.getInputStream() ) );
		} catch (IOException e6) {
			e6.printStackTrace();
		}
	}
	
	// run() method that is called by start() from Server class
	@SuppressWarnings({ "static-access"})
	public void run() {
		boolean quitTime = true;
	    while( quitTime )
	    {
		   try {
			    String inLine = readSock.readLine();
			    //CeaserCipher encrypt = new CeaserCipher();
				//BlockCipher block = new BlockCipher();
			    StreamCipher stream = new StreamCipher();
			    RSA_1 rsaKey = new RSA_1();
				File k = new File("key.txt");
				File m = new File("messages.txt");

				if(m.exists()){
					m.delete();
					try {
						m.createNewFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if(k.exists()){
					k.delete();
					try {
						k.createNewFile();
					} catch (IOException eeee) {
						eeee.printStackTrace();
					}
				}
				FileWriter key = null;
				key = new FileWriter("key.txt", true);
				FileWriter messages = null;
				messages = new FileWriter("messages.txt", true);
			    ReadFiles read = new ReadFiles();
			    
			    //System.out.println();
			    if( inLine.equals("quit")) {
					quitTime = false;
			        writeSock.println("Good Bye!\n");
				    this.sock.close();
				} else {
					// for RSA encryption
					if(read.getCipher().equals("RSA")) {
						Map<String, Object> keys = rsaKey.getRSAKeys();
						PrivateKey privateKey = (PrivateKey) keys.get("private");
				        PublicKey publicKey = (PublicKey) keys.get("public");
				        key.write(publicKey.toString() + "\n");
				        key.close();
				        encryptedText = rsaKey.encryptMessage(inLine, privateKey);
				        decryptedText = rsaKey.decryptMessage(encryptedText, publicKey);
					} else if (read.getCipher().equals("Block Cipher")){
						
					} else if (read.getCipher().equals("Stream Cipher")) {
						long rand_key = stream.generateRandomKeys();
						long mainKeys = stream.mainKeys(rand_key);
						key.write("Number: "+ rand_key + "\nKey: " + mainKeys);
						key.close();
						encryptedText = stream.encrypt(inLine, rand_key).toString();
				        decryptedText = stream.decrypt(encryptedText, rand_key).toString();
				        System.out.println(decryptedText);
					}
			        
				    if (read.getAttackName().equals("CipherText Only Attack")) {
				    	
				    } else if (read.getAttackName().equals("Known PlainText Attack")) {
				    	if(read.whoSentMsg().equals("alice")) {
		 					messages.write("Alice => Encrypted Text: " + encryptedText+"\n");
		 					messages.write("Alice => Decrypted Text: " + decryptedText+"\n");
		 					messages.close();
				    	} else if (read.whoSentMsg().equals("alice")) {
				    		messages.write("Bob => Encrypted Text: " + encryptedText+"\n");
		 					messages.write("Bob => Decrypted Text: " + decryptedText+"\n");
		 					messages.close();
				    	}
				    	writeSock.println("CipherText:\t" + encryptedText );
				    } else if (read.getAttackName().equals("Chosen CipherText Attack")) {
				    	if(read.whoSentMsg().equals("alice")) {
		 					messages.write("Alice => Encrypted Text: " + encryptedText+"\n");
		 					messages.write("Alice => Decrypted Text: " + decryptedText+"\n");
		 					messages.close();
				    	} else if (read.whoSentMsg().equals("alice")) {
				    		messages.write("Bob => Encrypted Text: " + encryptedText+"\n");
		 					messages.write("Bob => Decrypted Text: " + decryptedText+"\n");
		 					messages.close();
				    	}
					    writeSock.println("CipherText [KNOWN TO USER]:    " + encryptedText );
				    } else if (read.getAttackName().equals("Chosen PlainText Attack")) {
				    	if(read.whoSentMsg().equals("alice")) {
		 					messages.write("Alice => Encrypted Text: " + encryptedText+"\n");
		 					messages.write("Alice => Decrypted Text: " + decryptedText+"\n");
		 					messages.close();
				    	} else if (read.whoSentMsg().equals("alice")) {
				    		messages.write("Bob => Encrypted Text: " + encryptedText+"\n");
		 					messages.write("Bob => Decrypted Text: " + decryptedText+"\n");
		 					messages.close();
				    	}
					    writeSock.println("CipherText:\t" + encryptedText );
				    }
				}
		   } catch (IOException e4) {
			   //e4.printStackTrace();
	       } catch (Exception e) {
			e.printStackTrace();
		} 
	    }	
	    try {
			this.sock.close();
			System.out.println("Connection closed. Port: 5520");
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	}
}
