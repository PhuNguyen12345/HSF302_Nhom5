package fu.learning.springboot.exception;

public class IllegalResourceException extends RuntimeException {
	
	public IllegalResourceException(String message) {
		super(message); 
	}
}
