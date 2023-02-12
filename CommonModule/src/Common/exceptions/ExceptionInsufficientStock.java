package Common.exceptions;

public class ExceptionInsufficientStock extends Exception{
    public ExceptionInsufficientStock (){
        super("The ordered quantity exceeds the available quantity");
    }
}