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
public class chuck_RSA extends JFrame {

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
					chuck_RSA frame = new chuck_RSA();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings("static-access")
	public chuck_RSA() throws IOException {
		setFont(new Font("Tahoma", Font.PLAIN, 12));
		setTitle("Crypto Project");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 350, 449);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Chuck will use this public key and develope");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 45, 314, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Decrypted Messages:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(10, 190, 235, 16);
		contentPane.add(lblNewLabel_1);
		
		JScrollPane scrollPane_decrypted = new JScrollPane();
		scrollPane_decrypted.setBounds(10, 217, 314, 189);
		contentPane.add(scrollPane_decrypted);
		
		JTextArea textArea_decrypted = new JTextArea();
		scrollPane_decrypted.setViewportView(textArea_decrypted);
		BufferedReader br1 = new BufferedReader(new FileReader("messages.txt"));
		 
		String line1 = null;
		while ((line1 = br1.readLine()) != null) {
			textArea_decrypted.append(line1+"\n");
		}
		br1.close();
		
		JScrollPane scrollPane_key = new JScrollPane();
		scrollPane_key.setBounds(10, 89, 314, 90);
		contentPane.add(scrollPane_key);
		
		JTextArea textArea_key = new JTextArea();
		scrollPane_key.setViewportView(textArea_key);
		BufferedReader br = new BufferedReader(new FileReader("key.txt"));
		 
		String line = null;
		while ((line = br.readLine()) != null) {
			textArea_key.append(line+"\n");
		}
		br.close();
		
		JLabel lblAnAlgorithmTo = new JLabel("an algorithm to decrypt the messages:");
		lblAnAlgorithmTo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAnAlgorithmTo.setBounds(10, 62, 314, 16);
		contentPane.add(lblAnAlgorithmTo);
		
		JLabel lblNewLabel_2 = new JLabel("Cipher Used: ");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(10, 20, 107, 14);
		contentPane.add(lblNewLabel_2);
		
		textField_cipher = new JTextField();
		textField_cipher.setBounds(99, 16, 225, 25);
		textField_cipher.setText(read.getCipher());
		contentPane.add(textField_cipher);
		textField_cipher.setColumns(10);
		
	}
}
