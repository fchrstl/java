package encryption;

import message.*;

public interface EncryptionAlgorithm {

	public Messagec cypher(Messaged md, String key);

	public Messaged decypher(Messagec mc, String key);
}