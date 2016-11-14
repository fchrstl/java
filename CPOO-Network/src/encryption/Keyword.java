package encryption;

import message.*;
import java.util.List;
import java.util.ArrayList;

/**
 * The Keyword cipher substitutes each letter of the alphabet by a letter in the
 * same rank from another alphabet, the latter alphabet having been created by
 * juxtaposing the letters of a keyword - in its order and without repetition:
 * "ANANAS" becomes "ANS" - to the remaining letters of the alphabet. For
 * instance, if we pick the keyword "JAVA", the new alphabet for transcription
 * is given as:
 * <p>
 * J A V B C D E F G H I K L M N O P Q R S T U W X Y Z
 * <p>
 * This cypher method is not case-sensitive regarding the keyword: upper (resp.
 * lower) case characters are transformed using upper (resp. lower) case
 * alphabet and keyword.
 * <p>
 * Only the upper and lower case letter are transformed, the others are left
 * untouched, and removed by the class' methods if contained in the keyword:
 * "Midi-Pyrénées" becomes the keyword "MIDPYRNES"/"midpyrnes".
 * 
 * @author Benjamin GUIGNARD
 * @author Félix CHRISTELLE
 * 
 * @version 1.0
 */

public class Keyword implements EncryptionAlgorithm {

	private String createCode(String code) {
		String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		// Creation of the code string
		List<Character> code_list = new ArrayList<Character>();

		for (char c : (code.toUpperCase() + ALPHABET).toCharArray()) {
			if (!(code_list.contains(c))) {
				code_list.add(c);
			}
		}
		StringBuilder codeB = new StringBuilder();
		for (Character c : code_list) {
			codeB.append(c);
		}
		return codeB.toString();
	}

	private String transform(String alphabet, String code, String str) {
		StringBuilder strB = new StringBuilder(str);
		int j;
		for (int i = 0; i < strB.length(); i++) {
			j = alphabet.indexOf(strB.charAt(i));
			if (j > -1) {
				strB.setCharAt(i, code.charAt(j));
			}
			j = alphabet.toLowerCase().indexOf(strB.charAt(i));
			if (j > -1) {
				strB.setCharAt(i, code.toLowerCase().charAt(j));
			}
		}
		return strB.toString();
	}

	/**
	 * Encrypts the message with the Keyword cypher. Only conventional upper
	 * case A-Z and lower case a-z letters will be transformed.
	 * 
	 * @param md
	 *            the message to be transformed.
	 * @param key
	 *            the cypher keyword
	 * @return the encrypted message
	 */
	public Messagec cypher(Messaged md, String key) {
		return new Messagec(transform("ABCDEFGHIJKLMNOPQRSTUVWXYZ", createCode(key), md.getMessage()));
	}

	/**
	 * Decrypts the message with the Keyword cypher. Only conventional upper
	 * case A-Z and lower case a-z letters will be transformed.
	 * 
	 * @param mc
	 *            the message to be transformed.
	 * @param key
	 *            the cypher keyword
	 * @return the encrypted message
	 */
	public Messaged decypher(Messagec mc, String key) {
		return new Messaged(transform(createCode(key), "ABCDEFGHIJKLMNOPQRSTUVWXYZ", mc.getMessage()));
	}
}
