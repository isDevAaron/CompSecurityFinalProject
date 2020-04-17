import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files


public class ReadFiles {
	
	static ArrayList<String> decryptedMsgs = new ArrayList<String>(); // Create an ArrayList object
	
    /*public static void main(String[] args) {
    	decMsgs("he i is him");
    	System.out.println("...............");
    	System.out.println(getDecrypted());
    }*/

	public static void decMsgs(String msgs) {
		System.out.println("/////////////////////");
		decryptedMsgs.add(msgs);
		System.out.println("himesh is the best");
		System.out.println("==========");
		System.out.println(msgs);
	}
	
	public static ArrayList<String> getDecrypted(){
		return decryptedMsgs;
	}

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
}
