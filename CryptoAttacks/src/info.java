
public class info {
	
	static String[] chosenAttack = new String[1];
	public static void chosenAttack(String attack) {
		chosenAttack[0] = attack;
	}
	
	
	public static String[] getChosen() {
		System.out.println("-------------");
		System.out.println(chosenAttack);
		return chosenAttack;
	}
}
