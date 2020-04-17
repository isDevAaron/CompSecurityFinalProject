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
	@SuppressWarnings({ "static-access", "unused" })
	public void run() {
		boolean quitTime = true;
	    while( quitTime )
	    {
		   try {
			    String inLine = readSock.readLine();
			    //CeaserCipher encrypt = new CeaserCipher();
				//BlockCipher block = new BlockCipher();
			    //StreamCipher stream = new StreamCipher();
			    RSA_1 rsaKey = new RSA_1();
				FileWriter alice = null;
				alice = new FileWriter("alice_msg.txt", true);
				File f = new File("alice_msg.txt");

				if(f.exists()){
					f.delete();
					try {
						f.createNewFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
			    ReadFiles read = new ReadFiles();
			    
			    
			    if( inLine.equals("quit")) {
					quitTime = false;
			        writeSock.println("Good Bye!\n");
				    this.sock.close();
				} else {
					// for RSA encryption
					Map<String, Object> keys = rsaKey.getRSAKeys();
					PrivateKey privateKey = (PrivateKey) keys.get("private");
			        PublicKey publicKey = (PublicKey) keys.get("public");
			        String encryptedText = rsaKey.encryptMessage(inLine, privateKey);
			        String descryptedText = rsaKey.decryptMessage(encryptedText, publicKey);
			        
				    if (read.getAttackName().equals("CipherText Only Attack")) {
				    	
				    } else if (read.getAttackName().equals("Known PlainText Attack")) {
 					    alice.write(encryptedText+"\n");
 					    alice.close();
				    	writeSock.println("CipherText:\t" + encryptedText );
				    } else if (read.getAttackName().equals("Chosen CipherText Attack")) {
				    	alice.write(encryptedText+"\n");
 					    alice.close();
					    writeSock.println("CipherText [KNOWN TO USER]:    " + encryptedText );
				    } else if (read.getAttackName().equals("Chosen PlainText Attack")) {
				    	alice.write(encryptedText+"\n");
 					    alice.close();
 					    System.out.println("----------------");
 					    System.out.println(descryptedText);
 					    read.decMsgs(descryptedText);
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
