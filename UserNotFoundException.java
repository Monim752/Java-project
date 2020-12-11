
public class UserNotFoundException extends Exception {
	String msg;
	UserNotFoundException(String msg){
		this.msg = msg;
	}
	String errorMessage() {
		return msg;
	}
}
