package encryption;

import message.*;

/**
 * The Vigenere cipher generalizes the Caesar cipher: in this algorithm, the
 * Caesar's shift for substituting a letter in position <i>i</i> in a text is
 * the rank in the usual alphabet of the letter in position <i>i</i> in a string
 * made of long enough repetition of the keyword given with the message to
 * cypher.
 * <p>
 * By convention, the position of the character to transform in the plain text
 * is the one without taking account of the blank spaces, as if all the words
 * were attached.
 * <p>
 * For instance, if we are given the message "Give me your answer" with the
 * keyword "Daisy", and we want to transform the character 'm' in position 5,
 * then we take its position in the string without spacing (
 * "Give<b>m</b>eyourandswer"): 4, we take the fifth letter from the repetition
 * of keywords "Dais<b>y</b>Daisy...": 'y', which is the 25th letter of the
 * alphabet, therefore we apply a Caesar cypher with a shift of 24 on 'm', and
 * eventually obtain 'o'.
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
 * 
 * @see Caesar
 */

public class Vigenere implements EncryptionAlgorithm {

	private int intOfChar(char c) {
		return Character.getNumericValue(c) - Character.getNumericValue('A');
	}

	private String transform(String str, String key, int cypher) {

		StringBuilder strB = new StringBuilder(str);
		int j;
		char c_str, c_key;
		String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String alphabet = ALPHABET.toLowerCase();

		int i = 0;
		for (int i0 = 0; i0 < strB.length(); i0++) {
			c_str = strB.charAt(i0);
			j = ALPHABET.indexOf(c_str);
			if (j > -1) {
				c_key = key.toUpperCase().charAt(i % key.length());
				strB.setCharAt(i0, ALPHABET.charAt((intOfChar(c_str) + cypher * intOfChar(c_key) + 26) % 26));
			}
			j = alphabet.indexOf(c_str);
			if (j > -1) {
				c_key = key.toLowerCase().charAt(i % key.length());
				strB.setCharAt(i0, alphabet.charAt((intOfChar(c_str) + cypher * intOfChar(c_key) + 26) % 26));
			}
			if (!(strB.charAt(i0) == ' ')) {
				i++;
			}
		}
		return strB.toString();
	}

	/**
	 * Encrypts the message with the Vigenere cypher. Only conventional upper
	 * case A-Z and lower case a-z letters will be transformed.
	 * 
	 * @param md
	 *            the message to be transformed.
	 * @param key
	 *            the cypher keyword
	 * @return the encrypted message
	 */
	public Messagec cypher(Messaged md, String key) {
		return new Messagec(transform(md.getMessage(), key, 1));
	}

	/**
	 * Decrypts the message with the Vigenere cypher. Only conventional upper
	 * case A-Z and lower case a-z letters will be transformed.
	 * 
	 * @param mc
	 *            the message to be transformed.
	 * @param key
	 *            the cypher keyword
	 * @return the encrypted message
	 */
	public Messaged decypher(Messagec mc, String key) {
		return new Messaged(transform(mc.getMessage(), key, -1));
	}
}
