// importing the required packages
import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.io.File; 
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.util.Scanner;
import java.awt.Choice;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class attack_mode_chooser extends JFrame {

	// initializing the variables
	static attack_mode_chooser frame;
	protected static final int ServerSocket = 0;
	private JPanel contentPane;
	protected ServerSocket servSock;
	Choice choice;
	FileWriter myWriter = null;
	ReadFiles read = new ReadFiles();
    File myObj = null;
    Scanner myReader = null;
	private String data;
	static JButton btn_chuck = null;
	JTextArea textArea_errors = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new attack_mode_chooser();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// setting up the user interface
	public attack_mode_chooser() {
		setFont(new Font("Tahoma", Font.PLAIN, 12));
		setTitle("Crypto Project");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 389, 312);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		choice = new Choice();
		choice.setBounds(24, 33, 181, 20);
		choice.add("Known PlainText Attack");
		choice.add("CipherText Only Attack");
		choice.add("Chosen PlainText Attack");
		choice.add("Chosen CipherText Attack");
		contentPane.add(choice);
		
		JButton btnNewButton = new JButton("Select Attack");
		btnNewButton.setBounds(211, 21, 154, 39);
		contentPane.add(btnNewButton);
		
		// clicking on button "Alice" will open Alice's UI
		JButton btn_alice = new JButton("Alice");
		btn_alice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alice a = new alice();
				a.setVisible(true);
			}
		});
		btn_alice.setBounds(24, 212, 89, 23);
		contentPane.add(btn_alice);
		
		// clicking on button "Bob" will open Bob' UI
		JButton btn_bob = new JButton("Bob");
		btn_bob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bob b = new bob();
			    b.setVisible(true);
			}
		});
		btn_bob.setBounds(136, 212, 89, 23);
		contentPane.add(btn_bob);
		
		btn_chuck = new JButton("Chuck");
		btn_chuck.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				myObj = new File("count.txt");
		    	  try {
						myReader = new Scanner(myObj);
						while (myReader.hasNextLine()) {
							  data = myReader.nextLine();
							}
						myReader.close();
					} catch (FileNotFoundException e12345) {}
					
					try {
						if(Integer.parseInt(data) > 7 && data != null) {
							textArea_errors.append("Chuck can now decrypt\n");
							if(read.getCipher().equals("Block Cipher")) {
								
								// checking the attacks and cipher that is chosen
								if(read.getAttackName().equals("Known PlainText Attack")) {
									chuck_block cb = null;
									cb = new chuck_block();
									cb.setVisible(true);
								} else if (read.getAttackName().equals("CipherText Only Attack")) {
									chuck c = new chuck();
								    c.setVisible(true);
								} else if (read.getAttackName().equals("Chosen PlainText Attack")) {
									chuck c = new chuck();
								    c.setVisible(true);
								} else if (read.getAttackName().equals("Chosen CipherText Attack")) {
									chuck c = new chuck();
								    c.setVisible(true);
								}
							} else if (read.getCipher().equals("Stream Cipher")) {
								if(read.getAttackName().equals("Known PlainText Attack")) {
									chuck_stream cs = null;
									try {
										cs = new chuck_stream();
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									cs.setVisible(true);
								} else if (read.getAttackName().equals("CipherText Only Attack")) {
									chuck c = new chuck();
								    c.setVisible(true);
								} else if (read.getAttackName().equals("Chosen PlainText Attack")) {
									chuck c = new chuck();
								    c.setVisible(true);
								} else if (read.getAttackName().equals("Chosen CipherText Attack")) {
									chuck c = new chuck();
								    c.setVisible(true);
								}
							} else if (read.getCipher().equals("RSA")) {
								if(read.getAttackName().equals("Known PlainText Attack")) {
									chuck_RSA cr = null;
									try {
										cr = new chuck_RSA();
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									cr.setVisible(true);
								} else if (read.getAttackName().equals("CipherText Only Attack")) {
									chuck c = new chuck();
								    c.setVisible(true);
								} else if (read.getAttackName().equals("Chosen PlainText Attack")) {
									chuck c = new chuck();
								    c.setVisible(true);
								} else if (read.getAttackName().equals("Chosen CipherText Attack")) {
									chuck c = new chuck();
								    c.setVisible(true);
								}
							}
						} else {
							textArea_errors.append("Not enough messages for chuck to figure out the key\n");
						}
					} catch (Exception err) {
						textArea_errors.append("Not enough messages for chuck to figure out the key\n");
					}
			}
		});
		btn_chuck.setBounds(242, 212, 89, 23);
		contentPane.add(btn_chuck);
		
		// UI elements
		JLabel lblNewLabel = new JLabel("               (Click on the buttons to open screens)");
		lblNewLabel.setBounds(34, 239, 307, 23);
		contentPane.add(lblNewLabel);
		
		btn_alice.setEnabled(false);
		btn_bob.setEnabled(false);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 122, 307, 71);
		contentPane.add(scrollPane);
		
		textArea_errors = new JTextArea();
		scrollPane.setViewportView(textArea_errors);
		
		JLabel lblNewLabel_1 = new JLabel("Log:");
		lblNewLabel_1.setBounds(25, 105, 48, 14);
		contentPane.add(lblNewLabel_1);
		btn_chuck.setEnabled(false);
		
		// create or re initiate some files when clicked on the "Select Attack" button
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 

				// creating File objects
				File myObj = new File("choice.txt");
				File myObj3 = new File("cipher.txt");
				File m = new File("messages.txt");
				File p = new File("plaintext.txt");
				File count_file = new File("count.txt");
				
				// checking if the file exist, then delete it, or else continue
				if(m.exists()){
					m.delete();
					try {
						m.createNewFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if(count_file.exists()){
					count_file.delete();
					try {
						count_file.createNewFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if(p.exists()){
					p.delete();
					try {
						p.createNewFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
		    	try {
					myObj.createNewFile();
					myObj3.createNewFile();
		    	} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					// writing into the files
					myWriter = new FileWriter("choice.txt");
					myWriter.write(choice.getSelectedItem());
					myWriter.close();
					btn_bob.setEnabled(true);
					btn_alice.setEnabled(true);
					btn_chuck.setEnabled(true);
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		});
	}	
}
