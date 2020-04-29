/*
 * the class that governs the communication and encryption/decryption
 * all the algorithms are called here
 * must be run prior to using the program
 */

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
			    StreamCipher stream = new StreamCipher();
			    RSA_1 rsaKey = new RSA_1();
				File k = new File("key.txt");
				
				// checking if the file exists, then delete it, or else continue
				if(k.exists()){
					k.delete();
					try {
						k.createNewFile();
					} catch (IOException eeee) {
						eeee.printStackTrace();
					}
				}
				// reading and writing files
				FileWriter key = null;
				key = new FileWriter("key.txt", true);
				FileWriter messages = null;
				messages = new FileWriter("messages.txt", true);
			    ReadFiles read = new ReadFiles();
			    
			    if( inLine.equals("quit")) {
					quitTime = false;
			        writeSock.println("Good Bye!\n");
				    this.sock.close();
				} else {
					if(read.getCipher().equals("RSA")) {
						
						// RSA encryption: generating keys and encrypting/decrypting messages
						Map<String, Object> keys = rsaKey.getRSAKeys();
						PrivateKey privateKey = (PrivateKey) keys.get("private");
				        PublicKey publicKey = (PublicKey) keys.get("public");
				        key.write(publicKey.toString() + "\n");
				        key.close();
				        encryptedText = rsaKey.encryptMessage(inLine, privateKey);
				        decryptedText = rsaKey.decryptMessage(encryptedText, publicKey);
					} else if (read.getCipher().equals("Block Cipher")){
						
						// Block Cipher: generating keys and encrypting/decrypting messages
						DES des = new DES();
						RandomString rand = new RandomString();
						String key1 = rand.getAlphaNumericString(20); 
						String key2 = rand.getAlphaNumericString(25); 
						String key3 = rand.getAlphaNumericString(15); 
						key.write("Key1: " + key1 + "\nKey2: " + key2 + "\nKey3: "+ key3 + "\n");
				        key.close();
						String encryptedText1 = des.encrypt(key3, des.decrypt(key2, des.encrypt(key1, des.utfToBin(inLine))));
						encryptedText = des.binToHex(encryptedText1);
						String decryptedText1 = des.decrypt(key1, des.encrypt(key2, des.decrypt(key3, des.hexToBin(des.binToHex(encryptedText1)))));
						decryptedText = des.binToUTF(decryptedText1);
					} else if (read.getCipher().equals("Stream Cipher")) {
						
						// Stream Cipher: generating keys and encrypting/decrypting messages
						long rand_key = stream.generateRandomKeys();
						long mainKeys = stream.mainKeys(rand_key);
						key.write("Number: "+ rand_key + "\nKey: " + mainKeys);
						key.close();
						encryptedText = stream.encrypt(inLine, rand_key).toString();
				        decryptedText = stream.decrypt(encryptedText, rand_key).toString();
				        System.out.println(decryptedText);
					}
			        
					// checking the attack modes
				    if (read.getAttackName().equals("CipherText Only Attack")) {
				    	if(read.whoSentMsg().equals("alice")) {
		 					messages.write("Alice => Encrypted Text: " + encryptedText+"\n");
		 					messages.write("Alice => Decrypted Text: " + decryptedText+"\n");
		 					messages.close();
					    	writeSock.println("");
				    	} else if (read.whoSentMsg().equals("bob")) {
				    		messages.write("Bob => Encrypted Text: " + encryptedText+"\n");
		 					messages.write("Bob => Decrypted Text: " + decryptedText+"\n");
		 					messages.close();
					    	writeSock.println("");
				    	} else if (read.whoSentMsg().equals("chuck")) {
				    		messages.write("Chuck => Encrypted Text: " + encryptedText+"\n");
		 					messages.write("Chuck => Decrypted Text: " + decryptedText+"\n");
					    	writeSock.println("CipherText:\t" + encryptedText );
				    	}
				    } else if (read.getAttackName().equals("Known PlainText Attack")) {
				    	if(read.whoSentMsg().equals("alice")) {
		 					messages.write("Alice => Encrypted Text: " + encryptedText+"\n");
		 					messages.write("Alice => Decrypted Text: " + decryptedText+"\n");
		 					messages.close();
					    	writeSock.println("");
				    	} else if (read.whoSentMsg().equals("bob")) {
				    		messages.write("Bob => Encrypted Text: " + encryptedText+"\n");
		 					messages.write("Bob => Decrypted Text: " + decryptedText+"\n");
		 					messages.close();
					    	writeSock.println("");
				    	} else if (read.whoSentMsg().equals("chuck")) {
				    		messages.write("Chuck => Encrypted Text: " + encryptedText+"\n");
		 					messages.write("Chuck => Decrypted Text: " + decryptedText+"\n");
					    	writeSock.println("CipherText:\t" + encryptedText );
				    	}
				    } else if (read.getAttackName().equals("Chosen CipherText Attack")) {
				    	if(read.whoSentMsg().equals("alice")) {
		 					messages.write("Alice => Encrypted Text: " + encryptedText+"\n");
		 					messages.write("Alice => Decrypted Text: " + decryptedText+"\n");
		 					messages.close();
					    	writeSock.println("");
				    	} else if (read.whoSentMsg().equals("bob")) {
				    		messages.write("Bob => Encrypted Text: " + encryptedText+"\n");
		 					messages.write("Bob => Decrypted Text: " + decryptedText+"\n");
		 					messages.close();
					    	writeSock.println("");
				    	} else if (read.whoSentMsg().equals("chuck")) {
				    		messages.write("Chuck => Encrypted Text: " + encryptedText+"\n");
		 					messages.write("Chuck => Decrypted Text: " + decryptedText+"\n");
					    	writeSock.println("CipherText:\t" + encryptedText );
				    	}
				    } else if (read.getAttackName().equals("Chosen PlainText Attack")) {
				    	if(read.whoSentMsg().equals("alice")) {
		 					messages.write("Alice => Encrypted Text: " + encryptedText+"\n");
		 					messages.write("Alice => Decrypted Text: " + decryptedText+"\n");
		 					messages.close();
					    	writeSock.println("");
				    	} else if (read.whoSentMsg().equals("bob")) {
				    		messages.write("Bob => Encrypted Text: " + encryptedText+"\n");
		 					messages.write("Bob => Decrypted Text: " + decryptedText+"\n");
		 					messages.close();
					    	writeSock.println("");
				    	} else if (read.whoSentMsg().equals("chuck")) {
				    		messages.write("Chuck => Encrypted Text: " + encryptedText+"\n");
		 					messages.write("Chuck => Decrypted Text: " + decryptedText+"\n");
					    	writeSock.println("CipherText:\t" + encryptedText );
				    	}
				    }
				}
		   } catch (IOException e4) {
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
