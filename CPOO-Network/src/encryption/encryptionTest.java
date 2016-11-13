package encryption;

import static org.junit.Assert.*;
import org.junit.Test;
import message.*;

/**
 * This JUnit class is intended to test all the encryption algorithm provided in
 * the {@link encryption} package :
 * <ul>
 * <li>{@link encryption#Atbash}
 * <li>{@link encryption#Cesar}
 * <li>{@link encryption#Keyword}
 * <li>{@link encryption#Rottreize}
 * <li>{@link encryption#Vigenere}
 * </ul>
 * The initial message to be transcripted is the <code>String</code>
 * <code>"ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz 1.è"</code>. For
 * each cypher method, this string must become the respective following ones :
 * <ul>
 * <li>Atbash:
 * <code>"ZYXWVUTSRQPONMLKJIHGFEDCBA zyxwvutsrqponmlkjihgfedcba 1.è"</code>
 * <li>Cesar:
 * <code>"DEFGHIJKLMNOPQRSTUVWXYZABCdefghijklmnopqrstuvwxyzabc 1.è"</code>
 * <li>Keyword:
 * <code>"KRYPTOSABCDEFGHIJLMNQUVWXZ kryptoabcdefghijlmnquvwxz a1.è"</code>
 * <li>Rottreize:
 * <code>"ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz 1.è"</code>
 * <li>Vigenere:
 * <code>"ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz 1.è"</code>
 * </ul>
 * <p>
 * For the Vigenere cipher method though, it is not useful to display the whole
 * alphabet, as the method does not rely on a monocharacter transcription
 * process, and that depends from the position of the character position in the
 * string (for instance, "AAAAA" cyphered with the key "KEY" gives "")
 * 
 * 
 * @author Benjamin GUIGNARD
 * @author Félix CHRISTELLE
 * @version 1.0, 11/06/2016
 * @since 1.0
 */

public class encryptionTest {

	public static String TEXT = "ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz 1.è";

	@Test
	public void testAtbash() {
		String expectedCypher = "ZYXWVUTSRQPONMLKJIHGFEDCBA zyxwvutsrqponmlkjihgfedcba 1.è";

		EncryptionAlgorithm atbash = new Atbash();

		String mc = atbash.cypher(new Messaged(TEXT), "").getMessage();
		String md = atbash.decypher(new Messagec(mc), "").getMessage();

		assertEquals(mc, expectedCypher);
		assertEquals(md, TEXT);
	}

	@Test
	public void testCesar() {
		String expectedCypher = "DEFGHIJKLMNOPQRSTUVWXYZABC defghijklmnopqrstuvwxyzabc 1.è";

		EncryptionAlgorithm cesar = new Cesar();

		String mc = cesar.cypher(new Messaged(TEXT), "3").getMessage();
		String md = cesar.decypher(new Messagec(mc), "3").getMessage();

		assertEquals(mc, expectedCypher);
		assertEquals(md, TEXT);
	}

	@Test
	public void testKeyword() {
		String expectedCypher = "KRYPTOSABCDEFGHIJLMNQUVWXZ kryptoabcdefghijlmnquvwxz a1.è";

		EncryptionAlgorithm keyword = new Keyword();

		String mc = keyword.cypher(new Messaged(TEXT), "KRYPTOS").getMessage();
		String md = keyword.decypher(new Messagec(mc), "KRYPTOS").getMessage();

		assertEquals(mc, expectedCypher);
		assertEquals(md, TEXT);
	}

	@Test
	public void testRottreize() {
		String expectedCypher = "NOPQRSTUVWXYZABCDEFGHIJKLM nopqrstuvwxyzabcdefghijklm 1.è";

		EncryptionAlgorithm rottreize = new Rottreize();

		String mc = rottreize.cypher(new Messaged(TEXT), "").getMessage();
		String md = rottreize.decypher(new Messagec(mc), "").getMessage();

		assertEquals(mc, expectedCypher);
		assertEquals(md, TEXT);
	}

	@Test
	public void testVigenere() {
		String expectedCypher = "KSASXTYRZHZEAFYGOGLHMFNVNS ksasxtyrzhzeafygoglhmfnvns 1.è";

		EncryptionAlgorithm vigenere = new Vigenere();

		String mc = vigenere.cypher(new Messaged(TEXT), "KRYPTOS").getMessage();
		String md = vigenere.decypher(new Messagec(mc), "KRYPTOS").getMessage();

		assertEquals(mc, expectedCypher);
		assertEquals(md, TEXT);
	}
}
