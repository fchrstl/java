package message;

/**
 * @author Benjamin GUIGNARD
 * @author F�lix CHRISTELLE
 * 
 * @version 1.0
 */

public class Messagec {

	String message;

	public Messagec(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public void display() {
		System.out.println(this.message);
	}
}