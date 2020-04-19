import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.net.ServerSocket;
import java.security.PublicKey;
import javax.swing.JLabel;
import javax.swing.JTextField;

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
		
	}
}
