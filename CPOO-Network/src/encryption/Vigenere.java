package encryption;

import message.*;

public class Vigenere implements EncryptionAlgorithm {

	private int intOfChar(char c) {
		return Character.getNumericValue(c) - Character.getNumericValue('A');
	}

	private String transform(String str, String key, int cypher) {

		StringBuilder strB = new StringBuilder(str);
		int j;
		char c_str, c_key;
		String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		for (int i = 0; i < strB.length(); i++) {
			c_str = strB.charAt(i);
			j = ALPHABET.indexOf(c_str);
			if (j > -1) {
				c_key = key.charAt(i % key.length());
				strB.setCharAt(i, ALPHABET.charAt((intOfChar(c_str) + cypher * intOfChar(c_key) + 26) % 26));
			}
		}
		return strB.toString();
	}

	public Messagec cypher(Messaged md, String key) {
		return new Messagec(transform(md.getMessage(), key, 1));
	}

	public Messaged decypher(Messagec mc, String key) {
		return new Messaged(transform(mc.getMessage(), key, -1));
	}
}
