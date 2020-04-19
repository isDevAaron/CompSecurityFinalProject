import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JTextField;
import java.net.ServerSocket;
import java.security.PublicKey;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class chuck_stream extends JFrame {

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
					chuck_stream frame = new chuck_stream();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings("static-access")
	public chuck_stream() throws IOException {
		setFont(new Font("Tahoma", Font.PLAIN, 12));
		setTitle("Crypto Project");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 350, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Key:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 36, 48, 14);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane_key = new JScrollPane();
		scrollPane_key.setBounds(10, 61, 314, 54);
		contentPane.add(scrollPane_key);
		
		JTextArea textArea_key = new JTextArea();
		scrollPane_key.setViewportView(textArea_key);
		BufferedReader br = new BufferedReader(new FileReader("key.txt"));
		 
		String line = null;
		while ((line = br.readLine()) != null) {
			textArea_key.append(line+"\n");
		}
		br.close();
		
		JLabel lblNewLabel_1 = new JLabel("Decrypted Messages:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(10, 117, 235, 16);
		contentPane.add(lblNewLabel_1);
		
		JScrollPane scrollPane_decrypted = new JScrollPane();
		scrollPane_decrypted.setBounds(10, 144, 314, 227);
		contentPane.add(scrollPane_decrypted);
		
		JTextArea textArea_decrypted = new JTextArea();
		scrollPane_decrypted.setViewportView(textArea_decrypted);
		BufferedReader br1 = new BufferedReader(new FileReader("messages.txt"));
		 
		String line1 = null;
		while ((line1 = br1.readLine()) != null) {
			textArea_decrypted.append(line1+"\n");
		}
		br1.close();
		
		JLabel lblNewLabel_2 = new JLabel("Cipher Used:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(10, 11, 96, 14);
		contentPane.add(lblNewLabel_2);
		
		textField_cipher = new JTextField();
		textField_cipher.setBounds(93, 6, 231, 27);
		textField_cipher.setText(read.getCipher());
		contentPane.add(textField_cipher);
		textField_cipher.setColumns(10);
		
	}
}
