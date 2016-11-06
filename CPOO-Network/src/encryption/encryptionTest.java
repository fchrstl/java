package encryption;

import static org.junit.Assert.*;
import org.junit.Test;
import message.*;

/**
 * @author Benjamin GUIGNARD
 * @author Félix CHRISTELLE
 * @version 1.0, 11/06/2016
 * @since 1.0
 */

public class encryptionTest {

	public static String TEXT = "ABCDEFGHIJKLMNOPQRSTUVWXYZ a1.è";

	@Test
	public void testAtbash() {
		String expectedCypher = "ZYXWVUTSRQPONMLKJIHGFEDCBA a1.è";

		EncryptionAlgorithm atbash = new Atbash();

		String mc = atbash.cypher(new Messaged(TEXT), "").getMessage();
		String md = atbash.decypher(new Messagec(mc), "").getMessage();

		assertEquals(mc, expectedCypher);
		assertEquals(md, TEXT);
	}

	@Test
	public void testCesar() {
		String expectedCypher = "DEFGHIJKLMNOPQRSTUVWXYZABC a1.è";

		EncryptionAlgorithm cesar = new Cesar();

		String mc = cesar.cypher(new Messaged(TEXT), "3").getMessage();
		String md = cesar.decypher(new Messagec(mc), "3").getMessage();

		assertEquals(mc, expectedCypher);
		assertEquals(md, TEXT);
	}

	@Test
	public void testKeyword() {
		String expectedCypher = "KRYPTOSABCDEFGHIJLMNQUVWXZ a1.è";

		EncryptionAlgorithm keyword = new Keyword();

		String mc = keyword.cypher(new Messaged(TEXT), "KRYPTOS").getMessage();
		String md = keyword.decypher(new Messagec(mc), "KRYPTOS").getMessage();

		assertEquals(mc, expectedCypher);
		assertEquals(md, TEXT);
	}

	@Test
	public void testRottreize() {
		String expectedCypher = "NOPQRSTUVWXYZABCDEFGHIJKLM a1.è";

		EncryptionAlgorithm rottreize = new Rottreize();

		String mc = rottreize.cypher(new Messaged(TEXT), "").getMessage();
		String md = rottreize.decypher(new Messagec(mc), "").getMessage();

		assertEquals(mc, expectedCypher);
		assertEquals(md, TEXT);
	}

	@Test
	public void testVigenere() {
		String expectedCypher = "KSASXTYRZHZEAFYGOGLHMFNVNS a1.è";

		EncryptionAlgorithm vigenere = new Vigenere();

		String mc = vigenere.cypher(new Messaged(TEXT), "KRYPTOS").getMessage();
		String md = vigenere.decypher(new Messagec(mc), "KRYPTOS").getMessage();

		assertEquals(mc, expectedCypher);
		assertEquals(md, TEXT);
	}
}
