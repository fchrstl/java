package encryption;

import message.*;

/**
 * 
 * @author Benjamin GUIGNARD, F�lix CHRISTELLE
 *
 */

public class Atbash implements EncryptionAlgorithm {

	public String transform(String str) {
		StringBuilder strB = new StringBuilder(str);
		int j;
		String alphabet = "abcdefghijklmnopqrstuvwxyz";

		for (int i = 0; i < strB.length(); i++) {
			j = alphabet.indexOf(strB.charAt(i));
			if (j > -1) {
				strB.setCharAt(i, alphabet.charAt(25 - j));
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
