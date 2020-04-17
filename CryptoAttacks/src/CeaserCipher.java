public class CeaserCipher {
	
	// gets the index of the lower cased string
	public static int getIndex(char[] c, char s) {
	    int index = -1;
	    for (int i = 0; (i < c.length) && (index == -1); i++) {
	        if (c[i] == s) {
	            index = i;
	        }
	    }
	    return index;
	}
	
	// gets the index of the upper case string
	public static int getIndexUpper(char[] c, char s) {
	    int index = -1;
	    for (int i = 0; (i < c.length) && (index == -1); i++) {
	        if (c[i] == s) {
	            index = i;
	        }
	    }
	    return index;
	}
	
	public String encrypt(String s) {
		// arrays containing upper and lower cased alphabets
		char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		char[] Alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		
		// Ceaser Ciphers
		int[] arr = new int[]{ 5,19,19,5,19 };
		int j = 0;
		//char[] c = s.toCharArray();
		for(int i = 0; i<s.length(); i++) {
			// this if statement helps us re-iterate Ceaser Cipher array when reached the end
			if(j == arr.length) {
				j = 0;
			} 
			while(j < arr.length) {
				int p = 0;
				if(Character.isUpperCase(s.charAt(i))) {
					p = getIndexUpper(Alphabet, s.charAt(i));
				} else {
					p = getIndex(alphabet, s.charAt(i));
				}
				
				if(p == -1) {
					s = s.substring(0, i) + s.charAt(i) + s.substring(i + 1);
					break;
				}
				if(j < arr.length) {
					if(p+j > alphabet.length) {
						p = p - 25;
						if(Character.isUpperCase(s.charAt(i))) {
							s = s.substring(0, i) + Alphabet[p] + s.substring(i + 1); 
						} else {
							s = s.substring(0, i) + alphabet[p] + s.substring(i + 1); 
						}
					} else {
						int q = p+arr[j];
						if( q > alphabet.length ) {
							q = q - 26;
							if(Character.isUpperCase(s.charAt(i))) {
								s = s.substring(0, i) + Alphabet[q] + s.substring(i + 1);
							} else {
								s = s.substring(0, i) + alphabet[q] + s.substring(i + 1);
							}
							j++;
							break;
						} else {
							if(Character.isUpperCase(s.charAt(i))) {
								s = s.substring(0, i) + Alphabet[p+arr[j]] + s.substring(i + 1);
							} else {
								s = s.substring(0, i) + alphabet[p+arr[j]] + s.substring(i + 1);
							}
							j++;
							break;
						}
					}
				}
			}
		}
		return s;
	}
}
