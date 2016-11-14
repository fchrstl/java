package encryption;

import message.*;

/**
 * @author Benjamin GUIGNARD
 * @author Félix CHRISTELLE
 * 
 * @version 1.0
 */

public interface EncryptionAlgorithm {

	public Messagec cypher(Messaged md, String key);

	public Messaged decypher(Messagec mc, String key);
}