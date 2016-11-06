package encryption;

import message.*;

/**
 * @author Benjamin GUIGNARD
 * @author Félix CHRISTELLE
 * 
 * @version 1.0
 */

public class Rottreize implements EncryptionAlgorithm {

	public Messagec cypher(Messaged md, String key) {
		Cesar c = new Cesar();
		return c.cypher(md, "13");
	}

	public Messaged decypher(Messagec mc, String key) {
		Cesar c = new Cesar();
		return c.decypher(mc, "13");
	}
}
