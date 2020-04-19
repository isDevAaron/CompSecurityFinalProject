import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PublicKey;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Choice;

@SuppressWarnings("serial")
public class chuck extends JFrame {

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
	@SuppressWarnings("unused")
	private JTextArea txtArea;
	Choice choice = new Choice();
	ReadFiles read = new ReadFiles();
    RSA_1 rsaKey = new RSA_1();
	PublicKey publicKey;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					chuck frame = new chuck();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings("static-access")
	public chuck() {
		setFont(new Font("Tahoma", Font.PLAIN, 12));
		setTitle("Crypto Project");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 494, 456);
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
		JTextArea txtArea_chuckMsgs = new JTextArea();
		
		
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
						   txtArea_chuckMsgs.append("Connected to Server\n");
						   btnConnect.setText("Disconnect");
						   check = false;
					   } else {
						   txtArea_chuckMsgs.append("Disconnected!\n");
						   btnConnect.setText("Connect");
						   sock = null; 
						   check = true;
					   }
					}
					catch( Exception e3 ) {
					   //System.out.println( "Error: " + e3.toString() );
					   txtArea_chuckMsgs.append("Error in connecting \\ disconnecting\n");
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
		
		// 'Send' button action
		JButton btnSend = new JButton("Send message");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = txtMessage.getText();
				FileWriter myWriter = null;
				try {
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
						   if(read.getAttackName().equals("Known PlainText Attack")) {
							   try {
								   txtArea_chuckMsgs.append("Partial PlainText:   " + msg.charAt(0)+msg.charAt(1)+msg.charAt(2) + "\n");
							   } catch(Exception ee) {
								   txtArea_chuckMsgs.append("Partial PlainText:   " + msg.charAt(0) + "\n");
							   }
							   txtMessage.setText("");
							   dataRead = readSock.readLine();
						   } else if (read.getAttackName().equals("Chosen CipherText Attack")){
							   txtArea_chuckMsgs.append("PlainText [SENT BY SERVER]: " + msg + "\n");
							   txtMessage.setText("");
							   dataRead = readSock.readLine();
						   } else {
							   txtArea_chuckMsgs.append("PlainText:\t" + msg + "\n");
							   txtMessage.setText("");
							   dataRead = readSock.readLine();
						   }
					   } catch (IOException e1) {
						   txtMessage.setText("");
						   txtArea_chuckMsgs.append("Error in receiving data from the server!\n");
					   }
					   txtArea_chuckMsgs.append(dataRead+"\n");
					   if(msg.equals("quit")) {
							btnConnect.setText("Connect");
							sock = null;
							txtArea_chuckMsgs.append("Disconnected!\n");
							check = true;
						}
					} else {
						txtMessage.setText("");
						txtArea_chuckMsgs.append("You are not connected\n");
					}
				} catch (Exception e2) {
					txtMessage.setText("");
					txtArea_chuckMsgs.append("You are not connected\n");
				}
			}
		});
		
		btnSend.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSend.setBounds(298, 108, 173, 37);
		contentPane.add(btnSend);
		
		JScrollPane scrollPane_chuckMsgs = new JScrollPane();
		scrollPane_chuckMsgs.setBounds(10, 162, 458, 180);
		contentPane.add(scrollPane_chuckMsgs);
		scrollPane_chuckMsgs.setViewportView(txtArea_chuckMsgs);
		
		JLabel lblAlice = new JLabel("Chuck:");
		lblAlice.setBounds(10, 77, 53, 24);
		contentPane.add(lblAlice);
		
		choice.setBounds(10, 125, 277, 31);
		choice.add("Block Cipher");
		choice.add("Stream Cipher");
		choice.add("RSA");
		contentPane.add(choice);
		
		JButton decryptBtn = new JButton("Click here to decrypt messages between Alice and Bob");
		decryptBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chuck_RSA r = null;
				chuck_block b = null;
				chuck_stream s = null;
				try {
					s = new chuck_stream();
					r = new chuck_RSA();
					b = new chuck_block();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				if(read.getCipher().equals("RSA")){
					r.setVisible(true);
				} else if (read.getCipher().equals("Block Cipher")) {
					b.setVisible(true);
				} else if (read.getCipher().equals("Stream Cipher")) {
					s.setVisible(true);
				}
			}
		});
		decryptBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		decryptBtn.setBounds(10, 364, 458, 42);
		contentPane.add(decryptBtn);
		
		
	}
}







