import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class ReadFiles {
		
    /*public static void main(String[] args) {
    	System.out.println(getCipher());
    }*/

	public static String getAttackName() {
		String data = "";
		try {
			File myObj = new File("choice.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
			  data = myReader.nextLine();
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return data;
	}
	
	public static String getCipher() {
		String data = "";
		try {
			File myObj = new File("cipher.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
			  data = myReader.nextLine();
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return data;
	}
	
	public static String whoSentMsg() {
		String data = "";
		try {
			File myObj = new File("who_sent_msg.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
			  data = myReader.nextLine();
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return data;
	}
	
}
