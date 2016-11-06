package message;

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