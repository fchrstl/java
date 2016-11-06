package message;

/**
 * @author Benjamin GUIGNARD
 * @author Félix CHRISTELLE
 * 
 * @version 1.0
 */

public class Messaged {

	String message;

	public Messaged(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public void display() {
		System.out.println(this.message);
	}
}