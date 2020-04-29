/*
 * this class is for chuck's block cipher's encryption
 * depending on the chosen attack, the UI will change
 */

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.security.PublicKey;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class chuck_block extends JFrame {

	protected static final int ServerSocket = 0;
	private JPanel contentPane;
	protected ServerSocket servSock;
	
	ReadFiles read = new ReadFiles();
    RSA_1 rsaKey = new RSA_1();
	PublicKey publicKey;
	private JTextField textField_cipher;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					chuck_block frame = new chuck_block();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings("static-access")
	public chuck_block() {
		
		// setting up the UI elements
		setFont(new Font("Tahoma", Font.PLAIN, 12));
		setTitle("Crypto Project");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 350, 456);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Cipher Used:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 14, 84, 14);
		contentPane.add(lblNewLabel);
		
		textField_cipher = new JTextField();
		textField_cipher.setBounds(104, 9, 220, 27);
		textField_cipher.setText(read.getCipher());
		contentPane.add(textField_cipher);
		textField_cipher.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Key:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(10, 57, 109, 14);
		contentPane.add(lblNewLabel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 82, 314, 66);
		contentPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("key.txt"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		 
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				textArea.append(line+"\n");
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JLabel lblNewLabel_2 = new JLabel("All Messages:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(10, 165, 148, 16);
		contentPane.add(lblNewLabel_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 192, 314, 214);
		contentPane.add(scrollPane_1);
		
		JTextArea textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		
		BufferedReader br1 = null;
		try {
			br1 = new BufferedReader(new FileReader("messages.txt"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		 
		String line1 = null;
		try {
			while ((line1 = br1.readLine()) != null) {
				textArea_1.append(line1+"\n");
			}
			br1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
