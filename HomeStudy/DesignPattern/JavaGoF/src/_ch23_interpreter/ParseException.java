package _ch23_interpreter;

public class ParseException extends RuntimeException {
    
    public ParseException() {
        
    }
    
    public ParseException(String msg) {
        super(msg);
    }
}
