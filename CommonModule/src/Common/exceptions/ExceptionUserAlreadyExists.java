package Common.exceptions;

public class ExceptionUserAlreadyExists extends Exception {
    public ExceptionUserAlreadyExists(String userName){
        super("User " + userName + "already exists.");
    }
}
