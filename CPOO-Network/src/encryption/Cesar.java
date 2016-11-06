package encryption;

import message.*;

public class Cesar implements EncryptionAlgorithm {

	private String transform(String str, int decal) {
		StringBuilder strB = new StringBuilder(str);
		int j;
		String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		for (int i = 0; i < strB.length(); i++) {
			j = ALPHABET.indexOf(strB.charAt(i));
			if (j > -1) {
				strB.setCharAt(i, ALPHABET.charAt((j + decal + 26) % 26));
			}
		}
		return strB.toString();
	}

	public Messagec cypher(Messaged md, String key) {
		return new Messagec(transform(md.getMessage(), Integer.parseInt(key)));
	}

	public Messaged decypher(Messagec mc, String key) {
		return new Messaged(transform(mc.getMessage(), Integer.parseInt("-" + key)));
	}
}