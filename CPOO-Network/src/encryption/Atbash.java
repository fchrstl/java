package encryption;

import message.*;

public class Atbash implements EncryptionAlgorithm {

	private String transform(String str) {
		StringBuilder strB = new StringBuilder(str);
		int j;
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		for (int i = 0; i < strB.length(); i++) {
			j = alphabet.indexOf(strB.charAt(i));
			if (j > -1) {
				strB.setCharAt(i, alphabet.charAt(25 - j));
			}
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