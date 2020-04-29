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
public class bob extends JFrame {

	protected static final int ServerSocket = 0;
	private JPanel contentPane;
	private JTextField txtIP_bob;
	private static JTextField txtPort_bob;
	private JTextField txtMessage_bob;
	protected ServerSocket servSock_bob;
	private Socket sock_bob = new Socket();   
	private PrintWriter writeSock_bob;    
	private BufferedReader readSock_bob;
	private boolean check_bob = true;
	private Choice choice_bob;
	ReadFiles read_bob = new ReadFiles();
    private String data;
	public static JTextArea txtArea_bobMsgs = null;
	File myObj = null;
	int lineNum = 0; 
    Scanner myReader = null;
    
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					bob frame = new bob();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public bob() {
		setFont(new Font("Tahoma", Font.PLAIN, 12));
		setTitle("Crypto Project");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(80, 80, 494, 427);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIpLabel_bob = new JLabel("IP Address");
		lblIpLabel_bob.setBounds(10, 12, 94, 22);
		lblIpLabel_bob.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblIpLabel_bob);
		
		JLabel lblPortNumber_bob = new JLabel("Port Number");
		lblPortNumber_bob.setBounds(10, 43, 94, 23);
		lblPortNumber_bob.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblPortNumber_bob);
		
		txtIP_bob = new JTextField();
		txtIP_bob.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtIP_bob.setBounds(102, 12, 184, 23);
		txtIP_bob.setText("localhost");
		contentPane.add(txtIP_bob);
		txtIP_bob.setColumns(10);
		
		txtPort_bob = new JTextField();
		txtPort_bob.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPort_bob.setBounds(102, 43, 184, 23);
		txtPort_bob.setText("5520");
		txtPort_bob.setColumns(10);
		contentPane.add(txtPort_bob);
		
		JButton btnConnect_bob = new JButton("Connect to the server");
		txtArea_bobMsgs = new JTextArea();
		
		// connecting to socket
		btnConnect_bob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				try {
					   System.out.println("here");
					   int portNum = Integer.parseInt(txtPort_bob.getText());
					   if(portNum != 5520) {
						   throw new java.net.UnknownHostException();
					   }
					   String hostAddress = txtIP_bob.getText();  
					   sock_bob = new Socket(hostAddress, portNum );
					   writeSock_bob = new PrintWriter( sock_bob.getOutputStream(), true );
					   readSock_bob = new BufferedReader( new InputStreamReader(
					                                  sock_bob.getInputStream() ) );
					   if(check_bob == true) {
						   txtArea_bobMsgs.append("Connected to Server\n");
						   btnConnect_bob.setText("Disconnect");
						   check_bob = false;
					   } else {
						   txtArea_bobMsgs.append("Disconnected!\n");
						   btnConnect_bob.setText("Connect");
						   sock_bob = null; 
						   check_bob = true;
					   }
				}
				catch( Exception e3 ) {
				   //System.out.println( "Error: " + e3.toString() );
				   txtArea_bobMsgs.append("Error in connecting \\ disconnecting\n");
				   sock_bob = null;    
				}
			}
		});
		
		btnConnect_bob.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnConnect_bob.setBounds(296, 29, 175, 37);
		contentPane.add(btnConnect_bob);
		
		txtMessage_bob = new JTextField();
		txtMessage_bob.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtMessage_bob.setBounds(10, 96, 276, 23);
		contentPane.add(txtMessage_bob);
		txtMessage_bob.setColumns(10);
		
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
					txtArea_bobMsgs.append(data+"\n");
		    }
		   };
		   Timer timer = new Timer();
		   timer.schedule( task , new Date(), 1000 );
		
		
		// 'Send' button action
		JButton btnSend_bob = new JButton("Send message");
		btnSend_bob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = txtMessage_bob.getText();
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
				FileWriter plaintext_bob = null;
				try {
					who = new FileWriter("who_sent_msg.txt");
					who.write("bob");
					who.close();
					myWriter = new FileWriter("cipher.txt");
					myWriter.write(choice_bob.getSelectedItem());
					myWriter.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				try {
					if( sock_bob != null ) {
					   String message = msg;
					   writeSock_bob.println( message );
					   String dataRead = null;
					   try {
						   plaintext_bob = new FileWriter("plaintext.txt");
						   plaintext_bob.write("Bob: "+ msg+"\n");
						   plaintext_bob.close();
						   txtMessage_bob.setText("");
						   dataRead = readSock_bob.readLine();
					   } catch (IOException e1) {
						   txtMessage_bob.setText("");
						   txtArea_bobMsgs.append("Error in receiving data from the server!\n");
					   }
					   txtArea_bobMsgs.append(dataRead+"\n");
					   if(msg.equals("quit")) {
							btnConnect_bob.setText("Connect");
							sock_bob = null;
							txtArea_bobMsgs.append("Disconnected!\n");
							check_bob = true;
						}
					} else {
						txtMessage_bob.setText("");
						txtArea_bobMsgs.append("You are not connected\n");
					}
				} catch (Exception e2) {
					txtMessage_bob.setText("");
					txtArea_bobMsgs.append("You are not connected\n");
				}
				lineNum = getWrappedLines(txtArea_bobMsgs);
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
		
		btnSend_bob.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSend_bob.setBounds(298, 108, 173, 37);
		contentPane.add(btnSend_bob);
		
		JScrollPane scrollPane_bobMsgs = new JScrollPane();
		scrollPane_bobMsgs.setBounds(10, 162, 458, 212);
		contentPane.add(scrollPane_bobMsgs);
		scrollPane_bobMsgs.setViewportView(txtArea_bobMsgs);
		
		JLabel lblBob = new JLabel("Bob:");
		lblBob.setBounds(10, 77, 53, 24);
		contentPane.add(lblBob);
		
		choice_bob = new Choice();
		choice_bob.setBounds(10, 125, 277, 31);
		choice_bob.add("Block Cipher");
		choice_bob.add("Stream Cipher");
		choice_bob.add("RSA");
		contentPane.add(choice_bob);
		
	}
	
	public static int getWrappedLines(JTextArea component)
	{
		View view = component.getUI().getRootView(component).getView(0);
		int preferredHeight = (int)view.getPreferredSpan(View.Y_AXIS);
		int lineHeight = component.getFontMetrics( component.getFont() ).getHeight();
		return preferredHeight / lineHeight;
	}
}
