/*
 * one of the most important classes. It is basically reading from the files
 * Instead of writing a read method multiple times, we simply created a class
 * which does it. In order to use it, we just create an object pointing to this class
 * and call the methods from there
 */

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class ReadFiles {

	// reading "choice.txt" file
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
	
	// reading "cipher.txt" file
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
	
	// reading "who_sent_msg.txt" file
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
