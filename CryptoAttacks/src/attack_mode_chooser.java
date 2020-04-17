import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.io.File;  // Import the File class
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.awt.Choice;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class attack_mode_chooser extends JFrame {

	static attack_mode_chooser frame;
	protected static final int ServerSocket = 0;
	private JPanel contentPane;
	protected ServerSocket servSock;
	Choice choice;
	FileWriter myWriter = null;
	info i = new info();
	private JLabel lblInstruction;
	private JLabel lblInstruction_1;

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

	public attack_mode_chooser() {
		setFont(new Font("Tahoma", Font.PLAIN, 12));
		setTitle("Crypto Project");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 494, 261);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		choice = new Choice();
		choice.setBounds(97, 20, 277, 31);
		choice.add("Known PlainText Attack");
		choice.add("CipherText Only Attack");
		choice.add("Chosen PlainText Attack");
		choice.add("Chosen CipherText Attack");
		contentPane.add(choice);
		
		JButton btnNewButton = new JButton("Select");
		btnNewButton.setBounds(184, 151, 89, 60);
		contentPane.add(btnNewButton);
		
		lblInstruction = new JLabel("(On clicking on \"Select\" button, all three user interfaces for Alice, Bob, and");
		lblInstruction.setBounds(23, 112, 445, 14);
		contentPane.add(lblInstruction);
		
		lblInstruction_1 = new JLabel("Chuck will be opened and selected attack mode will be performed)");
		lblInstruction_1.setBounds(44, 126, 400, 14);
		contentPane.add(lblInstruction_1);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				frame.setVisible(false);
				File myObj = new File("choice.txt");
				File myObj1 = new File("alice_msg.txt");
				File myObj2 = new File("bob_msg.txt");
				File myObj3 = new File("cipher.txt");

		    	try {
					myObj.createNewFile();
					myObj1.createNewFile();
					myObj2.createNewFile();
					myObj3.createNewFile();

		    	} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					myWriter = new FileWriter("choice.txt");
					myWriter.write(choice.getSelectedItem());
					myWriter.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				alice a = new alice();
				bob b = new bob();
				chuck c = new chuck();
				a.setVisible(true);
			    b.setVisible(true);
			    c.setVisible(true);
			}
		});
	}	
}
