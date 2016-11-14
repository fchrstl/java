package encryption;

import message.*;

/**
 * The Atbash encryption algorithm works by substituting the first letter of an
 * alphabet for the last letter, the second letter for the second to last and so
 * on, effectively reversing the alphabet; each letter from the first row of the
 * following table is substituted by the one of the same column on the second
 * row:
 * <p>
 * A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
 * <p>
 * Z Y X W V U T S R Q P O N M L K J I H G F E D C B A
 * <p>
 * Only the upper and lower case letter characters are transformed, the others
 * are left untouched.
 * <p>
 * The methods {@link EncryptionAlgorithm#cypher(Messaged, String)} and
 * {@link EncryptionAlgorithm#decypher(Messagec, String)} both expect a
 * <code>String</code> second argument which is exceptionally useless here. The
 * user may then call them with any <code>String</code> value. However, for
 * clarity purpose and not mistake the cypher method with another one, the use
 * of <code>""</code> is advised.
 * 
 * @author Benjamin GUIGNARD
 * @author Félix CHRISTELLE
 * 
 * @version 1.0
 */

public class Atbash implements EncryptionAlgorithm {

	private String transform(String str) {
		StringBuilder strB = new StringBuilder(str);
		int j;
		String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String alphabet = ALPHABET.toLowerCase();

		for (int i = 0; i < strB.length(); i++) {
			j = ALPHABET.indexOf(strB.charAt(i));
			if (j > -1) {
				strB.setCharAt(i, ALPHABET.charAt(25 - j));
			}
			j = alphabet.indexOf(strB.charAt(i));
			if (j > -1) {
				strB.setCharAt(i, alphabet.charAt(25 - j));
			}
		}
		return strB.toString();
	}

	/**
	 * Encrypts the message with the Atbash cypher. Only conventional upper case
	 * A-Z and lower case a-z letters will be transformed.
	 * 
	 * @param md
	 *            the message to be transformed.
	 * @param key
	 *            an irrelevant <code>String</code>. May take any value here.
	 * @return the encrypted message
	 */
	public Messagec cypher(Messaged md, String key) {
		return new Messagec(transform(md.getMessage()));
	}

	/**
	 * Decrypts the message with the Atbash cypher.
	 * 
	 * @param mc
	 *            the message to be transformed.
	 * @param key
	 *            an irrelevant <code>String</code>. May take any value here.
	 * @return the decrypted message
	 */
	public Messaged decypher(Messagec mc, String key) {
		return new Messaged(transform(mc.getMessage()));
	}
}