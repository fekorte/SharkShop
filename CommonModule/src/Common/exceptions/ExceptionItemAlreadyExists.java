package Common.exceptions;

public class ExceptionItemAlreadyExists extends Exception{
    public ExceptionItemAlreadyExists(int itemCode) {
            super("Item with item code" + itemCode + " already exists. ");
        }
}
