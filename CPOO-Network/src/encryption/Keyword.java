package encryption;

import message.*;
import java.util.List;
import java.util.ArrayList;

public class Keyword implements EncryptionAlgorithm {

	private String createCode(String code) {
		String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		// Creation of the code string
		List<Character> code_list = new ArrayList<Character>();

		for (char c : (code + ALPHABET).toCharArray()) {
			if (!(code_list.contains(c))) {
				System.out.print(c);
				code_list.add(c);
			}
		}
		StringBuilder codeB = new StringBuilder();
		for (Character c : code_list) {
			System.out.print(c);
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
		}
		return strB.toString();
	}

	public Messagec cypher(Messaged md, String key) {
		return new Messagec(transform("ABCDEFGHIJKLMNOPQRSTUVWXYZ", createCode(key), md.getMessage()));
	}

	public Messaged decypher(Messagec mc, String key) {
		return new Messaged(transform(createCode(key), "ABCDEFGHIJKLMNOPQRSTUVWXYZ", mc.getMessage()));
	}
}
