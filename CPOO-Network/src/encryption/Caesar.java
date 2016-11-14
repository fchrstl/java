package encryption;

import message.*;

/**
 * The Caesar cipher algorithm transforms a plain text by shifting the places of
 * the letters in the alphabet to the left. For instance, if we use a sift of 3:
 * <p>
 * A B C D E F G H I J K L M N O P Q R S T U V W Y Z
 * <p>
 * becomes
 * <p>
 * D E F G H I J K L M N O P Q R S T U V W Y Z A B C
 * <p>
 * Only the upper and lower case letter characters are transformed, the others
 * are left untouched.
 * 
 * @author Benjamin GUIGNARD
 * @author Félix CHRISTELLE
 * 
 * @version 1.0
 */

public class Caesar implements EncryptionAlgorithm {

	private String transform(String str, int decal) {
		StringBuilder strB = new StringBuilder(str);
		int j;
		String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String alphabet = ALPHABET.toLowerCase();

		for (int i = 0; i < strB.length(); i++) {

			j = ALPHABET.indexOf(strB.charAt(i));
			if (j > -1) {
				strB.setCharAt(i, ALPHABET.charAt((j + decal + 26) % 26));
			}
			j = alphabet.indexOf(strB.charAt(i));
			if (j > -1) {
				strB.setCharAt(i, alphabet.charAt((j + decal + 26) % 26));
			}
		}
		return strB.toString();
	}

	/**
	 * Encrypts the message with the Caesar cypher. Only conventional upper case
	 * A-Z and lower case a-z letters will be transformed.
	 * 
	 * @param md
	 *            the message to be transformed.
	 * @param key
	 *            the shift for the substitution
	 * @return the encrypted message
	 */
	public Messagec cypher(Messaged md, String key) {
		return new Messagec(transform(md.getMessage(), Integer.parseInt(key)));
	}

	/**
	 * Decrypts the message with the Caesar cypher. Only conventional upper case
	 * A-Z and lower case a-z letters will be transformed.
	 * 
	 * @param mc
	 *            the message to be transformed.
	 * @param key
	 *            the shift for the substitution
	 * @return the encrypted message
	 */
	public Messaged decypher(Messagec mc, String key) {
		return new Messaged(transform(mc.getMessage(), Integer.parseInt("-" + key)));
	}
}