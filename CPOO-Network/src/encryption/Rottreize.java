package encryption;

import message.*;

/**
 * The Rottreize cipher is a specific case of Caesar cypher, with a shift of 13,
 * that is to say half the length of the alphabet. Hence, the methods from
 * {@link Caesar} are used for this cypher.
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
 * 
 * @see Caesar
 */

public class Rottreize implements EncryptionAlgorithm {

	/**
	 * Encrypts the message with the Rottreize cypher. Only conventional upper
	 * case A-Z and lower case a-z letters will be transformed.
	 * 
	 * @param md
	 *            the message to be transformed.
	 * @param key
	 *            an irrelevant <code>String</code>. May take any value here.
	 * @return the encrypted message
	 */
	public Messagec cypher(Messaged md, String key) {
		Caesar c = new Caesar();
		return c.cypher(md, "13");
	}

	/**
	 * Decrypts the message with the Rottreize cypher.
	 * 
	 * @param mc
	 *            the message to be transformed.
	 * @param key
	 *            an irrelevant <code>String</code>. May take any value here.
	 * @return the decrypted message
	 */
	public Messaged decypher(Messagec mc, String key) {
		Caesar c = new Caesar();
		return c.decypher(mc, "13");
	}
}
