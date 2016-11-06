package encryption;

import message.*;

/**
 * The Atbash encryption algorithm works by substituting the first letter of an
 * alphabet for the last letter, the second letter for the second to last and so
 * on, effectively reversing the alphabet : each letter from the first row of
 * the following table is substituted by the one of the same column on the
 * second row :
 * <p>
 * A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
 * <p>
 * Z Y X W V U T S R Q P O N M L K J I H G F E D C B A
 * <p>
 * Only the upper case letter characters are transformed, the others are left
 * untouched.
 * 
 * @author Benjamin GUIGNARD
 * @author Félix CHRISTELLE
 */

public class Atbash implements EncryptionAlgorithm {

	private String transform(String str) {
		StringBuilder strB = new StringBuilder(str);
		int j;
		String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		for (int i = 0; i < strB.length(); i++) {
			j = ALPHABET.indexOf(strB.charAt(i));
			if (j > -1) {
				strB.setCharAt(i, ALPHABET.charAt(25 - j));
			}
		}
		return strB.toString();
	}

	public Messagec cypher(Messaged md, String key) {
		return new Messagec(transform(md.getMessage()));
	}

	public Messaged decypher(Messagec mc, String key) {
		return new Messaged(transform(mc.getMessage()));
	}
}