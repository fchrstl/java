package encryption;

import message.*;

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
