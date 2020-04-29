import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.View;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Choice;

@SuppressWarnings("serial")
public class alice extends JFrame {

	protected static final int ServerSocket = 0;
	private JPanel contentPane;
	private JTextField txtIP;
	private static JTextField txtPort;
	private JTextField txtMessage;
	protected ServerSocket servSock;
	private Socket sock = new Socket();   
	private PrintWriter writeSock;    
	private BufferedReader readSock;
	private boolean check = true;
	FileWriter plaintext = null;
	Choice choice = new Choice();
	ReadFiles read = new ReadFiles();
    private String data;
    File myObj = null;
	int lineNum = 0; 
    Scanner myReader = null;
    
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					alice frame = new alice();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public alice() {
		setFont(new Font("Tahoma", Font.PLAIN, 12));
		setTitle("Crypto Project");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(90, 90, 494, 427);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIpLabel = new JLabel("IP Address");
		lblIpLabel.setBounds(10, 12, 94, 22);
		lblIpLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblIpLabel);
		
		JLabel lblPortNumber = new JLabel("Port Number");
		lblPortNumber.setBounds(10, 43, 94, 23);
		lblPortNumber.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblPortNumber);
		
		txtIP = new JTextField();
		txtIP.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtIP.setBounds(102, 12, 184, 23);
		txtIP.setText("localhost");
		contentPane.add(txtIP);
		txtIP.setColumns(10);
		
		txtPort = new JTextField();
		txtPort.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPort.setBounds(102, 43, 184, 23);
		txtPort.setText("5520");
		txtPort.setColumns(10);
		contentPane.add(txtPort);
		
		JButton btnConnect = new JButton("Connect to the server");
		JTextArea txtArea_aliceMsgs = new JTextArea();
		
		// connecting to socket
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				try {
					   int portNum = Integer.parseInt(txtPort.getText());
					   if(portNum != 5520) {
						   throw new java.net.UnknownHostException();
					   }
					   String hostAddress = txtIP.getText();  
					   sock = new Socket(hostAddress, portNum );
					   writeSock = new PrintWriter( sock.getOutputStream(), true );
					   readSock = new BufferedReader( new InputStreamReader(
					                                  sock.getInputStream() ) );
					   if(check == true) {
						   txtArea_aliceMsgs.append("Connected to Server\n");
						   btnConnect.setText("Disconnect");
						   check = false;
					   } else {
						   txtArea_aliceMsgs.append("Disconnected!\n");
						   btnConnect.setText("Connect");
						   sock = null; 
						   check = true;
					   }
					}
					catch( Exception e3 ) {
					   //System.out.println( "Error: " + e3.toString() );
					   txtArea_aliceMsgs.append("Error in connecting \\ disconnecting\n");
					   sock = null;    
					}
			}
		});
		
		btnConnect.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnConnect.setBounds(296, 29, 175, 37);
		contentPane.add(btnConnect);
		
		txtMessage = new JTextField();
		txtMessage.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtMessage.setBounds(10, 96, 276, 23);
		contentPane.add(txtMessage);
		txtMessage.setColumns(10);
		
		TimerTask task = new FileWatcher( new File("plaintext.txt") ) {
			
			protected void onChange( File file ) {
		    	  myObj = new File("plaintext.txt");
		    	  try {
						myReader = new Scanner(myObj);
					} catch (FileNotFoundException e12345) {
						// TODO Auto-generated catch block
					}
					while (myReader.hasNextLine()) {
					  data = myReader.nextLine();
					}
					myReader.close();
					txtArea_aliceMsgs.append(data+"\n");
		    }
		   };
		   Timer timer = new Timer();
		   timer.schedule( task , new Date(), 1000 );
		
		// 'Send' button action
		JButton btnSend = new JButton("Send message");
		btnSend.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String msg = txtMessage.getText();
				File f = new File("who_sent_msg.txt");
				if(f.exists()){
					f.delete();
					try {
						f.createNewFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				FileWriter who = null;
				FileWriter myWriter = null;
				try {
					who = new FileWriter("who_sent_msg.txt");
					who.write("alice");
					who.close();
					myWriter = new FileWriter("cipher.txt");
					myWriter.write(choice.getSelectedItem());
					myWriter.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				try {
					if( sock != null ) {
					   String message = msg;
					   writeSock.println( message );
					   String dataRead = null;
					   try {
						   plaintext = new FileWriter("plaintext.txt");
						   plaintext.write("Alice: "+ msg+"\n");
						   plaintext.close();
						   txtMessage.setText("");
						   dataRead = readSock.readLine();
					   } catch (IOException e1) {
						   txtMessage.setText("");
						   txtArea_aliceMsgs.append("Error in receiving data from the server!\n");
					   }
					   txtArea_aliceMsgs.append(dataRead+"\n");
					   if(msg.equals("quit")) {
							btnConnect.setText("Connect");
							sock = null;
							txtArea_aliceMsgs.append("Disconnected!\n");
							check = true;
						}
					} else {
						txtMessage.setText("");
						txtArea_aliceMsgs.append("You are not connected\n");
					}
				} catch (Exception e2) {
					txtMessage.setText("");
					txtArea_aliceMsgs.append("You are not connected\n");
				}
				lineNum = getWrappedLines(txtArea_aliceMsgs);
				System.out.println(lineNum);
				if (lineNum > 6) {
					FileWriter count = null;
					try {
						count = new FileWriter("count.txt");
						count.write(Integer.toString(lineNum));
						count.close();
					} catch (Exception w) {
						
					}
				}
			}
		});
		
		btnSend.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSend.setBounds(298, 108, 173, 37);
		contentPane.add(btnSend);
		
		JScrollPane scrollPane_aliceMsgs = new JScrollPane();
		scrollPane_aliceMsgs.setBounds(10, 162, 458, 212);
		contentPane.add(scrollPane_aliceMsgs);
		scrollPane_aliceMsgs.setViewportView(txtArea_aliceMsgs);
		
		JLabel lblAlice = new JLabel("Alice:");
		lblAlice.setBounds(10, 77, 53, 24);
		contentPane.add(lblAlice);
		
		choice.setBounds(10, 125, 277, 31);
		choice.add("Block Cipher");
		choice.add("Stream Cipher");
		choice.add("RSA");
		contentPane.add(choice);
		
	}
	
	public static int getWrappedLines(JTextArea component)
	{
		View view = component.getUI().getRootView(component).getView(0);
		int preferredHeight = (int)view.getPreferredSpan(View.Y_AXIS);
		int lineHeight = component.getFontMetrics( component.getFont() ).getHeight();
		return preferredHeight / lineHeight;
	}
}
