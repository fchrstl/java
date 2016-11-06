package message;

public class Messaged {
	
	String message;
	
	public Messaged() {
		this.message = "";
	}
	
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