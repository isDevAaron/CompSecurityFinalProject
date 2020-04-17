import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	// Default values
	String hostname = "localhost";
	int port = 5520;
	int c = 1;
	
	public void run() {
		ServerSocket servSock = null;
	    
		try {
			servSock = new ServerSocket( port );
			while( true )
		    {
				// accepting multiple connections (MultiThreading)
				Socket sock = servSock.accept();
				ServerThread servThread = new ServerThread( sock );
				servThread.start();
				/*c++;
				if(c%2 == 0) {
					pw.println("Got a connection: " + date + " /127.0.0.1 Port: 5520");
				} else {
					pw.println("Connection closed. Port: 5520");
				}*/
		    }
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		
		try {
			servSock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
		
	public static void main(String[] args) throws IOException {
		// System.out.println("Server is running ...");
	    Server server = new Server();
	    server.run();
	}
}














